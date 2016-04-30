package hello;

import javax.activation.DataHandler;

import java.util.Properties;
import java.io.*;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

@Component
public class WebmailAttachmentDownloadRepository {



	
	public InputStream downloadMailAttach(String host, String port, String id, String pass, String uid, String foldername, String name)
	{
		InputStream input=null;
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
          
			store=Connections.imapConnectionNP( host,  port,  id,  pass);
			IMAPStore imapStore = (IMAPStore) store; 
         folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
		System.out.println("%%%%%%%attach repo="+name);
      UIDFolder uf = (UIDFolder)folder;
if (!folder.exists()) {
	// inboxlist.setGetInboxByUid(inb);
    // return   inboxlist;        
  	}
  folder.open(Folder.READ_WRITE);
  long [] arr={Long.parseLong(uid)};
 Message[] msg =folder.getMessagesByUID(arr);
//int umsg= folder.getUnreadMessageCount();
 for (int i = 0; i< msg.length; i++)
  {
	 
	/* inb.setUid(""+uid);
	 Message message = msg[i];
	
	 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
	 inb.setMailFlage(chkst);
	 
	 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
	 inb.setMailSeen(chkseen);
	 
	 String from = InternetAddress.toString(msg[i].getFrom());
  inb.setFromId(from);
 

  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
  inb.setReplyId(replyTo);

  String to = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
  inb.setToId(to);

  String subject = msg[i].getSubject();
  inb.setSubject(subject);

  Date sent = msg[i].getSentDate();
  SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat format1 = new SimpleDateFormat("E, MMM dd, yyyy");
  SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a"); 
  SimpleDateFormat format3 = new SimpleDateFormat("MMM dd"); 
  String dip_dt="";
  Date cdt=new Date();
  String dt1=format0.format(cdt);
  String dt2=format0.format(sent);
  if(dt1.equalsIgnoreCase(dt2))
  {
	  dip_dt=format2.format(sent);
  }
  else
  {
	  dip_dt=format3.format(sent);
  }*/
  
  //String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
  
  //inb.setSendDtae(dip_dt);
 // inb.setSendDtaeTitle(ttl_dt);
 
  Object content = msg[i].getContent();  

 // String attch="";
 // String mail_cont="";
  
  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	 /* String cnt=msg[i].getContent().toString();
  	  
  		mail_cont=cnt;
  	  */
  }
  else
  {
	  /*if (content instanceof String)  
{
		  
    String body = (String)content;  
  
     attch="";
}  
else */
	if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
    
 

    for (int j = 0; j < multipart.getCount(); j++) {

        BodyPart bodyPart = multipart.getBodyPart(j);
       // Attachment at=new Attachment();
        /*mail_cont=getText(bodyPart);
        if(mail_cont.length()>100)
    	  {
    	    mail_cont=mail_cont.substring(0, 99);
    	  }
    	 */
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	 
              DataHandler handler = bodyPart.getDataHandler();
              if(handler.getName().equalsIgnoreCase(name))
              {
            	  
            	  input = bodyPart.getInputStream();
            	
              }
              
              System.out.println("%%%%stream="+input);
              
            //  at.setAttachmentName(handler.getName());
              
              /*
              at.setAttachmentSize(""+bodyPart.getSize());
              if(attch.equals(""))
              {
            	  attch=""+handler.getName(); 
              }
              else
              {
            	  attch=attch+"-"+handler.getName();             	  
              }
              
              inb.getAttachment().add(at);*/
            }
          else 
          	{
        	  
        	  /*if(bodyPart.getContentType().contains("text/html;"))
    		  {
        	  String cnt=bodyPart.getContent().toString();
              if(mail_cont==null || mail_cont.equals(""))
              {
          	     	mail_cont=cnt;
          	  }
    		  }
        	  else  if(bodyPart.getContentType().contains("text/plain;"))
    		  {
            	  continue;
    		  }
        	  else
        	  {
        		  String cnt=bodyPart.getContent().toString();
                  if(mail_cont==null || mail_cont.equals(""))
                  {
              	     	mail_cont=cnt;
              	  }
        	  }*/
            }
   
  }
  }
  }
 // String arr[]=attch.split(",");
  //inb.setMailContent(mail_cont);
  
  
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	  //	status=false;
        }
  catch(IOException e)
        {
		//status=false;
        }    
  catch(Exception e)
        {
		//status=false;
        }
        
        return input;
}
	

	
}
