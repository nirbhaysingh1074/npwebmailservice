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
import org.springframework.web.util.HtmlUtils;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.webmail.mailattach.ReplyDisplay;


@Component
public class WebmailAttachmentRepository {


	 private boolean textIsHtml = false;
	static String mail_cont="";
	static String mail_cont_otr="";
	static int flg=0;
	  static int inline=0;
	public ReplyDisplay listWebmailAttachment(String host, String port, String id, String pass, String uid, String foldername)
	{
		ReplyDisplay inb= new ReplyDisplay();
		
		
		
		  List<String> flnm= new ArrayList<String>();
		    List<String> flnewnm= new ArrayList<String>();
		    List<String> flsz= new ArrayList<String>();
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
int umsg= folder.getUnreadMessageCount();
 for (int i = 0; i< msg.length; i++)
  {
	 
	 Enumeration headers = msg[i].getAllHeaders();
	 int hd=0;
	 while (headers.hasMoreElements()) {
	 Header h = (Header) headers.nextElement();
	 /* out.print("<br>%%%%%%% "+h.getName() + ": " + h.getValue());*/
	  if(h.getName().equalsIgnoreCase("Message-ID"))
	  {
		  hd++;
		  inb.setMessageID(h.getValue());
		 
	  }
	  
	  if(h.getName().equalsIgnoreCase("References"))
	  {
		  hd++;
		  inb.setReferences(h.getValue());
		 
	  }
	  if(hd>1)
	  {
		  break; 
	  }
	  }
	 
	 
	 String from = InternetAddress.toString(msg[i].getFrom());
	  inb.setFromId(from);
	 
	  String repTo= InternetAddress.toString( msg[i].getReplyTo());
	 if(repTo==null)
	 {
		 repTo="";
	 }
	  inb.setReplyToId(repTo);
	  
	  List<String> to_lst= new  ArrayList<String>();
	  Address arr_to[]=msg[i].getRecipients(Message.RecipientType.TO);
	  if(arr_to!=null)
	  {
	  for(int toi=0; toi< arr_to.length; toi++)
	  {
		  if(arr_to[toi].toString()!=null)
		  to_lst.add( HtmlUtils.htmlEscape(MimeUtility.decodeText(arr_to[toi].toString())));
	  }
	  }
	  inb.getToId().addAll(to_lst);
	  
	  List<String> cc_lst= new  ArrayList<String>();
	  Address arr_cc[]=msg[i].getRecipients(Message.RecipientType.CC);
	  if(arr_cc!=null)
	  {
	  for(int cci=0;cci< arr_cc.length; cci++)
	  {
		  if(arr_cc[cci].toString()!=null)
		  cc_lst.add( HtmlUtils.htmlEscape(MimeUtility.decodeText(arr_cc[cci].toString())));
	  }
	  }
	  inb.getCCId().addAll(cc_lst);
	 
	  List<String> bcc_lst= new  ArrayList<String>();
	  Address arr_bcc[]=msg[i].getRecipients(Message.RecipientType.BCC);
	  if(arr_bcc!=null)
	  {
	  for(int bcci=0;bcci< arr_bcc.length; bcci++)
	  {
		  if(arr_bcc[bcci].toString()!=null)
		  bcc_lst.add(HtmlUtils.htmlEscape(MimeUtility.decodeText(arr_bcc[bcci].toString())));
	  }
	  }
	  inb.getBCCId().addAll(bcc_lst);
	  
	 
	  
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

  String attch="No";
 // String mail_cont="";
  
  Message message1 = msg[i];
  //System.out.println("---------------------------------");
  writePart(message1);
  
  
  if(msg[i].getContentType().contains("text/html;") || msg[i].getContentType().contains("text/plain;"))
  {
	 /* String cnt=msg[i].getContent().toString();
  	  
  		mail_cont=cnt;
  	  */
  }
  else
  {
	 
if (content instanceof Multipart)  
{  
    Multipart multipart = (Multipart)content;  
  
    
    for (int j = 0; j < multipart.getCount(); j++) {

        BodyPart bodyPart = multipart.getBodyPart(j);
      
        
        String disposition = bodyPart.getDisposition();

          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) 
          	{ 
        	  attch="Yes";
              DataHandler handler = bodyPart.getDataHandler();
              flsz.add(""+bodyPart.getSize());
              InputStream inputStream = bodyPart.getInputStream();
              String filenm=handler.getName();
              flnm.add(filenm);
              String newfilenm=java.util.UUID.randomUUID()+"_"+filenm;
              flnewnm.add(newfilenm);
            
           // read this file into InputStream
      		//inputStream = new FileInputStream("/Users/mkyong/Downloads/holder.js");
       
      		// write the inputStream to a FileOutputStream
           //  OutputStream outputStream =  new FileOutputStream(new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+newfilenm));
              OutputStream outputStream =  new FileOutputStream(new File("/tmp/tomcat7-tomcat7-tmp/"+newfilenm));
      		int read = 0;
      		byte[] bytes = new byte[1024];
       
      		while ((read = inputStream.read(bytes)) != -1)
      		{
      			outputStream.write(bytes, 0, read);
      		}
                
           
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
 inb.setAttachStatus(attch);
 inb.getAttachmentNameList().addAll(flnm);
 inb.getAttachmentNewNameList().addAll(flnewnm);
 inb.getAttachmentSizeList().addAll(flsz);
  }
 
  folder.close(true);
  store.close();
  }
  catch(MessagingException e)
        {
	    e.printStackTrace();
        }
  catch(IOException e)
        {
	  e.printStackTrace();
        }    
  catch(Exception e)
        {
	  e.printStackTrace();
        }
  return   inb;        
}
	
	
	
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
		    	  
		    	  
		    	  
		    	 
		    //	File f = new File("C:\\Users\\nirbhay\\AppData\\Local\\Temp\\"+p.getFileName());
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
	       	   mail_cont=mail_cont.replace(con_id, path_img);
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
	      else {
	         Object o = p.getContent();
	         if (o instanceof String) {
	            System.out.println("This is a string");
	            System.out.println("---------------------------");
	            System.out.println((String) o);
	            mail_cont+=(String) o;
	         } 
	         else if (o instanceof InputStream) {
	            System.out.println("This is just an input stream");
	            System.out.println("---------------------------");
	            InputStream is = (InputStream) o;
	            is = (InputStream) o;
	            int c;
	            while ((c = is.read()) != -1)
	               System.out.write(c);
	         } 
	         else {
	            System.out.println("This is an unknown type");
	            System.out.println("---------------------------");
	            System.out.println(o.toString());
	            mail_cont+=(String) o;
	         }
	   }

	}

}
