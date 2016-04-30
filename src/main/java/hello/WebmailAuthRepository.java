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
import com.webmail.quota.Webmailquota;

import edms.core.Config;

@Component
public class WebmailAuthRepository {


/*	@Autowired
	FolderService folderService;*/

	
	public int webmailAuthentication(String host, String port, String id, String pass)
	{
		
		int flag=0;
		flag=Connections.imapAuthNP( host,  port,  id,  pass);
	       
				
	      
	       
  return   flag;        
}
}
