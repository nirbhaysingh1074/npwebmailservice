package hello;

import com.webmail.authentication.GetWebmailAuthRequest;
import com.webmail.authentication.GetWebmailAuthResponse;
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
public class WebmailAuthEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Authentication";

	private WebmailAuthRepository webmailAuthRepository;

	@Autowired
	public WebmailAuthEndpoint(WebmailAuthRepository webmailAuthRepository) {
		this.webmailAuthRepository = webmailAuthRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailAuthRequest")
	@ResponsePayload
	public GetWebmailAuthResponse getMailAuth(@RequestPayload GetWebmailAuthRequest request) {
		GetWebmailAuthResponse response = new GetWebmailAuthResponse();
		response.setGetWebmailAuth(webmailAuthRepository.webmailAuthentication(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword()));
		return response;
	}
	
	
}
