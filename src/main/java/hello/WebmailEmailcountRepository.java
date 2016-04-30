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
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;

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
import com.webmail.quota.Webmailquota;

import edms.core.Config;

@Component
public class WebmailEmailcountRepository {


/*	@Autowired
	FolderService folderService;*/

	
	public int listWebmailUnreadMailCount(String host,String port, String id, String pass, String folder)
	{
		int f=0;
		Store store = null;
		try
		{
		
			store=Connections.imapConnectionNP( host,  port,  id,  pass);
	    Folder inbox = store.getFolder(folder);
	    inbox.open(Folder.READ_ONLY);
	    Flags seen = new Flags(Flags.Flag.SEEN);
	    FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
	    Message messages[] = inbox.search(unseenFlagTerm);
	    f=messages.length;
	    inbox.close(true);
	    store.close();
		}
      catch(Exception e)
		{
    	  // out.print(e);
		}
                    
  return   f;        
}
	
	
	public synchronized  long listWebmailAllMailCount(String host,String port, String id, String pass, String foldername)
	{
		long f=0l;
		Store store = null;
		IMAPFolder folder = null;
		try
		{
			store=Connections.imapConnectionNP( host,  port,  id,  pass);
		IMAPStore imapStore = (IMAPStore) store; 
        folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
		
    
if (!folder.exists()) {
	   
 	}
 folder.open(Folder.READ_WRITE);
 
Message[] messages =folder.getMessages();
	    f=messages.length;
	    folder.close(true);
	    store.close();
		}
      catch(Exception e)
		{
    	  f=-1;
    	  e.printStackTrace();
		}
                    
  return   f;        
}
	
}
