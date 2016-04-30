package hello;

import javax.activation.DataHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import java.io.*;
import java.nio.file.Files;




import org.springframework.stereotype.Component;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.SortTerm;
import com.webmail.inbox.ArrayOfInboxMail;
import com.webmail.inbox.ArrayOfInboxMailDesc;
import com.webmail.inbox.Inbox;
import com.webmail.inbox.InboxDesc;
import com.webmail.inbox.InboxListDescReturn;
import com.webmail.inbox.InboxListReturn;


@Component
public class WebmailInboxRepository {


	 private boolean textIsHtml = false;
	static String mail_cont="";
	static String mail_cont_otr="";
	static int flg=0;
	static int inline=0;
	
	public InboxListReturn listWebmailInbox(String host, String port, String id, String pass, String start, String end, String foldername)
	{
		InboxListReturn inboxlist= new InboxListReturn();
		ArrayOfInboxMail inboxmail= new ArrayOfInboxMail();
		boolean status=true;		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
          
        	store=Connections.imapConnectionNP(host, port, id, pass);
			IMAPStore imapStore = (IMAPStore) store; 
         folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
		
      UIDFolder uf = (UIDFolder)folder;
if (!folder.exists()) {
	 inboxlist.setInboxListReturn(inboxmail);
     inboxlist.setSuccess(false);
     return   inboxlist;        
  	}
  folder.open(Folder.READ_ONLY);
  
 Message[] msg =Sortload(folder);  //folder.getMessages();
 
// List<Message> msglist = new ArrayList<Message>(Arrays.asList(msg));
 //Collections.sort(msglist,new NPCompare());
 //msg= msglist.toArray(new Message[msglist.size()]);

//int umsg= folder.getUnreadMessageCount();
 for (int i = msg.length-((Integer.parseInt(start))+1),k=0; k< (Integer.parseInt(end)) && i>=0;k++, i--)
  {
	Inbox inb= new Inbox();
	flg=0;
	inline=0;
	String tag="";
	String arr[]= msg[i].getFlags().getUserFlags();
	 for(int b=0;b<arr.length;b++)
	  {
		 if(tag.equals(""))
		 {
			 tag=arr[b];
		 }
		 else
		 {
			 tag=tag+"~"+arr[b];
		 }
	  // out.print("<br>user flag="+arr[b]);
	  } 
	inb.setMailTag(tag);
	
	
	Enumeration headers = msg[i].getAllHeaders();
	 while (headers.hasMoreElements()) {
		 Header h = (Header) headers.nextElement();
		 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
		  if(h.getName().equalsIgnoreCase("X-Priority"))
		  {
			if( h.getValue().contains("Lowest"))
			{
				inb.setMailPriority("Lowest");
			}
			else if(h.getValue().contains("Highest"))
			{
				inb.setMailPriority("Highest");
			}
			  break;
		  }
		  }
	
	
	 long uid = uf.getUID(msg[i]);
	 inb.setUid(""+uid);
	 Message message = msg[i];
	
	 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
	 inb.setMailFlage(chkst);
	 
	 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
	 inb.setMailSeen(chkseen);
	 
	 String from = InternetAddress.toString(msg[i].getFrom());
	 inb.setFromId(from);
 
//System.out.println("inbox repository***from="+from);
  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
  if(replyTo==null)
  {
	  replyTo="";
  }
  inb.setReplyId(replyTo);
//  //System.out.println("inbox repository***replyto="+replyTo);
  String to = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
 if(to==null)
 {
	 to="";
 }
  inb.setToId(to);
  
  String cc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.CC));
  if(cc==null)
  {
	  cc="";
  }
  inb.setCCId(cc);
  
  String bcc = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.BCC));
  if(bcc==null)
  {
	  bcc="";
  }
  inb.setBCCId(bcc);
