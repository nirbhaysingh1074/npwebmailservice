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

import org.springframework.stereotype.Component;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.webmail.maildisplay.Attachment;
import com.webmail.maildisplay.GetMailDisplayResponse;
import com.webmail.maildisplay.InboxDisplay;

import java.io.*;
import java.nio.file.Files;

@Component
public class WebmailContentDisplayRepository {

	  static String mail_cont="";
	  static int flg=0;
	  static int inline=0;

	
	public InboxDisplay listWebmailContent(String host, String port, String id, String pass, String uid, String foldername)
	{
	//	GetMailDisplayResponse inboxlist= new GetMailDisplayResponse();
		InboxDisplay inb= new InboxDisplay();
		List<String> attach_nm=new ArrayList<String>();
		List<String> attach_size=new ArrayList<String>();
		IMAPFolder folder = null;
        Store store = null;
        Flag flag = null;
        mail_cont="";
        flg=0;
        inline=0;
        try 
        {
        	store=Connections.imapConnectionNP( host,  port,  id,  pass);
			IMAPStore imapStore = (IMAPStore) store; 
			folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
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
	 
	 inb.setUid(""+uid);
	 Message message = msg[i];
	 msg[i].setFlag(Flags.Flag.SEEN, true);
	 boolean chkst=	message.isSet(Flags.Flag.FLAGGED);
	 inb.setMailFlage(chkst);
	 boolean chkst1=	message.isSet(Flags.Flag.RECENT);
	 inb.setMailFlageRecent(chkst1); 
	 
	 boolean readSt=false;
	 
	 String harr[]= msg[i].getHeader("List-Unsubscribe");
	 
	 Enumeration headers = msg[i].getAllHeaders();
	 int hd=0;
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	  if(h.getName().equalsIgnoreCase("Disposition-Notification-To"))
	  {
		  hd++;
		  readSt=true;
		  inb.setMailReadRecId(h.getValue());
		 
	  }
	  
	  if(h.getName().equalsIgnoreCase("X-Priority"))
	  {
		  hd++;
		if( h.getValue().contains("Lowest"))
		{
			inb.setMailPriority("Lowest");
		}
		else if(h.getValue().contains("Highest"))
		{
			inb.setMailPriority("Highest");
		}
		 
	  }
	  if(hd>1)
	  {
		  break; 
	  }
	  }
	 inb.setMailFlageRecent(readSt);
	 
	 
	 String tag="";
		String arrt[]= msg[i].getFlags().getUserFlags();
		 for(int b=0;b<arrt.length;b++)
		  {
			 if(tag.equals(""))
			 {
				 tag=arrt[b];
			 }
			 else
			 {
				 tag=tag+"~"+arrt[b];
			 }
		  // out.print("<br>user flag="+arr[b]);
		  } 
		inb.setMailTag(tag);
	 
	 
	 boolean chkseen=	message.isSet(Flags.Flag.SEEN);
	 inb.setMailSeen(chkseen);
	 
  String from = InternetAddress.toString(msg[i].getFrom());
  inb.setFromId(from);
 

  String replyTo = InternetAddress.toString(msg[i].getReplyTo());
  inb.setReplyId(replyTo);

  String to = InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.TO));
  if(to==null)
  {
	  to="";
  }
  
  inb.setToId(to);

  String cc=InternetAddress.toString( msg[i].getRecipients(Message.RecipientType.CC));
  if(cc==null)
  {
	  cc="";
  }
  inb.setCCId(cc);
  
  String subject = msg[i].getSubject();
  inb.setSubject(subject);

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
  
  inb.setSendDtae(dip_dt);
  inb.setSendDtaeTitle(ttl_dt);
 
  Object content = msg[i].getContent();  

  String attch="";
  //String mail_cont="";
  Message message1 = msg[i];
  //System.out.println("---------------------------------");
  writePart(message1);
  
 //mail_cont=mail_cont.replaceAll("\n\n\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n\n", "<br />");
