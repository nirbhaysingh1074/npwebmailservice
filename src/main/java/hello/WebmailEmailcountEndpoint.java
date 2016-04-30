package hello;

import com.webmail.mailcount.GetWebmailAllMailCountRequest;
import com.webmail.mailcount.GetWebmailAllMailCountResponse;
import com.webmail.mailcount.GetWebmailUnreadMailCountRequest;
import com.webmail.mailcount.GetWebmailUnreadMailCountResponse;
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
public class WebmailEmailcountEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Mailcount";

	private WebmailEmailcountRepository webmailEmailcountRepository;

	@Autowired
	public WebmailEmailcountEndpoint(WebmailEmailcountRepository webmailEmailcountRepository) {
		this.webmailEmailcountRepository = webmailEmailcountRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailUnreadMailCountRequest")
	@ResponsePayload
	public GetWebmailUnreadMailCountResponse getUnreadEmailcount(@RequestPayload GetWebmailUnreadMailCountRequest request) {
		GetWebmailUnreadMailCountResponse response = new GetWebmailUnreadMailCountResponse();
		response.setUnreademailcount(webmailEmailcountRepository.listWebmailUnreadMailCount(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolder()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailAllMailCountRequest")
	@ResponsePayload
	public GetWebmailAllMailCountResponse getAllEmailcount(@RequestPayload GetWebmailAllMailCountRequest request) {
		GetWebmailAllMailCountResponse response = new GetWebmailAllMailCountResponse();
		response.setAllemailcount(webmailEmailcountRepository.listWebmailAllMailCount(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolder()));
		return response;
	}
	
	
}
