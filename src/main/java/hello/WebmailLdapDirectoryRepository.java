package hello;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
//import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.*;
import javax.naming.directory.*;

import java.io.*;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.TransientRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import scala.annotation.meta.setter;

import com.edms.service.FolderService;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.webmail.inbox.ArrayOfInboxMail;
import com.webmail.inbox.ArrayOfInboxMailDesc;
import com.webmail.inbox.Inbox;
import com.webmail.inbox.InboxDesc;
import com.webmail.inbox.InboxListDescReturn;
import com.webmail.inbox.InboxListReturn;
import com.webmail.ldapdirectory.ArrayOfUserDir;
import com.webmail.ldapdirectory.MyDirectory;
import com.webmail.quota.Webmailquota;

import edms.core.Config;

@Component
public class WebmailLdapDirectoryRepository {


	 private boolean textIsHtml = false;
	static String mail_cont="";
	static int flg=0;
	
	public ArrayOfUserDir listDirectoryUsers(String ldapurl, String uid, String pass, String base)
	{
		DirContext ctx=null;
		ArrayOfUserDir arrdir= new ArrayOfUserDir();
		try
		{
			System.out.println("Directory repository");
		ctx=getConnection(ldapurl,uid,pass,base);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {"cn","mail","mobile","homePhone","telephoneNumber","postalAddress","postalCode","jpegPhoto","department"}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results =ctx.search(base,"mail=*", constraints);
		int f=0;
		int x=0;
		
		while (results.hasMore()) {
			f=1;
			MyDirectory dir= new MyDirectory();
			String mail="";
			String photo="";
			String fn="";
			String mob="";
			String hmob="";
			String tel="";
			String dept="";
			String addr="";
			String addrpcode="";
			SearchResult si =(SearchResult)results.next();
			String ck=si.getName();
			   System.out.println("<br><br><br>"+ck);
			    Attributes attrs = si.getAttributes();
			String arr[]=ck.split(",");
			int l=arr.length;

			    if (attrs == null) {
			       System.out.println("   No attributes");
			        continue;
			    }
			    
			    
			    
			    	NamingEnumeration ae = attrs.getAll(); 
			    	while (ae.hasMoreElements()) {
			        Attribute attr =(Attribute)ae.next();
			        String id = attr.getID();
			        Enumeration vals = attr.getAll();
			        while (vals.hasMoreElements())
			        {
			        String str= vals.nextElement().toString();
			       
			        if(id.equalsIgnoreCase("cn"))
			        {
			        fn=str;
			        }
			        else if(id.equalsIgnoreCase("mail"))
			        {
			        mail=str;
			        }
			        else if(id.equalsIgnoreCase("jpegPhoto"))
			        {
			        photo=str;
			        }
			        else if(id.equalsIgnoreCase("mobile"))
			        {
			        mob=str;
			        }
			        else if(id.equalsIgnoreCase("homePhone"))
			        {
			        hmob=str;
			        }
			        else if(id.equalsIgnoreCase("telephoneNumber"))
			        {
			        tel=str;
			        }
			        else if(id.equalsIgnoreCase("postalAddress"))
			        {
			        addr=str;
			        }
			        else if(id.equalsIgnoreCase("postalCode"))
			        {
			        	addrpcode=str;
			        }
			        else if(id.equalsIgnoreCase("department"))
			        {
			        dept=str;
			        }
			        
			        
			     }
			    }

			if(mail.indexOf("@")>0)
			{
				String adr=addr;
				dir.setName(fn);
				dir.setEmail(mail);
				if(!addrpcode.equals(""))
				{
					adr+=", "+addrpcode;
				}
				dir.setAddress(adr);
				
				arrdir.getDirectoryUserList().add(dir);

			}
			
			}
			/*if(f==0)
			{
			//out.print("Search not found");
			
			}*/
		
			
			}
			catch(Exception e)
			{
				System.out.print(e);
			}
		finally
		{
			if(ctx!=null)
			{
				closeConn(ctx);
			}
				
		}
		
		          
  return   arrdir;        
}
	
	public  DirContext getConnection(String url,String uid,String password, String base)
	{
		    DirContext ctx=null;
		    try
		    {
		    	String username="";
		    	/*String arr[]=uid.split("@");
		    	String dom="";
		    	if(arr!=null && arr.length==2)
		    	{
		    		dom=arr[1];
		    	}
		    String base="ou=Users,domainName="+dom+",o=domains,"+mbase;*/
		    username="mail="+uid+","+base;
		  //  System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, url); // LDAP host and base

		    // LDAP authentication options
		    env.put(Context.SECURITY_AUTHENTICATION, "simple");
		    env.put(Context.SECURITY_PRINCIPAL, username);
		    env.put(Context.SECURITY_CREDENTIALS, password);

		     ctx = new InitialDirContext(env);
		
		    }
		    catch(Exception e)
		    {
		    	System.out.print(e.toString());
		    	e.printStackTrace();
		    	
		    }
		    return ctx;
	}
	
	public void closeConn(DirContext ctx)
	{
		try
	    {
	   if(ctx!=null)
	   {
	     ctx.close();
	   }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	//System.out.print(e.toString());
	    	
	    }
	}

}
