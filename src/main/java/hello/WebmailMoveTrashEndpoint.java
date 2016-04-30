package hello;

import com.webmail.movetrash.GetWebmailMoveTrashRequest;
import com.webmail.movetrash.GetWebmailMoveTrashResponse;
import com.webmail.movetrash.GetWebmailSpamRequest;
import com.webmail.movetrash.GetWebmailSpamResponse;
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
public class WebmailMoveTrashEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Movetrash";

	private WebmailMoveTrashRepository webmailMoveTrashRepository;

	@Autowired
	public WebmailMoveTrashEndpoint(WebmailMoveTrashRepository webmailMoveTrashRepository) {
		this.webmailMoveTrashRepository = webmailMoveTrashRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailMoveTrashRequest")
	@ResponsePayload
	public GetWebmailMoveTrashResponse mailMoveTrash(@RequestPayload GetWebmailMoveTrashRequest request) {
		GetWebmailMoveTrashResponse response = new GetWebmailMoveTrashResponse();
		response.setMoveTrashStatus(webmailMoveTrashRepository.webmailMoveTrash(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolder(), request.getWebamilUids()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailSpamRequest")
	@ResponsePayload
	public GetWebmailSpamResponse mailSpam(@RequestPayload GetWebmailSpamRequest request) {
		GetWebmailSpamResponse response = new GetWebmailSpamResponse();
		response.setSpamStatus(webmailMoveTrashRepository.webmailSpamAction(request.getWebmailHost(), request.getWebmailPort(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolder(), request.getWebmailFolderDestination(), request.getWebmailUids())); 
		return response;
	}
	
	
	
}
