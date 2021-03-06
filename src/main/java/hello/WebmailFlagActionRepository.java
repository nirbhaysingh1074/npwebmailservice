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
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

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
public class WebmailFlagActionRepository {


/*	@Autowired
	FolderService folderService;*/

	
	public boolean setWebmailFlag(String host, String port, String id, String pass, String foldernm, String uids)
	{
		
		boolean status=true;
		
		String arr[]=uids.split("-");
		long uidarr[]=new long[arr.length];
		for(int i=0;i< arr.length;i++)
		{
			uidarr[i]=Long.parseLong(arr[i].trim());
		}
		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
        	store=Connections.imapConnectionNP( host,  port,  id,  pass);
			IMAPStore imapStore = (IMAPStore) store; 
			folder = (IMAPFolder) store.getFolder(foldernm); //This works for both email account
		
			UIDFolder uf = (UIDFolder)folder;
			if (!folder.exists()) 
			{
				System.exit(0);
			}
      folder.open(Folder.READ_WRITE);
      Message[] msg =uf.getMessagesByUID(uidarr);
     
      for (int i = msg.length-1;  i>=0; i--)
      {

    	  msg[i].setFlag(Flags.Flag.FLAGGED, true);

      }
 
  folder.close(true);
  store.close();
  }
  
catch(Exception e)
        {
		status=false;
		e.printStackTrace();
        }
  return  status;        
}
	
	
	
public boolean removeWebmailFlag(String host, String port, String id, String pass, String foldernm, String uids)
	{
		
		boolean status=true;
		
		String arr[]=uids.split("-");
		long uidarr[]=new long[arr.length];
		for(int i=0;i< arr.length;i++)
		{
			uidarr[i]=Long.parseLong(arr[i].trim());
		}
		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
        	store=Connections.imapConnectionNP( host,  port,  id,  pass);
			IMAPStore imapStore = (IMAPStore) store; 
			folder = (IMAPFolder) store.getFolder(foldernm); //This works for both email account
			UIDFolder uf = (UIDFolder)folder;
			if (!folder.exists()) 
			{
				System.exit(0);
			}
			folder.open(Folder.READ_WRITE);
			Message[] msg =uf.getMessagesByUID(uidarr);
			for (int i = msg.length-1;  i>=0; i--)
			{
				msg[i].setFlag(Flags.Flag.FLAGGED, false);
			}
 
			folder.close(true);
			store.close();
        }
        catch(Exception e)
        {
        status=false;
        e.printStackTrace();
        }
  return  status;        
}
}
