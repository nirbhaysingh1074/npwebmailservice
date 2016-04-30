package hello;

import com.webmail.inbox.GetInboxMailDescRequest;
import com.webmail.inbox.GetInboxMailRequest;
import com.webmail.inbox.GetMailInboxDescResponse;
import com.webmail.inbox.GetMailInboxResponse;
import com.webmail.mailattach.GetMailAttachmentRequest;
import com.webmail.mailattach.GetMailAttachmentResponse;
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
public class WebmailAttachmentEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/MailAttach";

	private WebmailAttachmentRepository webmailAttachmentRepository;

	@Autowired
	public WebmailAttachmentEndpoint(WebmailAttachmentRepository webmailAttachmentRepository) {
		this.webmailAttachmentRepository = webmailAttachmentRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMailAttachmentRequest")
	@ResponsePayload
	public GetMailAttachmentResponse getMailAttch(@RequestPayload GetMailAttachmentRequest request) {
		GetMailAttachmentResponse response = new GetMailAttachmentResponse();
	//	System.out.println("inbox endpoint ****="+request.getWebamilId());
		//response.setGetInboxByMailLimit(webmailInboxRepository.listWebmailInbox(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilStart(), request.getWebamilEnd(), request.getWebamilFolder()));
		response.setGetAttachByUid(webmailAttachmentRepository.listWebmailAttachment(request.getWebmailHost(), request.getWebmailPort(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailUid(), request.getWebmailFolder()));
		return response;
	}
	
	
}
