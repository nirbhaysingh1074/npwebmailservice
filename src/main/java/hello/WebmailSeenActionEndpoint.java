package hello;

import com.webmail.flagaction.RemoveWebmailFlagRequest;
import com.webmail.flagaction.RemoveWebmailFlagResponse;
import com.webmail.flagaction.SetWebmailFlagResponse;
import com.webmail.flagaction.SetWebmailFlageRequest;
import com.webmail.movetrash.GetWebmailMoveTrashRequest;
import com.webmail.movetrash.GetWebmailMoveTrashResponse;
import com.webmail.quota.*;
import com.webmail.seenaction.SetWebmailSeenRequest;
import com.webmail.seenaction.SetWebmailSeenResponse;
import com.webmail.seenaction.SetWebmailUnSeenRequest;
import com.webmail.seenaction.SetWebmailUnSeenResponse;
import com.edms.folder.GetFolderResponse;
import com.edms.folder.HasChildRequest;
import com.edms.folder.HasChildResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailSeenActionEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Seenaction";

	private WebmailSeenActionRepository webmailSeenActionRepository;

	@Autowired
	public WebmailSeenActionEndpoint(WebmailSeenActionRepository webmailSeenActionRepository) {
		this.webmailSeenActionRepository = webmailSeenActionRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "setWebmailSeenRequest")
	@ResponsePayload
	public SetWebmailSeenResponse setMailSeen(@RequestPayload SetWebmailSeenRequest request) {
		SetWebmailSeenResponse response = new SetWebmailSeenResponse();
		response.setSetWebmailSeenStatus(webmailSeenActionRepository.setWebmailSeen(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebmailFolder(), request.getWebamilUids()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "setWebmailUnSeenRequest")
	@ResponsePayload
	public SetWebmailUnSeenResponse setMailUnSeen(@RequestPayload SetWebmailUnSeenRequest request) {
		SetWebmailUnSeenResponse response = new SetWebmailUnSeenResponse();
		response.setSetWebmailUnSeenStatus(webmailSeenActionRepository.setWebmailUnSeen(request.getWebmailHost(), request.getWebmailPort(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolder(), request.getWebmailUids()));
		return response;
	}
	
	
}
