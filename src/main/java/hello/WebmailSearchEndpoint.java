package hello;

import com.webmail.inbox.GetInboxMailDescRequest;
import com.webmail.inbox.GetInboxMailRequest;
import com.webmail.inbox.GetMailInboxDescResponse;
import com.webmail.inbox.GetMailInboxResponse;
import com.webmail.quota.*;
import com.webmail.search.GetInboxMailQuickSearchRequest;
import com.webmail.search.GetInboxMailSearchRequest;
import com.webmail.search.GetMailInboxQuickSearchResponse;
import com.webmail.search.GetMailInboxSearchResponse;
import com.edms.folder.GetFolderResponse;
import com.edms.folder.HasChildRequest;
import com.edms.folder.HasChildResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailSearchEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Search";

	private WebmailSearchRepository webmailSearchRepository;

	@Autowired
	public WebmailSearchEndpoint(WebmailSearchRepository webmailSearchRepository) {
		this.webmailSearchRepository = webmailSearchRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInboxMailSearchRequest")
	@ResponsePayload
	public GetMailInboxSearchResponse getInboxMailSearch(@RequestPayload GetInboxMailSearchRequest request) {
		GetMailInboxSearchResponse response = new GetMailInboxSearchResponse();
		response.setGetInboxByMailLimitSearch(webmailSearchRepository.listWebmailSearch(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilStart(), request.getWebamilEnd(), request.getWebamilFolder(), request.getSearchTo(), request.getSearchFrom(), request.getSearchSubject(), request.getSearchContent(),request.getSearchDateStart(), request.getSearchDateEnd() , request.getSearchTag(), request.getSearchStatus()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInboxMailQuickSearchRequest")
	@ResponsePayload
	public GetMailInboxQuickSearchResponse getInboxMailQuickSearch(@RequestPayload GetInboxMailQuickSearchRequest request) {
		GetMailInboxQuickSearchResponse response = new GetMailInboxQuickSearchResponse();
		response.setGetInboxByMailLimitQuickSearch(webmailSearchRepository.listWebmailQuickSearch(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilStart(), request.getWebamilEnd(), request.getWebamilFolder(), request.getQuickVal()));
		return response;
	}
	
	
}
