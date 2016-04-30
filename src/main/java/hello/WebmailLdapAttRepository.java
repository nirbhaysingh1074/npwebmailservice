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
public class WebmailLdapAttRepository {


/*	@Autowired
	FolderService folderService;*/

	
	
	public VCFLdapDirListReturn getVCFLdapDirAtt(String ldapurl, String uid, String pass, String base, String searchbase)
	{
		
		VCFLdapDirListReturn FileList1 =new VCFLdapDirListReturn();
		ArrayOfVCFLdapDir Files =new ArrayOfVCFLdapDir();
		
		boolean status=true;
		DirContext ctx=null;
		 try	
		 {
		ctx=getConnection(ldapurl,uid,pass,base);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {"cn","mail","mobile","homePhone","telephoneNumber","postalAddress","postalCode","jpegPhoto","department","enabledService","accountStatus"}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results = null;
		if(searchbase.startsWith("123"))
		{
			searchbase = "(|(mail=0*)(mail=1*)(mail=2*)(mail=3*)(mail=4*)(mail=5*)(mail=6*)(mail=7*)(mail=8*)(mail=9*))";
			results =ctx.search(base, searchbase, constraints);
		}
		else
		{
			results =ctx.search(base,"mail="+searchbase, constraints);
		}
		
		int f=0;
		int x=0;
		
		while (results.hasMore()) {
			f=1;
			VCFLdapDirAtt vcfldapatt= new VCFLdapDirAtt();
			int book=0;
			String acc="";
			String mail="";
			String photo="";
			String fn="";
			String mob="";
			String hmob="";
			String tel="";
			String dept="";
			String addr="";
			String addrpcode="";
			byte []jpegBytes1=null;
			SearchResult si =(SearchResult)results.next();
			String ck=si.getName();
			//System.out.println("<br><br><br>"+ck);
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
			        //String str= vals.nextElement().toString();
			       
			        if(id.equalsIgnoreCase("cn"))
			        {
			        fn=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("mail"))
			        {
			        mail=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("jpegPhoto"))
			        {
			        	jpegBytes1 = (byte[]) vals.nextElement();
			        	 if(jpegBytes1!=null)
			        	 {
			        		 photo= new sun.misc.BASE64Encoder().encode(jpegBytes1);
			        	 }
			        }
			        else if(id.equalsIgnoreCase("mobile"))
			        {
			        mob=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("homePhone"))
			        {
			        hmob=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("telephoneNumber"))
			        {
			        tel=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("postalAddress"))
			        {
			        addr=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("postalCode"))
			        {
			        	addrpcode=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("department"))
			        {
			        dept=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("accountStatus"))
			        {
			        acc=vals.nextElement().toString();
			        }
			        else if(id.equalsIgnoreCase("enabledService"))
			        {
			        	String str=vals.nextElement().toString();
			        	if(str.equalsIgnoreCase("displayedInGlobalAddressBook"))
			        	{
			        	book=1;
			        	}
			        }
			        
			        
			     }
			    }

			if(mail.indexOf("@")>0 && acc.equalsIgnoreCase("active") && book==1)
			{
				String adr=addr;
				vcfldapatt.setContactName(fn);
				vcfldapatt.setContactEmail(mail);
				if(!addrpcode.equals(""))
				{
					adr+=", "+addrpcode;
				}
				 
				String mb="";
				int chk=0;
				if(mob != null && !(mob.equalsIgnoreCase("")))
				{
				mb=mob;	
				chk++;
				}
				if(hmob != null && !(mob.equalsIgnoreCase("")))
				{
					if(mb == null || (mb.equalsIgnoreCase("")))
					{	
						mb=hmob;	
					}
					chk++;
				}
				if(tel != null && !(tel.equalsIgnoreCase("")))
				{
					if(mb == null || (mb.equalsIgnoreCase("")))
					{	
						mb=tel;	
					}
					chk++;
				}
				
				if(chk>1)
				{
					chk--;
					mb=mb+"(+"+chk+")";
				}
				
				vcfldapatt.setContactAddress(adr);
				vcfldapatt.setContactPhoto(photo);
				vcfldapatt.setContactDept(dept);
				vcfldapatt.setContactPhone(mb);
				Files.getVCFLdapDirList().add(vcfldapatt);
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
		
		 FileList1.setVCFLdapDirListResult(Files);	
		 FileList1.setVCFLdapDirSuccess(status);	
		
			return FileList1;	
			
	
	}
	
	

	
	public ContactFullDetail getFullDetailContact(String ldapurl, String uid, String pass, String base, String contactemail)
	{
		ContactFullDetail confdet=new ContactFullDetail();
		DirContext ctx=null;
		 try	
		 {
			 ctx=getConnection(ldapurl,uid,pass,base);
			  System.out.println("^^^^^^^^^^^^^^^^^ctx="+ctx);
				SearchControls constraints = new SearchControls(); 
				constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
				String attrList[] = {"cn","mail","mobile","homePhone","telephoneNumber","postalAddress","postalCode","jpegPhoto","department"}; 
				constraints.setReturningAttributes(attrList);
				NamingEnumeration results =ctx.search(base,"mail="+contactemail, constraints);
				int f=0;
				int x=0;
				
				while (results.hasMore()) {
					f=1;
					VCFLdapDirAtt vcfldapatt= new VCFLdapDirAtt();
					String mail="";
					String photo="";
					String fn="";
					String mob="";
					String hmob="";
					String tel="";
					String dept="";
					String addr="";
					String addrpcode="";
					byte []jpegBytes1=null;
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
					       // String str= vals.nextElement().toString();
					       
					        if(id.equalsIgnoreCase("cn"))
					        {
					        fn=vals.nextElement().toString();;
					        }
					        else if(id.equalsIgnoreCase("mail"))
					        {
					        mail=vals.nextElement().toString();;
					        }
					        else if(id.equalsIgnoreCase("jpegPhoto"))
					        {
					        jpegBytes1 = (byte[]) vals.nextElement();
					        	 if(jpegBytes1!=null)
					        	 {
					        		 photo= new sun.misc.BASE64Encoder().encode(jpegBytes1);
					        	 }
					        	
					        }
					        else if(id.equalsIgnoreCase("mobile"))
					        {
					        mob=vals.nextElement().toString();;
					        }
					        else if(id.equalsIgnoreCase("homePhone"))
					        {
					        hmob=vals.nextElement().toString();
					        }
					        else if(id.equalsIgnoreCase("telephoneNumber"))
					        {
					        tel=vals.nextElement().toString();
					        }
					        else if(id.equalsIgnoreCase("postalAddress"))
					        {
					        addr=vals.nextElement().toString();
					        }
					        else if(id.equalsIgnoreCase("postalCode"))
					        {
					        	addrpcode=vals.nextElement().toString();
					        }
					        else if(id.equalsIgnoreCase("department"))
					        {
					        dept=vals.nextElement().toString();
					        }
					       // System.out.println("^^^^^^^^^^^^^^^^^="+str);
					        
					     }
					    }

					if(mail.indexOf("@")>0)
					{
						String adr=addr;
						confdet.setContactName(fn);
						confdet.setContactEmail(mail);
						if(!addrpcode.equals(""))
						{
							adr+=", "+addrpcode;
						}
						 
						String mb="";
						int chk=0;
						if(mob != null && !(mob.equalsIgnoreCase("")))
						{
							confdet.setContactMobile(mob);
						}
						if(hmob != null && !(mob.equalsIgnoreCase("")))
						{
							confdet.setContactHomePhone( hmob);	
						}
						if(tel != null && !(tel.equalsIgnoreCase("")))
						{
							confdet.setContactTel(tel);
							
						}
						
						
						confdet.setContactAddress(adr);
						confdet.setContactPhoto(photo);
						confdet.setContactDept(dept);
						
						
					}
					
					}
					if(f==0)
					{
					//out.print("Search not found");
					
					}
				
					
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
		
		
		
		
		
		return confdet;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getLdapAttFname(String ldapurl, String uid, String pass, String base, String attname)
	{
		
		String attval="";
		try
		{
		DirContext ctx=getConnection(ldapurl,uid,pass,base);
		
		//System.out.println("@@@@@@@@@@@@@@@@@ctx="+ctx);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {attname}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results =ctx.search(base,"mail="+uid, constraints);
		int f=0;
		int x=0;
		while (results.hasMore()) {
		f=1;
		    SearchResult si =(SearchResult)results.next();
		    String ck=si.getName();
		   // System.out.println("@@@@@@@@@@@ck="+ck);
		    Attributes attrs = si.getAttributes();
		    if (attrs == null) {
		        continue;
		    }
		    
		    NamingEnumeration ae = attrs.getAll(); 
		    while (ae.hasMoreElements()) {
		        Attribute attr =(Attribute)ae.next();
		        String id = attr.getID();
		        Enumeration vals = attr.getAll();
		        while (vals.hasMoreElements())
		        {
		      //  String str= vals.nextElement().toString();
		     //   System.out.print("********************str="+str);
		        if(id.equalsIgnoreCase(attname) )
		        {
		        	if(id.equalsIgnoreCase("jpegPhoto"))
			        {
		        		byte []jpegBytes1=null;
		        		jpegBytes1 = (byte[]) vals.nextElement();
			        	 if(jpegBytes1!=null)
			        	 {
			        		 attval= new sun.misc.BASE64Encoder().encode(jpegBytes1);
			        	 }
			        	
			        }
		        	else
		        	{
		        	attval=vals.nextElement().toString();
		        	}
		        }
		      }
		    }
		 

		}
		

		
		closeConn(ctx);
		}
		catch(NamingException e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		//System.out.print("********************fname="+attval);
		return  attval;        
}
	
	
	public String getLdapAttOtrUser(String ldapurl, String uid, String pass, String base, String searchbase, String attname)
	{
		
		String attval="";
		try
		{
		DirContext ctx=getConnection(ldapurl,uid,pass,base);
		
		//System.out.println("@@@@@@@@@@@@@@@@@ctx="+ctx);
		
		SearchControls constraints = new SearchControls(); 
		constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		String attrList[] = {attname}; 
		constraints.setReturningAttributes(attrList);
		NamingEnumeration results =ctx.search(base,"mail="+searchbase, constraints);
		int f=0;
		int x=0;
		while (results.hasMore()) {
		f=1;
		    SearchResult si =(SearchResult)results.next();
		    String ck=si.getName();
		   // System.out.println("@@@@@@@@@@@ck="+ck);
		    Attributes attrs = si.getAttributes();
		    if (attrs == null) {
		        continue;
		    }
		    
		    NamingEnumeration ae = attrs.getAll(); 
		    while (ae.hasMoreElements()) {
		        Attribute attr =(Attribute)ae.next();
		        String id = attr.getID();
		        Enumeration vals = attr.getAll();
		        while (vals.hasMoreElements())
		        {
		      //  String str= vals.nextElement().toString();
		     //   System.out.print("********************str="+str);
		        if(id.equalsIgnoreCase(attname) )
		        {
		        	if(id.equalsIgnoreCase("jpegPhoto"))
			        {
		        		byte []jpegBytes1=null;
		        		jpegBytes1 = (byte[]) vals.nextElement();
			        	 if(jpegBytes1!=null)
			        	 {
			        		 attval= new sun.misc.BASE64Encoder().encode(jpegBytes1);
			        	 }
			        	
			        }
		        	else
		        	{
		        	attval=vals.nextElement().toString();
		        	}
		        }
		      }
		    }
		 

		}
		

		
		closeConn(ctx);
		}
		catch(NamingException e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			//System.out.print(e.toString());
			e.printStackTrace();
		}
		//System.out.print("********************fname="+attval);
		return  attval;        
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
		 //   System.out.println("@@@@@@@@@@@@@@@@@@ ldap user="+username);
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, url); // LDAP host and base
		    env.put("java.naming.ldap.attributes.binary", "jpegPhoto");
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
