package hello;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.*;

import org.springframework.stereotype.Component;

import com.sort.NPFolderCompare;
import com.webmail.folder.ArrayOfMailFolder;
import com.webmail.folder.ArrayOfSubsFolder;
import com.webmail.folder.ArrayOfSubsSubFolder;
import com.webmail.folder.MailFolderListReturn;
import com.webmail.folder.MailImapFolders;
import com.webmail.folder.SubsFolderListReturn;
import com.webmail.folder.SubsImapFolders;
import com.webmail.folder.SubsImapSubFolders;
import com.webmail.folder.SubsSubFolderListReturn;
import com.webmail.inbox.ArrayOfInboxMail;
import com.webmail.inbox.InboxListReturn;


@Component
public class WebmailFolderRepository {
	
	
	public boolean createNewFolder(String host,  String id, String pass, String newfldrnm)
	{
		
		boolean isCreated=true;
		if( !newfldrnm.equalsIgnoreCase("Inbox") && !newfldrnm.equalsIgnoreCase("Drafts") && !newfldrnm.equalsIgnoreCase("Sent") && !newfldrnm.equalsIgnoreCase("Junk") && !newfldrnm.equalsIgnoreCase("Trash") && !newfldrnm.equalsIgnoreCase("Archive"))
		{
		
        Store store=null;
            try {
            	store=Connections.imapConnectionSmallNP(host, id, pass);
                javax.mail.Folder parent = store.getDefaultFolder(); 
                
                javax.mail.Folder newFolder = parent.getFolder(newfldrnm); 
                isCreated = newFolder.create(javax.mail.Folder.HOLDS_MESSAGES);   
                newFolder.setSubscribed(true);
        
                store.close();
    } catch (Exception e)   
    {   
    	
        e.printStackTrace();   
        isCreated = false;   
    } 
            finally
            {
            	try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
		else
		{
			  isCreated = false; 	
		}
		
		return isCreated;
	}
	
	
	
	public boolean deleteFolder(String host,  String id, String pass, String fldnm)
	{
		boolean isDeleted = true; 
		if( !fldnm.equalsIgnoreCase("Inbox") && !fldnm.equalsIgnoreCase("Drafts") && !fldnm.equalsIgnoreCase("Sent") && !fldnm.equalsIgnoreCase("Junk") && !fldnm.equalsIgnoreCase("Trash") && !fldnm.equalsIgnoreCase("Archive")) 
				{
		
        Store store=null;
            try {
            	store=Connections.imapConnectionSmallNP(host, id, pass);
                javax.mail.Folder parent = store.getDefaultFolder(); 
                javax.mail.Folder newFolder = parent.getFolder(fldnm); 
                if(newFolder.isSubscribed())
                {
                newFolder.setSubscribed(false);
                }
                isDeleted = newFolder.delete(true);
               
                store.close();
    } catch (Exception e)   
    {   
      
        e.printStackTrace();   
        isDeleted = false;   
    }  
            finally
            {
            	try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
   //System.out.println("!!!!!!!!!!delete fldr="+isCreated);
				}
		else
		{
			isDeleted = false; 
		}
		
		return isDeleted;
	}
	
	
	
	public boolean subscriptionFolder(String host,  String id, String pass, String fldnm, boolean sub)
	{
		boolean isSub = true; 
		
		if( !fldnm.equalsIgnoreCase("Inbox") && !fldnm.equalsIgnoreCase("Drafts") && !fldnm.equalsIgnoreCase("Sent") && !fldnm.equalsIgnoreCase("Junk") && !fldnm.equalsIgnoreCase("Trash") && !fldnm.equalsIgnoreCase("Archive") )
		{
			
		
        Store store=null;
            try {
            	store=Connections.imapConnectionSmallNP(host, id, pass);
                javax.mail.Folder parent = store.getDefaultFolder(); 
                javax.mail.Folder newFolder = parent.getFolder(fldnm); 
                newFolder.setSubscribed(sub);
               
                store.close();
    } catch (Exception e)   
    {   
      
        e.printStackTrace();   
        isSub = false;   
    }  
            finally
            {
            	try {
					store.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
		else
		{
			 isSub = false;
		}
   //System.out.println("!!!!!!!!!!delete fldr="+isCreated);
		return isSub;
		
	}

	
	
	
	public String listWebmailFolderSubscribed(String host,  String id, String pass)
	{
		String res="";
		
            try {
                Store store = null;
                
                store=Connections.imapConnectionSmallNP(host, id, pass);
                res="Inbox;0;Drafts;0;Sent;0;Junk;0;Trash;0;Archive;0";
                Folder[] f = store.getDefaultFolder().listSubscribed();
          		for(Folder fd:f)
          			{
          			Folder t[]=fd.listSubscribed();
          			
          			if(fd.getName().equalsIgnoreCase("Drafts") || fd.getName().equalsIgnoreCase("Inbox") || fd.getName().equalsIgnoreCase("Sent") || fd.getName().equalsIgnoreCase("Junk") || fd.getName().equalsIgnoreCase("Trash") || fd.getName().equalsIgnoreCase("Archive")  )
          			{
          				continue;
          			}
          			else
          			{
          				if(res.equals("") || res==null)
          				{
          					res=fd.getName()+";"+t.length;
          				}
          				else
          				{
          					res+=";"+fd.getName()+";"+t.length;
          				}
          			}
          			}
          		store.close();
            	}
            catch(Exception e)
            	{
            	//out.print(e);
            	}  
		return res;
	}
	
	
	
	public SubsFolderListReturn listWebmailFolderSubscribedOther(String host,  String id, String pass, String path)
	{
		SubsFolderListReturn inboxlist= new SubsFolderListReturn();
		ArrayOfSubsFolder inboxmail= new ArrayOfSubsFolder();
		if(path==null || path.equals(""))
		{
		
		ArrayOfSubsFolder inboxmail_in= new ArrayOfSubsFolder();
		ArrayOfSubsFolder inboxmail_1= new ArrayOfSubsFolder();
		ArrayOfSubsFolder inboxmail_2= new ArrayOfSubsFolder();
		boolean status=true;
		
            try {
                Store store = null;
                
                store=Connections.imapConnectionSmallNP(host, id, pass);
                Folder[] f = store.getDefaultFolder().listSubscribed();
          		for(Folder fd:f)
          			{
          			boolean chd=true;
          			Folder t[]=fd.listSubscribed();
          			if(t.length>0)
          			{
          				chd=true;	
          			}
          			else
          			{
          				chd=false;
          			}
          			int md=0;
          			md=fd.getType();
          			int mescnt=0;
          			if(md!=2)
          			{
          				mescnt=fd.getMessageCount();
          			}
          			SubsImapFolders imapfldr=new SubsImapFolders();
          			imapfldr.setFolderFullName(fd.getFullName());
          			imapfldr.setFolderName(fd.getName());
          			imapfldr.setFolderMode(md);
          			imapfldr.setHasChild(chd);
          			imapfldr.setMessageCount(mescnt);
          			
          				if(fd.getName().equalsIgnoreCase("Inbox"))
              			{
          					inboxmail_in.getSubsFolderList().add(imapfldr);
              			}
          				else if(fd.getName().equalsIgnoreCase("Drafts")   || fd.getName().equalsIgnoreCase("Sent") || fd.getName().equalsIgnoreCase("Junk") || fd.getName().equalsIgnoreCase("Trash") || fd.getName().equalsIgnoreCase("Archive")  )
              			{
          					inboxmail_1.getSubsFolderList().add(imapfldr);
              			}
              			else
              			{
              				inboxmail_2.getSubsFolderList().add(imapfldr);
              			}
          			}
          		store.close();
          		
          		inboxmail.getSubsFolderList().addAll(inboxmail_in.getSubsFolderList());
          		inboxmail.getSubsFolderList().addAll(inboxmail_1.getSubsFolderList());
          		inboxmail.getSubsFolderList().addAll(inboxmail_2.getSubsFolderList());
          		inboxlist.setSubsFolderListReturn(inboxmail);
          		inboxlist.setSuccess(true);
            	}
            catch(Exception e)
            	{
            	inboxlist.setSubsFolderListReturn(inboxmail);
          		inboxlist.setSuccess(false);
          		e.printStackTrace();
            	} 
            
		}
		else
		{
			boolean status=true;
			
	            try {
	                Store store = null;
	                
	                store=Connections.imapConnectionSmallNP(host, id, pass);
	                Folder[] f=store.getFolder(path).listSubscribed();
	          		for(Folder fd:f)
	          			{
	          			boolean chd=true;
	          			Folder t[]=fd.listSubscribed();
	          			if(t.length>0)
	          			{
	          				chd=true;	
	          			}
	          			else
	          			{
	          				chd=false;
	          			}
	          			int md=0;
	          			md=fd.getType();
	          			int mescnt=0;
	          			if(md!=2)
	          			{
	          				mescnt=fd.getMessageCount();
	          			}
	          			SubsImapFolders imapfldr=new SubsImapFolders();
	          			imapfldr.setFolderFullName(fd.getFullName());
	          			imapfldr.setFolderName(fd.getName());
	          			imapfldr.setFolderMode(md);
	          			imapfldr.setHasChild(chd);
	          			imapfldr.setMessageCount(mescnt);
	          			
	          			inboxmail.getSubsFolderList().add(imapfldr)	;
	          			}
	          		store.close();
	          		
	          		
	          		inboxlist.setSubsFolderListReturn(inboxmail);
	          		inboxlist.setSuccess(true);
	            	}
	            catch(Exception e)
	            	{
	            	inboxlist.setSubsFolderListReturn(inboxmail);
	          		inboxlist.setSuccess(false);
	          		e.printStackTrace();
	            	} 
		}
            
            
		return inboxlist;
	}
	
	
	
	
	
	
	
	public MailFolderListReturn listWebmailFolderOther(String host,  String id, String pass, String path)
	{
		MailFolderListReturn inboxlist= new MailFolderListReturn();
		ArrayOfMailFolder inboxmail= new ArrayOfMailFolder();
		if(path==null || path.equals(""))
		{
		
			ArrayOfMailFolder inboxmail_in= new ArrayOfMailFolder();
			ArrayOfMailFolder inboxmail_1= new ArrayOfMailFolder();
			ArrayOfMailFolder inboxmail_2= new ArrayOfMailFolder();
		boolean status=true;
		
		
            try {
                Store store =null;

                store=Connections.imapConnectionSmallNP(host, id, pass);
                Folder[] f = store.getDefaultFolder().list();
          		for(Folder fd:f)
          			{
          			boolean chd=true;
          			Folder t[]=fd.list();
          			if(t.length>0)
          			{
          				chd=true;	
          			}
          			else
          			{
          				chd=false;
          			}
          			int md=0;
          			md=fd.getType();
          			int mescnt=0;
          			if(md!=2)
          			{
          				mescnt=fd.getMessageCount();
          			}
          			MailImapFolders imapfldr=new MailImapFolders();
          			imapfldr.setFolderFullName(fd.getFullName());
          			imapfldr.setFolderName(fd.getName());
          			imapfldr.setFolderMode(md);
          			imapfldr.setHasChild(chd);
          			imapfldr.setMessageCount(mescnt);
          			imapfldr.setIsSubs(fd.isSubscribed());
          			
          				if(fd.getName().equalsIgnoreCase("Inbox"))
              			{
          					inboxmail_in.getMailFolderList().add(imapfldr);
              			}
          				else if(fd.getName().equalsIgnoreCase("Drafts")   || fd.getName().equalsIgnoreCase("Sent") || fd.getName().equalsIgnoreCase("Junk") || fd.getName().equalsIgnoreCase("Trash") || fd.getName().equalsIgnoreCase("Archive")  )
              			{
          					inboxmail_1.getMailFolderList().add(imapfldr);
              			}
              			else
              			{
              				inboxmail_2.getMailFolderList().add(imapfldr);
              			}
          			}
          		store.close();
          		
          		inboxmail.getMailFolderList().addAll(inboxmail_in.getMailFolderList());
          		inboxmail.getMailFolderList().addAll(inboxmail_1.getMailFolderList());
          		//inboxmail.getMailFolderList().addAll( inboxmail_2.getMailFolderList());
          		List<MailImapFolders> nplst=inboxmail_2.getMailFolderList();
          		Collections.sort(nplst,new NPFolderCompare());
          		inboxmail.getMailFolderList().addAll(nplst );
          		
          		inboxlist.setMailFolderListReturn(inboxmail);
          		inboxlist.setSuccess(true);
            	}
            catch(Exception e)
            	{
            	inboxlist.setMailFolderListReturn(inboxmail);
          		inboxlist.setSuccess(false);
          		e.printStackTrace();
            	} 
            
		}
		else
		{
			boolean status=true;
			
			
	            try {
	            	ArrayOfMailFolder inboxmail_1= new ArrayOfMailFolder();
	                Store store = null;
	                store=Connections.imapConnectionSmallNP(host, id, pass);
	                Folder[] f=store.getFolder(path).list();
	                
	          		for(Folder fd:f)
	          			{
	          			boolean chd=true;
	          			Folder t[]=fd.list();
	          			if(t.length>0)
	          			{
	          				chd=true;	
	          			}
	          			else
	          			{
	          				chd=false;
	          			}
	          			int md=0;
	          			md=fd.getType();
	          			int mescnt=0;
	          			if(md!=2)
	          			{
	          				mescnt=fd.getMessageCount();
	          			}
	          			MailImapFolders imapfldr=new MailImapFolders();
	          			imapfldr.setFolderFullName(fd.getFullName());
	          			imapfldr.setFolderName(fd.getName());
	          			imapfldr.setFolderMode(md);
	          			imapfldr.setHasChild(chd);
	          			imapfldr.setMessageCount(mescnt);
	          			imapfldr.setIsSubs(fd.isSubscribed());
	          			inboxmail_1.getMailFolderList().add(imapfldr);
	          			}
	          		store.close();
	          		
	          		List<MailImapFolders> nplst=inboxmail_1.getMailFolderList();
	          		Collections.sort(nplst,new NPFolderCompare());
	          		inboxmail.getMailFolderList().addAll(nplst );
	          		
	          		inboxlist.setMailFolderListReturn(inboxmail);
	          		inboxlist.setSuccess(true);
	            	}
	            catch(Exception e)
	            	{
	            	inboxlist.setMailFolderListReturn(inboxmail);
	          		inboxlist.setSuccess(false);
	          		e.printStackTrace();
	            	} 
		}
            
            
		return inboxlist;
	}
	
	
	
	
	
	
	
	
	
	public SubsSubFolderListReturn listWebmailSubFolderSubscribedOther(String host,  String id, String pass, String path)
	{
		String res="";
		SubsSubFolderListReturn inboxlist= new SubsSubFolderListReturn();
		ArrayOfSubsSubFolder inboxmail= new ArrayOfSubsSubFolder();
		
            try {
                Store store = null;
                store=Connections.imapConnectionSmallNP(host, id, pass);
                Folder[] f=store.getFolder(path).listSubscribed();
            	for(Folder fd:f)
          			{
            		
            		boolean chd=true;
          			Folder t[]=fd.listSubscribed();
          			if(t.length>0)
          			{
          				chd=true;	
          			}
          			else
          			{
          				chd=false;
          			}
          			int md=0;
          			md=fd.getType();
          			int mescnt=0;
          			if(md!=2)
          			{
          				mescnt=fd.getMessageCount();
          			}
          			SubsImapSubFolders imapfldr=new SubsImapSubFolders();
          			imapfldr.setFolderFullName(fd.getFullName());
          			imapfldr.setFolderName(fd.getName());
          			imapfldr.setFolderMode(md);
          			imapfldr.setHasChild(chd);
          			imapfldr.setMessageCount(mescnt);
          			inboxmail.getSubsSubFolderList().add(imapfldr);
     				}
            	inboxlist.setSubsSubFolderListReturn(inboxmail);
            	inboxlist.setSuccess(true);
          		store.close();
            	}
            catch(Exception e)
            	{
            	e.printStackTrace();
            	inboxlist.setSubsSubFolderListReturn(inboxmail);
            	inboxlist.setSuccess(false);
            	}  
       return inboxlist;
   }
	
	
	
	
	public String listWebmailSubFolderSubscribed(String host,  String id, String pass, String path)
	{
		String res="";
		
            try {
                Store store = null;
                store=Connections.imapConnectionSmallNP(host, id, pass);
                Folder[] f=store.getFolder(path).listSubscribed();
            	for(Folder fd:f)
          			{
            		Folder t[]=fd.listSubscribed();
      				if(res.equals("") || res==null)
      				{
      					res=fd.getName()+";"+t.length;
      				}
      				else
      				{
      					res+=";"+fd.getName()+";"+t.length;
      				}
     				}
          		store.close();
            	}
            catch(Exception e)
            	{
            	res+=e;	
            			
            	//out.print(e);
            	}  
       return res;
   }
	
	
	
	
	public String listWebmailFolder(String host,  String id, String pass)
	{
		String res="";
		
            try {
               
                Store store = null;
                store=Connections.imapConnectionSmallNP(host, id, pass);
                String res_Inbox="Inbox;0;true;";
                String res_Drafts= "Drafts;0;true;";
                String res_Sent= "Sent;0;true;";
                String res_Junk= "Junk;0;true;";
                String res_Trash= "Trash;0;true;";
                String res_Archive= "Archive;0;true;";
                Folder[] f = store.getDefaultFolder().list();
          		for(Folder fd:f)
          			{
          			Folder t[]=fd.list();
          			
          			if(fd.getName().equalsIgnoreCase("Drafts")   )
          			{
          				res_Drafts+=fd.getMessageCount();
          			}
          			else if(fd.getName().equalsIgnoreCase("Inbox"))
          			{
          				res_Inbox+=fd.getMessageCount();
          			}
          		  else if(fd.getName().equalsIgnoreCase("Sent") )
        			{
          			  	res_Sent+=fd.getMessageCount();
        			}
        		  else if(fd.getName().equalsIgnoreCase("Junk"))
        			{
        				res_Junk+=fd.getMessageCount();
        			}
        		  else if(fd.getName().equalsIgnoreCase("Trash"))
        			{
        				res_Trash+=fd.getMessageCount();
        			}
        		  else if(fd.getName().equalsIgnoreCase("Archive"))
        			{
        				res_Archive+=fd.getMessageCount();
        			}
        		  else
          			{
          				if(res.equals("") || res==null)
          				{
          					res=fd.getName()+";"+t.length+";"+fd.isSubscribed()+";";
          				}
          				else
          				{
          					res+=";"+fd.getName()+";"+t.length+";"+fd.isSubscribed()+";";
          				}
          				int cont=0;
          				if(fd.getType()!=2)
          				{
          					cont=fd.getMessageCount();
          				}
          				res+=cont;
          			}
          			}
          		
          		res= res_Inbox+";"+ res_Drafts+";"+ res_Sent+";"+ res_Junk+";"+res_Trash+";"+res_Archive+";"+res;
          		store.close();
            	}
            catch(Exception e)
            	{
            	//out.print(e);
            	}  
		return res;
	}
	
	
	public String listWebmailSubFolder(String host,  String id, String pass, String path)
	{
		String res="";
	
            try {
                Store store = null;
                store=Connections.imapConnectionSmallNP(host, id, pass);
                Folder[] f=store.getFolder(path).list();
            	for(Folder fd:f)
          			{
            		Folder t[]=fd.list();
      				if(res.equals("") || res==null)
      				{
      					res=fd.getName()+";"+t.length+";"+fd.isSubscribed()+";";
      				}
      				else
      				{
      					res+=";"+fd.getName()+";"+t.length+";"+fd.isSubscribed()+";";
      				}
      				int cont=0;
      				if(fd.getType()!=2)
      				{
      					cont=fd.getMessageCount();
      				}
      				res+=cont;
      				
      				
     				}
            
          		store.close();
            	}
            catch(Exception e)
            	{
            	res+=e;	
            			
            	//out.print(e);
            	}  
       return res;
   }
	
}
