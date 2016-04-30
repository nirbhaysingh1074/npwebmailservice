package hello;

import com.webmail.inbox.GetInboxMailDescRequest;
import com.webmail.inbox.GetInboxMailRequest;
import com.webmail.inbox.GetMailInboxDescResponse;
import com.webmail.inbox.GetMailInboxResponse;
import com.webmail.ldapdirectory.GetLdapDirectoryRequest;
import com.webmail.ldapdirectory.GetLdapDirectoryResponse;
import com.webmail.quota.*;
import com.edms.folder.GetFolderResponse;
import com.edms.folder.HasChildRequest;
import com.edms.folder.HasChildResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailLdapDirectoryEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/LdapDirectory";

	private WebmailLdapDirectoryRepository webmailLdapDirectoryRepository;

	@Autowired
	public WebmailLdapDirectoryEndpoint(WebmailLdapDirectoryRepository webmailLdapDirectoryRepository) {
		this.webmailLdapDirectoryRepository = webmailLdapDirectoryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLdapDirectoryRequest")
	@ResponsePayload
	public GetLdapDirectoryResponse getLdapDirUsers(@RequestPayload GetLdapDirectoryRequest request)
	{
		System.out.println("Directory end point");
		GetLdapDirectoryResponse response = new GetLdapDirectoryResponse();
		response.setDirUserListReturn(webmailLdapDirectoryRepository.listDirectoryUsers(request.getWebamilUrl(), request.getWebamilId(), request.getWebamilPassword(), request.getWebmailBase()));
		return response;
	}
	
	
	
}
