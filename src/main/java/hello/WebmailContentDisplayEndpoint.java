package hello;


import com.webmail.maildisplay.GetMailDisplayRequest;
import com.webmail.maildisplay.GetMailDisplayResponse;
import com.webmail.maildisplay.GetMailHeaderRequest;
import com.webmail.maildisplay.GetMailHeaderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailContentDisplayEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Maildisplay";

	private WebmailContentDisplayRepository webmailContentDisplayRepository;

	@Autowired
	public WebmailContentDisplayEndpoint(WebmailContentDisplayRepository webmailContentDisplayRepository) {
		this.webmailContentDisplayRepository = webmailContentDisplayRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMailDisplayRequest")
	@ResponsePayload
	public GetMailDisplayResponse getMailDisplayInbox(@RequestPayload GetMailDisplayRequest request) 
	{
		GetMailDisplayResponse response = new GetMailDisplayResponse();
		response.setGetInboxByUid(webmailContentDisplayRepository.listWebmailContent(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilUid(), request.getWebamilFolder()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMailHeaderRequest")
	@ResponsePayload
	public GetMailHeaderResponse getMailHeader(@RequestPayload GetMailHeaderRequest request) 
	{
		GetMailHeaderResponse response = new GetMailHeaderResponse();
		response.setGetMailHeaderCont(webmailContentDisplayRepository.listWebmailHeader(request.getWebmailHost(), request.getWebmailPort(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailUid(), request.getWebmailFolder()));
		return response;
	}
}