package hello;

import javax.annotation.PostConstruct; 
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
//import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
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

import com.edms.service.FolderService;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.webmail.ldapattribute.ArrayOfVCFLdapDir;
import com.webmail.ldapattribute.ContactFullDetail;
import com.webmail.ldapattribute.VCFLdapDirAtt;
import com.webmail.ldapattribute.VCFLdapDirListReturn;
import com.webmail.ldapdirectory.MyDirectory;
import com.webmail.quota.Webmailquota;

import edms.core.Config;

@Component
public class WebmailLdapModifyAttRepository {


/*	@Autowired
	FolderService folderService;*/

	
	

	
	
	
	public boolean modifyOneLdapAtt(String ldapurl, String uid, String pass, String base, String attname, String attvalue)
	{
		boolean status=true;
		DirContext ctx=getConnection(ldapurl,uid,pass,base);
		String entry="mail="+uid+","+base;
		if(attvalue!=null && !(attvalue.equals("")))
		{
			status= modyfyRepAttr(ctx, entry, attname, attvalue);
		}
		else if(!attname.equalsIgnoreCase("cn") && !attname.equalsIgnoreCase("mail"))
		{
			status= modyfyRemAttr(ctx, entry, attname);	
		}
		closeConn(ctx);
		return status;
	}
	
	
	
	
	
	public boolean modifyListLdapAtt(String ldapurl, String uid, String pass, String base,  List<String> attlist, List<String> attlistvalue)
	{
		boolean status=true;
		DirContext ctx=getConnection(ldapurl,uid,pass,base);
		String entry="mail="+uid+","+base;
		
		for(int i=0;i< attlist.size() && i< attlistvalue.size();i++ )
		{
			if(attlistvalue.get(i) !=null && !(attlistvalue.get(i).equals("")))
			{
				status= modyfyRepAttr(ctx, entry, attlist.get(i), attlistvalue.get(i));
			}
			else if(!attlist.get(i).equalsIgnoreCase("cn") && !attlist.get(i).equalsIgnoreCase("mail"))
			{
				status= modyfyRemAttr(ctx, entry, attlist.get(i));	
			}
			
		}
		closeConn(ctx);
		return status;
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
		    	}*/
		   // String base="ou=Users,domainName="+dom+",o=domains,"+mbase;
		    username="mail="+uid+","+base;
		    //System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
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
	
	

	public boolean modyfyAddAttr(DirContext ctx, String MY_ENTRY, String attr, String val)
	{
		boolean status=true;
		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
			 javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute(attr, val);
			 mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod0);
			 ctx.modifyAttributes(MY_ENTRY, mods);
	    }
	    catch(Exception e)
	    {
	    	status=false;
	    	e.printStackTrace();
	    	
	    }
		return status;
	}
	
	public boolean modyfyRemAttr(DirContext ctx, String MY_ENTRY, String attr)
	{
		boolean status=true;
		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
			 javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute(attr);
			 mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod0);
			 ctx.modifyAttributes(MY_ENTRY, mods);
	    }
	    catch(Exception e)
	    {
	    	status=false;
	    	e.printStackTrace();
	    	
	    }
		return status;
	}
	
	public boolean modyfyRepAttr(DirContext ctx, String MY_ENTRY, String attr, String val)
	{
		boolean status=true;
		try
	    {
			 ModificationItem[] mods = new ModificationItem[1];
			 javax.naming.directory.Attribute mod0 = new javax.naming.directory.BasicAttribute(attr, val);
			 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			 ctx.modifyAttributes(MY_ENTRY, mods);

	    }
	    catch(Exception e)
	    {
	    	status=false;
	    	e.printStackTrace();
	    	
	    }
		return status;
	}
	

}
