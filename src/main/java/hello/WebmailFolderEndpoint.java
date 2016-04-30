package hello;


import com.webmail.folder.GetWebmailCreateFolderRequest;
import com.webmail.folder.GetWebmailCreateFolderResponse;
import com.webmail.folder.GetWebmailDeleteFolderRequest;
import com.webmail.folder.GetWebmailDeleteFolderResponse;
import com.webmail.folder.GetWebmailFolderOtherRequest;
import com.webmail.folder.GetWebmailFolderOtherResponse;
import com.webmail.folder.GetWebmailFolderRequest;
import com.webmail.folder.GetWebmailFolderResponse;
import com.webmail.folder.GetWebmailFolderSubscribedOtherRequest;
import com.webmail.folder.GetWebmailFolderSubscribedOtherResponse;
import com.webmail.folder.GetWebmailFolderSubscribedRequest;
import com.webmail.folder.GetWebmailFolderSubscribedResponse;
import com.webmail.folder.GetWebmailSubFolderRequest;
import com.webmail.folder.GetWebmailSubFolderResponse;
import com.webmail.folder.GetWebmailSubFolderSubscribedOtherRequest;
import com.webmail.folder.GetWebmailSubFolderSubscribedOtherResponse;
import com.webmail.folder.GetWebmailSubFolderSubscribedRequest;
import com.webmail.folder.GetWebmailSubFolderSubscribedResponse;
import com.webmail.folder.GetWebmailSubscriptionFolderRequest;
import com.webmail.folder.GetWebmailSubscriptionFolderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailFolderEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Folder";

	private WebmailFolderRepository webmailFolderRepository;

	@Autowired
	public WebmailFolderEndpoint(WebmailFolderRepository webmailFolderRepository)
	{
		this.webmailFolderRepository = webmailFolderRepository;
	}

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailCreateFolderRequest")
	@ResponsePayload
	public GetWebmailCreateFolderResponse getWebmailCreateFolder(@RequestPayload GetWebmailCreateFolderRequest request)
	{
		GetWebmailCreateFolderResponse response = new GetWebmailCreateFolderResponse();
		response.setGetcflderStatus(webmailFolderRepository.createNewFolder(request.getWebmailHost(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolderName()));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailDeleteFolderRequest")
	@ResponsePayload
	public GetWebmailDeleteFolderResponse getWebmailCreateFolder(@RequestPayload GetWebmailDeleteFolderRequest request)
	{
		GetWebmailDeleteFolderResponse response = new GetWebmailDeleteFolderResponse();
		response.setGetdflderStatus(webmailFolderRepository.deleteFolder(request.getWebmailHost(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolderName()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailSubscriptionFolderRequest")
	@ResponsePayload
	public GetWebmailSubscriptionFolderResponse getWebmailSubscriptionFolder(@RequestPayload GetWebmailSubscriptionFolderRequest request)
	{
		GetWebmailSubscriptionFolderResponse response = new GetWebmailSubscriptionFolderResponse();
		response.setGetsflderStatus(webmailFolderRepository.subscriptionFolder(request.getWebmailHost(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolderName(), request.isWebmailFolderSubStatus()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailFolderSubscribedRequest")
	@ResponsePayload
	public GetWebmailFolderSubscribedResponse getFolderSubscribed(@RequestPayload GetWebmailFolderSubscribedRequest request)
	{
		GetWebmailFolderSubscribedResponse response = new GetWebmailFolderSubscribedResponse();
		response.setGetWebmailFolderSubscribed(webmailFolderRepository.listWebmailFolderSubscribed(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailFolderSubscribedOtherRequest")
	@ResponsePayload
	public GetWebmailFolderSubscribedOtherResponse getFolderSubscribedOther(@RequestPayload GetWebmailFolderSubscribedOtherRequest request)
	{
		GetWebmailFolderSubscribedOtherResponse response = new GetWebmailFolderSubscribedOtherResponse();
		response.setGetSubFolder(webmailFolderRepository.listWebmailFolderSubscribedOther(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword(), request.getWebmailFolderPath()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailSubFolderSubscribedRequest")
	@ResponsePayload
	public GetWebmailSubFolderSubscribedResponse getSubFolderSubscribed(@RequestPayload GetWebmailSubFolderSubscribedRequest request) 
	{
		GetWebmailSubFolderSubscribedResponse response = new GetWebmailSubFolderSubscribedResponse();
		response.setGetWebmailSubFolderSubscribed(webmailFolderRepository.listWebmailSubFolderSubscribed(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolderPath()));
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailSubFolderSubscribedOtherRequest")
	@ResponsePayload
	public GetWebmailSubFolderSubscribedOtherResponse getSubFolderSubscribedOther(@RequestPayload GetWebmailSubFolderSubscribedOtherRequest request) 
	{
		GetWebmailSubFolderSubscribedOtherResponse response = new GetWebmailSubFolderSubscribedOtherResponse();
		response.setGetSubFolder(webmailFolderRepository.listWebmailSubFolderSubscribedOther(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolderPath()));
		return response;
	}
	
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailFolderRequest")
	@ResponsePayload
	public GetWebmailFolderResponse getFolder(@RequestPayload GetWebmailFolderRequest request)
	{
		GetWebmailFolderResponse response = new GetWebmailFolderResponse();
		response.setGetWebmailFolder(webmailFolderRepository.listWebmailFolder(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailFolderOtherRequest")
	@ResponsePayload
	public GetWebmailFolderOtherResponse getFolderOther(@RequestPayload GetWebmailFolderOtherRequest request)
	{
		GetWebmailFolderOtherResponse response = new GetWebmailFolderOtherResponse();
		response.setGetSubFolder(webmailFolderRepository.listWebmailFolderOther(request.getWebmailHost(), request.getWebmailId(), request.getWebmailPassword(), request.getWebmailFolderPath()));
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWebmailSubFolderRequest")
	@ResponsePayload
	public GetWebmailSubFolderResponse getSubFolder(@RequestPayload GetWebmailSubFolderRequest request) 
	{
		GetWebmailSubFolderResponse response = new GetWebmailSubFolderResponse();
		response.setGetWebmailSubFolder(webmailFolderRepository.listWebmailSubFolder(request.getWebamilHost(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilFolderPath()));
		return response;
	}
	
}