// mail_cont=mail_cont.replaceAll("\n", "<br />");
  /*mail_cont=mail_cont.replaceAll("\r", "<br />");*/
  //System.out.println("#############="+mail_cont);
 String tp=msg[i].getContentType();
  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	 // String cnt=msg[i].getContent().toString();
  	  
  	//	mail_cont=cnt;
  	  
  }
  else
  {
	  if (content instanceof String)  
{  
    String body = (String)content;  
  
     attch="";
}  
else if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
    
 

    for (int j = 0; j < multipart.getCount(); j++) {

        BodyPart bodyPart = multipart.getBodyPart(j);
        Attachment at=new Attachment();
      //  mail_cont=getText(bodyPart);
       // if(mail_cont.length()>100)
    	///  {
    	 ///   mail_cont=mail_cont.substring(0, 99);
    	///  }
    	 
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	 
              DataHandler handler = bodyPart.getDataHandler();
              if(handler.getName()!=null && ""+bodyPart.getSize()!=null)
              {
              at.setAttachmentName(handler.getName());
              at.setAttachmentSize(""+bodyPart.getSize());
                            
              inb.getAttachment().add(at);
              }
            }
          else 
          	{
        	 /* 
        	  if(bodyPart.getContentType().contains("text/html;"))
    		  {
        	  String cnt=bodyPart.getContent().toString();
              if(mail_cont==null || mail_cont.equals(""))
              {
          	   //  	mail_cont=cnt;
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
              	     //	mail_cont=cnt;
              	  }
        	  }*/
            }
   
  }
  }
  }
// String arr[]=attch.split(",");
 // String str="<div dir='ltr'>find document<br clear='all'><div><div><br>-- <br><div class='gmail_signature'><div dir='ltr'><div dir='ltr' style='color:rgb(34,34,34);font-family:arial;font-size:small;font-style:normal;font-variant:normal;font-weight:normal;letter-spacing:normal;line-height:normal;text-align:start;text-indent:0px;text-transform:none;white-space:normal;word-spacing:0px'><p style='margin:0px'><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;background-image:initial;background-repeat:initial'>We look forward to a long and fruitful association with you.</span><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><br><br></span></p><p style='margin:0px'><b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Thanks and Regards</span></b></p><p style='margin:0px'><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><br></span><span style='font-size:12pt;font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Nirbhay Pratap Singh</span></p><p style='margin:0px'><font size='3' face='Microsoft New Tai Lue, sans-serif'>Hindi Poet</font><b style='color:rgb(38,38,38);font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'></b></p><p style='margin:0px'><b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'>Call  us @:</span></b><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif;color:rgb(38,38,38)'> +91-9532392409<br><b>Visit us @:</b>  <a href='http://www.nirbhaypratapsingh.com' target='_blank'>www.nirbhaypratapsingh.com</a></span><span style='font-family:&#39;Microsoft New Tai Lue&#39;,sans-serif'><a href='http://www.google.com/url?q=http%3A%2F%2Fwww.silvereye.co&amp;sa=D&amp;sntz=1&amp;usg=AFQjCNEkmf2lpjcs1nZBAD2YV2lQ3jGqDw' style='color:rgb(17,85,204)' target='_blank'><span style='color:blue'></span></a></span></p></div></div></div></div></div></div>";
 // mail_cont=mail_cont.replace('"', '\'');
 // System.out.println("$$$$$$$$$$$$$$="+mail_cont);
  if( msg[i].getContentType().contains("text/plain;"))
  {
	  mail_cont="<pre>"+mail_cont+"</pre>";  
  }
 /* else
  {
	  mail_cont=mail_cont.replaceAll("<pre", "<span")  ;
	  mail_cont=mail_cont.replaceAll("</pre>", "</span>")  ;
  }*/
  inb.setMailContent(mail_cont.trim());
  inb.setInlineimgsize(""+inline);
  inline=0;
  //mail_cont="";
  
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	  //	status=false;
	  e.printStackTrace();
	  inb.setErrorCnt("<nps>");
        }
        
