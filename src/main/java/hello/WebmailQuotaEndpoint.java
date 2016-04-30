package hello;

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
public class WebmailQuotaEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Folder";

	private WebmailQuotaRepository webmailQuotaRepository;

	@Autowired
	public WebmailQuotaEndpoint(WebmailQuotaRepository webmailQuotaRepository) {
		this.webmailQuotaRepository = webmailQuotaRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailQuotaRequest")
	@ResponsePayload
	public GetWebmailQuotaResponse getMailQuota(@RequestPayload GetWebmailQuotaRequest request) {
		GetWebmailQuotaResponse response = new GetWebmailQuotaResponse();
		//response.setGetWebmailFolder(webmailFolderRepository.listWebmailFolder(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword()));
		response.setQuotaLimit(webmailQuotaRepository.listWebmailQuota(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		//response.setWebmailquota(webmailQuotaRepository.listWebmailQuota(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		return response;
	}
	
	/*@PayloadRoot(namespace = NAMESPACE_URI, localPart = "hasChildRequest")
	@ResponsePayload
	public HasChildResponse hasChild(@RequestPayload HasChildRequest request) {
		HasChildResponse response = new HasChildResponse();
		response.setHasChild(folderRepository.hasChild(request.getFolderPath()));
		return response;
	}*/
	
}