//System.out.println("inbox repository***to="+to);
  String subject = msg[i].getSubject();
  if(subject==null)
  {
	  subject="";
  }
  inb.setSubject(subject.trim());
  //System.out.println("inbox repository***sub="+subject);
  Date sent = msg[i].getReceivedDate();
  //System.out.println("inbox repository***date="+sent);
  SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat format1 = new SimpleDateFormat("E, MMM dd, yyyy");
  SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a"); 
  SimpleDateFormat format3 = new SimpleDateFormat("MMM dd"); 
  SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy"); 
  String dip_dt="";
  Date cdt=new Date();
  String dt1=format0.format(cdt);
  String dt2=format0.format(sent);
  
  SimpleDateFormat df = new SimpleDateFormat("yyyy");
  String year = df.format(cdt);
  String year1 = df.format(sent);
  
  if(dt1.equalsIgnoreCase(dt2))
  {
 	  dip_dt=format2.format(sent);
  }
  else
  {
 	 if(year.equalsIgnoreCase(year1))
 	 {
 	  dip_dt=format3.format(sent);
 	 }
 	 else
 	 {
 		 dip_dt=format4.format(sent);
 	 }
  }
  
  String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
  //System.out.println("inbox repository*** tit date="+ttl_dt);
  inb.setSendDtae(dip_dt);
  inb.setSendDtaeTitle(ttl_dt);
 
  Object content = msg[i].getContent();  

  
  /*Message message1 = msg[i];
  writePart(message1);
  if(mail_cont==null || mail_cont.equals(""))
  {
	  if(mail_cont.length()>170)
	  {
	    mail_cont=mail_cont.substring(0, 169)+"....";
	  }
	 
  }
 writePart_otr(message1);
  inb.setMailContent(mail_cont);
  inb.setMailContentOtr(mail_cont_otr);
 
  mail_cont="";
  mail_cont_otr="";*/
  String attch="No";
 
  
  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	  mail_cont=msg[i].getContent().toString();
  	  
  }
  else
  {
if (content instanceof String)  
{  
    String body = (String)content;  
    attch="No";
}  
else if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
    
   List<String> attflnm=new ArrayList();
   List<String> attflsize=new ArrayList();
   for (int j = 0; j < multipart.getCount(); j++) {

    BodyPart bodyPart = multipart.getBodyPart(j);
        
       
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	  attch="Yes";
        	  DataHandler handler = bodyPart.getDataHandler();
              attflnm.add( handler.getName());  
              attflsize.add(""+bodyPart.getSize());
            }
          else 
          	{ 
              String cnt=bodyPart.getContent().toString();
              if(mail_cont==null || mail_cont.equals(""))
              {
          	 
          		mail_cont=cnt;
          	  
              }
            }
   
  }
    inb.getAttachmentName().addAll(attflnm);
    inb.getAttachmentSize().addAll(attflsize);
  }
  }
  
  mail_cont = mail_cont.replaceAll("\\n", "");
  mail_cont = mail_cont.replaceAll("\\r", "");
  mail_cont = mail_cont.replaceAll("\\<.*?\\>", "");
  if(mail_cont!=null && !mail_cont.equals(""))
  {
	  if(mail_cont.length()>150)
	  {
	    mail_cont=mail_cont.substring(0, 150)+"....";
	  }
	 
  }
  
  if(mail_cont.startsWith("javax.mail.internet.MimeMultipart"))
  {
	  mail_cont="This mail has attachment or Inline Image";  
  }
inb.setMailContent("<pre>"+mail_cont.trim()+"</pre>");
//  inb.setMailContent("<pre>hii</pre>");
  inb.setMailContentOtr(mail_cont_otr.trim());
 
  mail_cont="";
  mail_cont_otr="";
  
  
  //System.out.println("inbox repository***attch="+attch);
  inb.setAttachment(attch);
  inboxmail.getInboxMailList().add(inb);
  
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	  e.printStackTrace();
	  	status=false;
        }
        