catch(Exception e)
        {
		//status=false;
	e.printStackTrace();
	 inb.setErrorCnt("<nps>");
        }
        
                    
  return  inb;        
}
	
	
	
	
	public String listWebmailHeader(String host, String port, String id, String pass, String uid, String foldername)
	{
		String mailheader="";
		
		IMAPFolder folder = null;
        Store store = null;
       // Flag flag = null;
        //mail_cont="";
       // flg=0;
        try 
        {
        	store=Connections.imapConnectionNP( host,  port,  id,  pass);
			IMAPStore imapStore = (IMAPStore) store; 
			folder = (IMAPFolder) store.getFolder(foldername); //This works for both email account
    
if (!folder.exists()) {
	// inboxlist.setGetInboxByUid(inb);
    // return   inboxlist;        
  	}
  folder.open(Folder.READ_ONLY);
  long [] arr={Long.parseLong(uid)};
  Message[] msg =folder.getMessagesByUID(arr);

 for (int i = 0; i< msg.length; i++)
  {
	
	
	 Enumeration headers = msg[i].getAllHeaders();
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	 String tmp=h.getName() + ":&nbsp; " + h.getValue();
	 tmp=tmp.replace("<", "&lt;");
	 tmp=tmp.replace(">", "&gt;");
	 /*try {
			tmp=MimeUtility.decodeText(tmp);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
*/
	 
	 mailheader=mailheader+tmp+"<br />";
	  }
  }
       
        folder.close(true);
        store.close();
        }
        catch(MessagingException e)
              {
      	  //	status=false;
              }
              
      catch(Exception e)
              {
      		//status=false;
              }
		
		return mailheader;
	}
	
	
	 /*
	   * This method checks for content-type 
	   * based on which, it processes and
	   * fetches the content of the message
	   */
	   public static void writePart(Part p) throws Exception {
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
	         mail_cont="<pre>" +p.getContent()+"</pre>";
	    	  }
	      } 
	      else if (p.isMimeType("text/html")) {
	    	  
	    	  if(flg!=2)
	    	  {
		       // System.out.println("This is plain text");
		      //  System.out.println("---------------------------flg="+flg);
		      //   System.out.println((String) p.getContent());
		         mail_cont=(String) p.getContent();
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
	            writePart(mp.getBodyPart(i));
	      } 
	      //check if the content is a nested message
	      else if (p.isMimeType("message/rfc822")) {
	        // System.out.println("This is a Nested Message");
	        // System.out.println("---------------------------");
	         writePart((Part) p.getContent());
	      } 
	     //check if the content is an inline image
	    /*  else if (p.isMimeType("image/jpeg")) {
	    	  
	    	 
	    	  
	         System.out.println("--------> image/jpeg");
	         Object o = p.getContent();

	         InputStream x = (InputStream) o;
	         // Construct the required byte array
	         System.out.println("x.length = " + x.available());
	         int i = 0;
	         byte[] bArray = new byte[x.available()];

	         while ((i = (int) ((InputStream) x).available()) > 0) {
	            int result = (int) (((InputStream) x).read(bArray));
	            if (result == -1)
	            break;
	         }
	        
	         FileOutputStream f2 = new FileOutputStream("/tmp/"+ p.getFileName());
	         f2.write(bArray);
	         
	         mail_cont+="<img src='C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\" + p.getFileName() + "' />";
	      } */
	      else if (p.getContentType().contains("image/")) {
	    	  System.out.println("content type"+p.getContentType());
	    	  
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
	    		  con_id=con_id.replaceAll("@", "_");
	    		  con_id=con_id.replaceAll("\\.", "_");
	    		  //con_id="cid:"+con_id; 
	    	  }
	    	  
	    	  
	    	  
	    
	    	// File f = new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+p.getFileName());
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
       	  // String inline_id="inline_img_"+inline;
	    	mail_cont+="<input type='hidden' id='"+con_id+"' value='" +path_img+ "' />";
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
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	            mail_cont+=(String) o;
	         } 
	         else if (o instanceof InputStream) {
	            /*System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(c);*/
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	            mail_cont+=(String) o;
	         }
	   }

	}
	   /*
	   * This method would print FROM,TO and SUBJECT of the message
	   *
	   public static void writeEnvelope(Message m) throws Exception {
	      System.out.println("This is the message envelope");
	      System.out.println("---------------------------");
	      Address[] a;

	      // FROM
	      if ((a = m.getFrom()) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("FROM: " + a[j].toString());
	      }

	      // TO
	      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
	         for (int j = 0; j < a.length; j++)
	         System.out.println("TO: " + a[j].toString());
	      }

	      // SUBJECT
	      if (m.getSubject() != null)
	         System.out.println("SUBJECT: " + m.getSubject());

	   }


	   */

}
