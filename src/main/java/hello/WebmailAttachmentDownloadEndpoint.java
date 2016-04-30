package hello;


import javax.servlet.http.HttpServletResponse;

import com.webmail.maildisplay.GetMailDisplayRequest;
import com.webmail.maildisplay.GetMailDisplayResponse;
import com.webmail.maildownload.GetMailAttachDownloadRequest;
import com.webmail.maildownload.GetMailAttachDownloadResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailAttachmentDownloadEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/Maildisplay";

	private WebmailAttachmentDownloadRepository webmailAttachmentDownloadRepository;

	@Autowired
	public WebmailAttachmentDownloadEndpoint(WebmailAttachmentDownloadRepository webmailAttachmentDownloadRepository) {
		this.webmailAttachmentDownloadRepository = webmailAttachmentDownloadRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMailAttachDownloadRequest")
	@ResponsePayload
	public GetMailAttachDownloadResponse getMailAttachDownload(@RequestPayload GetMailAttachDownloadRequest request) 
	{
		GetMailAttachDownloadResponse response = new GetMailAttachDownloadResponse();
		
		System.out.println("%%%%%%%%%%%%%%% attach endpoint");
		//response.setGetInboxByUid(webmailContentDisplayRepository.listWebmailContent(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilUid(), request.getWebamilFolder()));
		//response.setGetDownloadStatus(webmailAttachmentDownloadRepository.downloadMailAttach(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilUid(), request.getWebamilFolder(), request.getWebamilFileName()));
		response.setWebamilInputStream(webmailAttachmentDownloadRepository.downloadMailAttach(request.getWebamilHost(), request.getWebamilPort(), request.getWebamilId(), request.getWebamilPassword(), request.getWebamilUid(), request.getWebamilFolder(), request.getWebamilFileName()));
		return response;
	}
}