catch(Exception e)
        {
	 e.printStackTrace();
		status=false;
        }
        
        inboxlist.setInboxListReturn(inboxmail);
        inboxlist.setSuccess(status);
                    
  return   inboxlist;        
}
	
	
	
	public Message[] Sortload(IMAPFolder folder)
	        throws MessagingException, UnsupportedEncodingException {
		SortTerm[] term=new SortTerm[1];
		// term[0]=SortTerm.REVERSE;
	 if(folder.getFullName().equalsIgnoreCase("Sent"))
	   {
		term[0] = SortTerm.DATE;
	  }
	   else
	   {
		term[0] = SortTerm.ARRIVAL;
	   }
	  
	    Message[] messages =null;
	  
	    if (folder != null) {
	       	        messages = folder.getSortedMessages(term);
	         }

	    return messages;
	}
	
	

	public InboxListDescReturn listWebmailInboxDesc(String host, String port, String id, String pass, String start, String end, String foldername)
	{
		InboxListDescReturn inboxlist= new InboxListDescReturn();
		ArrayOfInboxMailDesc inboxmail= new ArrayOfInboxMailDesc();
		boolean statusdesc=true;		
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        try 
        {
        	store=Connections.imapConnectionNP(host, port, id, pass); 
			IMAPStore imapStore = (IMAPStore) store; 
         folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
		
      UIDFolder uf = (UIDFolder)folder;
if (!folder.exists()) {
	 inboxlist.setInboxListDescReturn(inboxmail);
     inboxlist.setSuccessDesc(false);
     return   inboxlist;        
  	}
  folder.open(Folder.READ_ONLY);
  
 Message[] msg =folder.getMessages();
//int umsg= folder.getUnreadMessageCount();
 for (int i = (Integer.parseInt(start)),k=0; k< (Integer.parseInt(end)) && i< msg.length;k++, i++)
  {
	InboxDesc inb= new InboxDesc();
	 
	 long uid = uf.getUID(msg[i]);
	 inb.setUidDesc(""+uid);
	 Message message = msg[i];
	
	 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
	 inb.setMailFlageDesc(chkst);
	 
	 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
	 inb.setMailSeenDesc(chkseen);
	 
	 String from = InternetAddress.toString(msg[i].getFrom());
  inb.setFromIdDesc(from);
 

  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
  inb.setReplyIdDesc(replyTo);

  String to = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
  if(to==null)
  {
	  to="";
  }
  inb.setToIdDesc(to);

  String subject = msg[i].getSubject();
  inb.setSubjectDesc(subject);

  Date sent = msg[i].getReceivedDate();
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
  }
  
  String ttl_dt=format1.format(sent)+" at "+format2.format(sent);
  
  inb.setSendDtaeDesc(dip_dt);
  inb.setSendDtaeTitleDesc(ttl_dt);
 
  Object content = msg[i].getContent();  

  String attch="No";
  String mail_cont="";
  
  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	  String cnt=msg[i].getContent().toString();
  	  if(cnt.length()>170)
  	  {
  		mail_cont=cnt.substring(0, 169)+"....";
  	  }
  	  else
  	  {
  		mail_cont=cnt;
  	  }
  }
  else
  {
if (content instanceof String)  
{  
    String body = (String)content;  
  
     attch="No";
}  
else if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
    
 

    for (int j = 0; j < multipart.getCount(); j++) {

        BodyPart bodyPart = multipart.getBodyPart(j);
       
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	  attch="Yes";
        	  DataHandler handler = bodyPart.getDataHandler();
              handler.getName();                 
            }
          
          else 
          	{ 
              String cnt=bodyPart.getContent().toString();
              if(mail_cont==null || mail_cont.equals(""))
              {
          	  if(cnt.length()>170)
          	  {
          	    mail_cont=cnt.substring(0, 169)+"....";
          	  }
          	  else
          	  {
          		mail_cont=cnt;
          	  }
              }
            }
   
  }
  }
  }
  inb.setMailContentDesc(mail_cont);
  inb.setAttachmentDesc(attch);
  inboxmail.getInboxMailListDesc().add(inb);
  
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	  	statusdesc=false;
        }
        
