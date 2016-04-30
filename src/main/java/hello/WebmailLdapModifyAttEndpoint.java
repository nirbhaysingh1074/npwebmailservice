package hello;



import com.webmail.ldapmodifyattribute.GetLdapModifyListAttRequest;
import com.webmail.ldapmodifyattribute.GetLdapModifyListAttResponse;
import com.webmail.ldapmodifyattribute.GetLdapModifyOneAttRequest;
import com.webmail.ldapmodifyattribute.GetLdapModifyOneAttResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailLdapModifyAttEndpoint 
{
	private static final String NAMESPACE_URI = "http://webmail.com/LdapModifyAttribute";

	private WebmailLdapModifyAttRepository webmailLdapModifyAttRepository;

	@Autowired
	public WebmailLdapModifyAttEndpoint(WebmailLdapModifyAttRepository webmailLdapModifyAttRepository) {
		this.webmailLdapModifyAttRepository = webmailLdapModifyAttRepository;
	}

	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLdapModifyOneAttRequest")
	@ResponsePayload
	public GetLdapModifyOneAttResponse getLdapModOneAtt(@RequestPayload GetLdapModifyOneAttRequest request) {
		GetLdapModifyOneAttResponse response = new GetLdapModifyOneAttResponse();
		response.setGetStatus(webmailLdapModifyAttRepository.modifyOneLdapAtt(request.getWebmailUrl(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailBase(), request.getWebmailAttName(), request.getWebmailAttValue()));
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLdapModifyListAttRequest")
	@ResponsePayload
	public GetLdapModifyListAttResponse getLdapModListAtt(@RequestPayload GetLdapModifyListAttRequest request) {
		GetLdapModifyListAttResponse response = new GetLdapModifyListAttResponse();
		response.setGetStatus(webmailLdapModifyAttRepository.modifyListLdapAtt(request.getWebmailUrl(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailBase(), request.getWebmailAttList(), request.getWebmailAttListValue()));
		return response;
	}
	
	
	
}
