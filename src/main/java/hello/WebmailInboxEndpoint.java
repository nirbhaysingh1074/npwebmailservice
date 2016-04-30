package hello;

import com.webmail.inbox.GetInboxMailDescRequest;
import com.webmail.inbox.GetInboxMailRequest;
import com.webmail.inbox.GetMailInboxDescResponse;
import com.webmail.inbox.GetMailInboxResponse;
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
public class WebmailInboxEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Inbox";

	private WebmailInboxRepository webmailInboxRepository;

	@Autowired
	public WebmailInboxEndpoint(WebmailInboxRepository webmailInboxRepository) {
		this.webmailInboxRepository = webmailInboxRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInboxMailRequest")
	@ResponsePayload
	public GetMailInboxResponse getMailInbox(@RequestPayload GetInboxMailRequest request) {
		GetMailInboxResponse response = new GetMailInboxResponse();
		System.out.println("inbox endpoint ****="+request.getWebamilId());
		response.setGetInboxByMailLimit(webmailInboxRepository.listWebmailInbox(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilStart(), request.getWebamilEnd(), request.getWebamilFolder()));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInboxMailDescRequest")
	@ResponsePayload
	public GetMailInboxDescResponse getMailInboxDesc(@RequestPayload GetInboxMailDescRequest request) {
		GetMailInboxDescResponse response = new GetMailInboxDescResponse();
		response.setGetInboxByMailLimitDesc(webmailInboxRepository.listWebmailInboxDesc(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilStart(), request.getWebamilEnd(), request.getWebamilFolder()));
		return response;
	}
	
}
