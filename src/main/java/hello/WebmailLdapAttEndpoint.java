package hello;

import com.webmail.ldapattribute.GetContactDetailRequest;
import com.webmail.ldapattribute.GetContactDetailResponse;
import com.webmail.ldapattribute.GetLdapFNameRequest;
import com.webmail.ldapattribute.GetLdapFNameResponse;
import com.webmail.ldapattribute.GetLdapOneAttOtrUserRequest;
import com.webmail.ldapattribute.GetLdapOneAttOtrUserResponse;
import com.webmail.ldapattribute.GetVCFLdapDirRequest;
import com.webmail.ldapattribute.GetVCFLdapDirResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailLdapAttEndpoint 
{
	private static final String NAMESPACE_URI = "http://webmail.com/LdapAttribute";

	private WebmailLdapAttRepository webmailLdapAttRepository;

	@Autowired
	public WebmailLdapAttEndpoint(WebmailLdapAttRepository webmailLdapAttRepository) {
		this.webmailLdapAttRepository = webmailLdapAttRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLdapOneAttOtrUserRequest")
	@ResponsePayload
	public GetLdapOneAttOtrUserResponse getLdapOneAttOtrUserRequest(@RequestPayload GetLdapOneAttOtrUserRequest request) {
		GetLdapOneAttOtrUserResponse response = new GetLdapOneAttOtrUserResponse();
		response.setGetLdapAttr(webmailLdapAttRepository.getLdapAttOtrUser(request.getWebmailUrl(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailBase(), request.getWebmailSearchBase(), request.getWebmailAttName()));
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLdapFNameRequest")
	@ResponsePayload
	public GetLdapFNameResponse setMailFlag(@RequestPayload GetLdapFNameRequest request) {
		GetLdapFNameResponse response = new GetLdapFNameResponse();
		response.setGetFName(webmailLdapAttRepository.getLdapAttFname(request.getWebamilUrl(), request.getWebamilId(), request.getWebamilPassword(), request.getWebmailBase(), request.getWebamilAttName()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVCFLdapDirRequest")
	@ResponsePayload
	public GetVCFLdapDirResponse getLdapDirAtt(@RequestPayload GetVCFLdapDirRequest request)
	{
		GetVCFLdapDirResponse response = new GetVCFLdapDirResponse();
		response.setGetVCFLdapDirByParentFile(webmailLdapAttRepository.getVCFLdapDirAtt(request.getWebmailDirUrl(), request.getWebmailDirId(), request.getWebmailDirPassword(), request.getWebmailDirBase(), request.getWebmailSearchBase()));
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContactDetailRequest")
	@ResponsePayload
	public GetContactDetailResponse getContactFullDetail(@RequestPayload GetContactDetailRequest request)
	{
		GetContactDetailResponse response = new GetContactDetailResponse();
		response.setGetContactFullDetail(webmailLdapAttRepository.getFullDetailContact(request.getWebmailDetailUrl(), request.getWebmailDetailId(), request.getWebmailDetailPassword(), request.getWebmailDetailBase(), request.getWebmailDetailSearchId()));
		return response;
	}
	
	
}
