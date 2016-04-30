package hello;

import com.webmail.ldapattribute.GetLdapFNameRequest;
import com.webmail.ldapattribute.GetLdapFNameResponse;
import com.webmail.ldapuserdirectory.GetLdapUserDirectoryRequest;
import com.webmail.ldapuserdirectory.GetLdapUserDirectoryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailLdapUserDirectoryEndpoint 
{
	private static final String NAMESPACE_URI = "http://webmail.com/LdapUserDirectory";

	private WebmailLdapUserDirectoryRepository webmailLdapUserDirectoryRepository;

	@Autowired
	public WebmailLdapUserDirectoryEndpoint(WebmailLdapUserDirectoryRepository webmailLdapUserDirectoryRepository) {
		this.webmailLdapUserDirectoryRepository = webmailLdapUserDirectoryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetLdapUserDirectoryRequest")
	@ResponsePayload
	public GetLdapUserDirectoryResponse setMailFlag(@RequestPayload GetLdapUserDirectoryRequest request) {
		GetLdapUserDirectoryResponse response = new GetLdapUserDirectoryResponse();
		response.setDirUserListReturn(webmailLdapUserDirectoryRepository.listLdapUserDirectory(request.getWebmailUrl(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailBase()));
		return response;
}
	
	
	
	
}
