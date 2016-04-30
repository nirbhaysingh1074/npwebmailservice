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

import java.io.*;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.TransientRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.edms.service.FolderService;
import com.sun.mail.imap.IMAPStore;
import com.webmail.imapquota.Imapquota;
import com.webmail.quota.Webmailquota;

import edms.core.Config;

@Component
public class WebmailImapquotaRepository {


/*	@Autowired
	FolderService folderService;*/

	
	public Imapquota webmailImapquota(String host,String id, String pass)
	{
		
		//long flag=0;
		Imapquota iqt=new Imapquota();
		
		
            try {
                Store store = null;
                store=Connections.imapConnectionSmallNP(host, id, pass);
             //   out.println(store);
              IMAPStore imapStore = (IMAPStore) store;
             
              Quota[] quotas = imapStore.getQuota("INBOX");

    for (Quota quota : quotas) {
       //out.println(String.format("<br>quotaRoot:'%s'", quota.quotaRoot));

        for (Quota.Resource resource : quota.resources) {
        //   out.println(String.format("<br>name:'%s', limit:'%s', usage:'%s'",  resource.name, resource.limit, resource.usage));
        iqt.setQuotalimit( resource.limit);
        iqt.setQuotauses( resource.usage);
        //	flag= resource.limit;
        }
    }
    imapStore.close();
             }
              catch(Exception e)
  {
 // out.print(e);
  }  
  return   iqt;        
}
}
