package hello;

import com.webmail.compose.GetComposeMailRequest;
import com.webmail.compose.GetComposeMailResponse;
import com.webmail.compose.GetSaveMailDraftRequest;
import com.webmail.compose.GetSaveMailDraftResponse;
import com.webmail.compose.GetWebmailDeleteDraftRequest;
import com.webmail.compose.GetWebmailDeleteDraftResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailComposeEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Compose";

	private WebmailComposeRepository webmailComposeRepository;

	@Autowired
	public WebmailComposeEndpoint(WebmailComposeRepository webmailComposeRepository) {
		this.webmailComposeRepository = webmailComposeRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getComposeMailRequest")
	@ResponsePayload
	public GetComposeMailResponse composeMail(@RequestPayload GetComposeMailRequest request) {
		GetComposeMailResponse response = new GetComposeMailResponse();
		System.out.println("endpoint="+request.getWebamilHost());
		response.setSetComposeStatus(webmailComposeRepository.sendComposeMail(request.getWebmailOldMessageID(), request.getWebmailReferences(), request.getWebamilIp(),request.getWebmailXMailer(),request.getWebmailDraftUid(), request.getWebamilHost(), request.getWebamilPort(),request.getWebamilIMAPPort(),request.isWebamilSaveSent(), request.getWebamilId(), request.getWebamilPassword(),request.getWebamilFromName(),request.getWebamilTo(), request.getWebamilCc(), request.getWebamilBcc(), request.getWebamilSubject(), request.getWebamilBodyContent(), request.isWebamilAttach(), request.getAttachmentFileName(), request.getAttachmentFileNewName(), request.getWebamilReadRec(), request.getWebamilPriority(), request.getWebmailTextType()));
		return response;
	}
	
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSaveMailDraftRequest")
	@ResponsePayload
	public GetSaveMailDraftResponse draftMail(@RequestPayload GetSaveMailDraftRequest request) {
		GetSaveMailDraftResponse response = new GetSaveMailDraftResponse();
		//System.out.println("endpoint="+request.getWebamilHost());
		response.setSetMailID(webmailComposeRepository.saveMailInDraft(request.getWebmailIp(),request.getWebmailXMailer(), request.getWebamilHost(), request.getWebmailPort(), request.getWebmailIMAPPort(), request.getWebmailUid(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFromName(), request.getWebmailTo(), request.getWebmailCc(), request.getWebmailBcc(), request.getWebmailSubject(), request.getWebmailBodyContent(), request.isWebmailAttach(), request.getAttachmentFileName(), request.getAttachmentFileNewName(),  request.getWebmailTextType())); 
		return response;
	}
	
	
	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailDeleteDraftRequest")
	@ResponsePayload
	public GetWebmailDeleteDraftResponse WebmailDeleteDraft(@RequestPayload GetWebmailDeleteDraftRequest request) {
		GetWebmailDeleteDraftResponse response = new GetWebmailDeleteDraftResponse();
		response.setDeleteDraftStatus(webmailComposeRepository.deleteDraft(request.getWebmailHost(), request.getWebmailPort(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailUids()));
		return response;
	}
}
