package hello;

import javax.annotation.PostConstruct;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import java.util.ArrayList;
import java.util.List;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.TransientRepository;

import com.edms.folder.ArrayOfFolders;
import com.edms.folder.Folder;
import com.edms.folder.FolderListReturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.edms.service.FolderService;

import edms.core.Config;

@Component
public class FolderRepository {
	private static final List<Country> countries = new ArrayList<Country>();

/*	@Autowired
	FolderService folderService;*/

	@PostConstruct
	public void initData() {
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);

		countries.add(spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency(Currency.PLN);
		poland.setPopulation(38186860);

		countries.add(poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency(Currency.GBP);
		uk.setPopulation(63705000);

		countries.add(uk);
	}

	public FolderListReturn listFolder(String name) {
		Assert.notNull(name);
		FolderListReturn folderList1 = new FolderListReturn();
		ArrayOfFolders folders = new ArrayOfFolders();

		// TODO Auto-generated method stub
		Repository repository = new TransientRepository();
		Session jcrsession=null;
		try {
			jcrsession = repository.login(new SimpleCredentials(
					Config.JCR_USERNAME, Config.JCR_PASSWORD.toCharArray()));

			Node root = jcrsession.getRootNode();
			if (name.length() > 1) {
				root = root.getNode(name.substring(1));
			}
			/*Node hello = root.addNode("hello");
			jcrsession.save();*/
			for (NodeIterator nit =  root.getNodes(); nit
					.hasNext();) {
				Node node = nit.nextNode();
				Folder folder = new Folder();
				folder.setFolderName(node.getName());
				System.out.println("path is : " + node.getPath());
				folder.setFolderPath(node.getPath());
				folders.getFolderList().add(folder);
			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jcrsession.logout();
		}
		folderList1.setFolderListResult(folders);
		folderList1.setSuccess(true);
		return folderList1;
	}

	public Boolean hasChild(String folderPath) {
		// TODO Auto-generated method stub
		Repository repository = new TransientRepository();
		Session jcrsession=null;
		boolean flag=false;
		try {
			jcrsession = repository.login(new SimpleCredentials(
					Config.JCR_USERNAME, Config.JCR_PASSWORD.toCharArray()));
		/*	if (!node.getName().equals("jcr:system")
					&& (!node.getProperty(JcrConstants.JCR_PRIMARYTYPE)
							.getString().equals(JcrConstants.NT_RESOURCE))) {*/
			Node root = jcrsession.getRootNode();
			if (folderPath.length() > 1) {
				root = root.getNode(folderPath.substring(1));
			}
			flag=root.hasNodes();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jcrsession.logout();
		}
		
		return flag;
	}
}
