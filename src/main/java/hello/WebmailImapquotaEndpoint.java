package hello;

import com.webmail.authentication.GetWebmailAuthRequest;
import com.webmail.authentication.GetWebmailAuthResponse;
import com.webmail.imapquota.GetWebmailImapquotaRequest;
import com.webmail.imapquota.GetWebmailImapquotaResponse;
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
public class WebmailImapquotaEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Imapquota";

	private WebmailImapquotaRepository webmailImapquotaRepository;

	@Autowired
	public WebmailImapquotaEndpoint(WebmailImapquotaRepository webmailImapquotaRepository) {
		this.webmailImapquotaRepository = webmailImapquotaRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailImapquotaRequest")
	@ResponsePayload
	public GetWebmailImapquotaResponse getMailAuth(@RequestPayload GetWebmailImapquotaRequest request) {
		GetWebmailImapquotaResponse response = new GetWebmailImapquotaResponse();
		//response.setGetWebmailAut(webmailAuthRepository.webmailAuthentication(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword()));
		//response.setGetWebmailImapquota(webmailImapquotaRepository.webmailImapquota(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		response.setImapquota(webmailImapquotaRepository.webmailImapquota(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		return response;
	}
	
	
}