catch(Exception e)
        {
		statusdesc=false;
        }
        
        inboxlist.setInboxListDescReturn(inboxmail);
        inboxlist.setSuccessDesc(statusdesc);
                    
  return   inboxlist;        
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	  public static void writePart(Part p) throws Exception {
	      if (p instanceof Message)
	    	  p.getContentType();
	      if (p.isMimeType("text/plain")) {
	    	  
	         mail_cont=(String) p.getContent();
	    	 
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(mail_cont==null || mail_cont.equals(""))
	    	  {
		       mail_cont=(String) p.getContent();
	    	  }
	    	
		      } 
	      else if (p.isMimeType("multipart/*")) {
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart(mp.getBodyPart(i));
	      } 
	      else if (p.isMimeType("message/rfc822")) {
	         writePart((Part) p.getContent());
	      } 
	  }
	  
	  
	  /*
	  public static void writePart_otr(Part p) throws Exception {
	      if (p instanceof Message)
	    	  p.getContentType();
	      if (p.isMimeType("text/plain")) {
	    	  
	    	  	mail_cont_otr=(String) p.getContent();
	    	 
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  if(mail_cont==null || mail_cont.equals(""))
	    	  {
	    		  mail_cont_otr=(String) p.getContent();
	    	  }
	    	
		      } 
	      else if (p.isMimeType("multipart/*")) {
	         Multipart mp = (Multipart) p.getContent();
	         int count = mp.getCount();
	         for (int i = 0; i < count; i++)
	            writePart_otr(mp.getBodyPart(i));
	      } 
	      else if (p.isMimeType("message/rfc822")) {
	         writePart_otr((Part) p.getContent());
	      } 
	  }
	  
	  */
	  
	  
	  
	
	   public static void writePart_otr(Part p) throws Exception {
		      if (p instanceof Message)
		         //Call methos writeEnvelope
		    //     writeEnvelope((Message) p);

		     // System.out.println("----------------------------");
		    	  p.getContentType();
		     // System.out.println("CONTENT-TYPE: " + p.getContentType());

		      //check if the content is plain text
		      if (p.isMimeType("text/plain")) {
		    	  if(flg==0)
		    	  {
		    		  flg=1;
		    	  }
		    	  if(flg!=2)
		    	  {
		        // System.out.println("This is plain text");
		        // System.out.println("---------------------------flg="+flg);
		         //System.out.println((String) p.getContent());
		         mail_cont_otr=(String) p.getContent();
		    	  }
		      } 
		      else if (p.isMimeType("text/html")) {
		    	  
		    	  if(flg!=2)
		    	  {
			       // System.out.println("This is plain text");
			      //  System.out.println("---------------------------flg="+flg);
			      //   System.out.println((String) p.getContent());
			         mail_cont_otr=(String) p.getContent();
		    	  }
		    	  flg=2;
			      } 
		      //check if the content has attachment
		      else if (p.isMimeType("multipart/*")) {
		        // System.out.println("This is a Multipart");
		        // System.out.println("---------------------------");
		         Multipart mp = (Multipart) p.getContent();
		         int count = mp.getCount();
		         for (int i = 0; i < count; i++)
		        	 writePart_otr(mp.getBodyPart(i));
		      } 
		      //check if the content is a nested message
		      else if (p.isMimeType("message/rfc822")) {
		        // System.out.println("This is a Nested Message");
		        // System.out.println("---------------------------");
		         writePart_otr((Part) p.getContent());
		      }
		      else if (p.getContentType().contains("image/")) {
		    	//  System.out.println("content type"+p.getContentType());
		    	  
		    	  Enumeration headers = p.getAllHeaders();
		    		 String con_id="";
		    		 while (headers.hasMoreElements()) {
		    		 Header h = (Header) headers.nextElement();
		    		 if(h.getName().equalsIgnoreCase("Content-ID"))
		    		  {
		    			 con_id= h.getValue();
		    			 break;
		    		  }
		    		 }
		    	  
		    	  if(con_id.equalsIgnoreCase(""))
		    	  {
		    		  con_id= ""+inline;
		    	  }
		    	  else
		    	  {
		    		  con_id=con_id.replace("<", "");
		    		  con_id=con_id.replace(">", "");
		    		 // con_id=con_id.replaceAll("@", "_");
		    		//  con_id=con_id.replaceAll("\\.", "_");
		    		  con_id="cid:"+con_id; 
		    	  }
		    	  
		    	  
		    	  
		    
		    //	File f = new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\"+p.getFileName());
		      File f = new File("/tmp/tomcat7-tomcat7-tmp/"+p.getFileName());
		    	  FileOutputStream output = new FileOutputStream(f);
		    	  com.sun.mail.util.BASE64DecoderStream test=(com.sun.mail.util.BASE64DecoderStream)p.getContent();
		    	  byte[] buffer = new byte[1024];
		    	  int bytesRead;
		    	  while((bytesRead = test.read(buffer)) != -1)
		    	  {
		    	  output.write(buffer,0,bytesRead);
		    	  }
		    	  test.close();
		    	  output.close();
		    	  
		    	//  File fi = new File("myfile.jpg");
		    	  byte[] fileContent = Files.readAllBytes(f.toPath());
		    	  
		    	 String path_img= new sun.misc.BASE64Encoder().encode(fileContent);
	       	   path_img= "data:image/jpg;base64,"+path_img;
	       	   inline++;
	       	mail_cont_otr=mail_cont_otr.replace(con_id, path_img);
	       	  // String inline_id="inline_img_"+inline;
	      /// 	mail_cont_otr+="<input type='hidden' id='"+con_id+"' value='" +path_img+ "' />";
		         /*System.out.println("content type" + p.getContentType());
		         File f = new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\image" + new Date().getTime() + ".jpg");
		         DataOutputStream output = new DataOutputStream(
		            new BufferedOutputStream(new FileOutputStream(f)));
		            com.sun.mail.util.BASE64DecoderStream test = 
		                 (com.sun.mail.util.BASE64DecoderStream) p
		                  .getContent();
		         byte[] buffer = new byte[1024];
		         int bytesRead;
		         while ((bytesRead = test.read(buffer)) != -1) {
		            output.write(buffer, 0, bytesRead);
		         }
		        String s = new String();
		         mail_cont+=s;*/
		    	
		      } 
		 

		}
	
}
