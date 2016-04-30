package hello;

import com.webmail.createcontact.CreateContactRequest;
import com.webmail.createcontact.CreateContactResponse;
import com.webmail.createcontact.DisplayContactRequest;
import com.webmail.createcontact.GetFullContactDetailResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



@Endpoint
public class WebmailCreateContactEndpoint 
{
	private static final String NAMESPACE_URI = "http://webmail.com/CreateContact";

	private WebmailCreateContactRepository webmailCreateContactRepository;

	

	@Autowired
	public WebmailCreateContactEndpoint(WebmailCreateContactRepository webmailCreateContactRepository) {
		this.webmailCreateContactRepository = webmailCreateContactRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createContactRequest")
	@ResponsePayload
	public CreateContactResponse setMailFlag(@RequestPayload CreateContactRequest request) {
		CreateContactResponse response = new CreateContactResponse();
		response.setGetVFCIOStream(webmailCreateContactRepository.getVCFIOStreame(request.getWebamilVCFFileName(),request.getWebamilNote(), request.getWebamilFullName(), request.getWebamilCompany(), request.getWebamilJob(), request.getWebamilEmail(), request.getWebamilWebPage(),  request.getWebamilPhoneWork(), request.getWebamilPhoneHome(), request.getWebamilPhoneFax(), request.getWebamilPhoneMob(), request.getWebmailAddrWork(), request.getWebamilAddrHome(), request.getWebamilPre(), request.getWebamilFNM(), request.getWebmailMNM(), request.getWebamilLNM(), request.getWebamilSuf(), request.getWebamilPhoto()));
		return response;
	}
	
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "displayContactRequest")
	@ResponsePayload
	public GetFullContactDetailResponse setContactDet(@RequestPayload DisplayContactRequest request) {
		GetFullContactDetailResponse response = new GetFullContactDetailResponse();
		response.setGetFullContactDetail(webmailCreateContactRepository.dispContactDetails(request.getGetVFCIOStream(), request.getGetVFCFileName()));
		return response;
	}
	
	
	
	
	
}
