package hello;

import com.webmail.flagaction.RemoveWebmailFlagRequest;
import com.webmail.flagaction.RemoveWebmailFlagResponse;
import com.webmail.flagaction.SetWebmailFlagResponse;
import com.webmail.flagaction.SetWebmailFlageRequest;
import com.webmail.movetrash.GetWebmailMoveTrashRequest;
import com.webmail.movetrash.GetWebmailMoveTrashResponse;
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
public class WebmailFlagActionEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Flagaction";

	private WebmailFlagActionRepository webmailFlagActionRepository;

	@Autowired
	public WebmailFlagActionEndpoint(WebmailFlagActionRepository webmailFlagActionRepository) {
		this.webmailFlagActionRepository = webmailFlagActionRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "setWebmailFlageRequest")
	@ResponsePayload
	public SetWebmailFlagResponse setMailFlag(@RequestPayload SetWebmailFlageRequest request) {
		SetWebmailFlagResponse response = new SetWebmailFlagResponse();
		response.setSetFlagStatus(webmailFlagActionRepository.setWebmailFlag(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebmailFolder(), request.getWebamilUids()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeWebmailFlagRequest")
	@ResponsePayload
	public RemoveWebmailFlagResponse removeMailFlag(@RequestPayload RemoveWebmailFlagRequest request) {
		RemoveWebmailFlagResponse response = new RemoveWebmailFlagResponse();
		response.setRemoveFlagStatus(webmailFlagActionRepository.removeWebmailFlag(request.getWebmailHost(), request.getWebmailPost(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolder(), request.getWebmailUids()));
		return response;
	}
	
	
}
