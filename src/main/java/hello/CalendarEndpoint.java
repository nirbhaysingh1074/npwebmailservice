package hello;

import com.webmail.calendar.ChangeColorRequest;
import com.webmail.calendar.ChangeColorResponse;
import com.webmail.calendar.CreateCalendarRequest;
import com.webmail.calendar.CreateCalendarResponse;
import com.webmail.calendar.CreateEventRequest;
import com.webmail.calendar.CreateEventResponse;
import com.webmail.calendar.EventBean;
import com.webmail.calendar.GetCalendarDetailRequest;
import com.webmail.calendar.GetCalendarDetailResponse;
import com.webmail.calendar.GetCalendarMailRequest;
import com.webmail.calendar.GetCalendarMailResponse;
import com.webmail.calendar.GetDeleteEventRequest;
import com.webmail.calendar.GetDeleteEventResponse;
import com.webmail.calendar.GetEventsRequest;
import com.webmail.calendar.GetEventsResponse;
import com.webmail.calendar.GetFilterEventsRequest;
import com.webmail.calendar.GetFilterEventsResponse;
import com.webmail.calendar.GetImportCalendarRequest;
import com.webmail.calendar.GetImportCalendarResponse;
import com.webmail.calendar.GetInviteEventRequest;
import com.webmail.calendar.GetInviteEventResponse;
import com.webmail.calendar.GetUpdateCalendarRequest;
import com.webmail.calendar.GetUpdateCalendarResponse;
import com.webmail.calendar.GeteventdetailsRequest;
import com.webmail.calendar.GeteventdetailsResponse;
import com.webmail.calendar.ImportCalendarRequest;

import org.apache.derby.impl.store.raw.data.SetReservedSpaceOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CalendarEndpoint {
	private static final String NAMESPACE_URI = "http://webmail.com/calendar";

	private CalendarRepository calendarRepository;

	@Autowired
	public CalendarEndpoint(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCalendarMailRequest")
	@ResponsePayload
	public GetCalendarMailResponse getCalendarMailRequest(@RequestPayload GetCalendarMailRequest request) 
	{
		GetCalendarMailResponse response = null;
		try
		{
			response= calendarRepository.sendCalendarMail(request.getWebamilIp(), request.getWebamilId(),
					request.getWebamilPassword(),request.getWebamilFromName(),
					request.getWebamilHost(), request.getWebamilPort(),
					request.getWebamilTo(), request.getWebamilSubject(),
					request.getWebamilBodyContent(), request.getWebamilCalendarContent(),
					request.getWebmailXMailer());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCalendarRequest")
	@ResponsePayload
	public CreateCalendarResponse createCalendarRequest(@RequestPayload CreateCalendarRequest request) 
	{
		CreateCalendarResponse response = new CreateCalendarResponse();
		try{
		response.setResponse(calendarRepository.createCalendar(request.getCalID(),request.getCalColor(), request.getCalMethod()));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "importCalendarRequest")
	@ResponsePayload
	public CreateCalendarResponse importCalendarRequest(@RequestPayload ImportCalendarRequest request) 
	{
		CreateCalendarResponse response = new CreateCalendarResponse();
		try
		{
			response.setResponse(calendarRepository.importEventsToDefaultCalendar(request.getFromfilecontent(),request.getTofilecontent()));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEventRequest")
	@ResponsePayload
	public CreateEventResponse createEventRequest(@RequestPayload CreateEventRequest request) 
	{
		CreateEventResponse response = new CreateEventResponse();
		response=calendarRepository.createEvent(request.getEventbean(),request.getFilecontent(), request.getOrgname(), request.getOrgemail());
		//response.setRepeatdates(calendarRepository.createEvent(request.getEventbean(),request.getFilecontent()).getRepeatdates());
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventsRequest")
	@ResponsePayload
	public GetEventsResponse getEventsRequest(@RequestPayload GetEventsRequest request) 
	{
		GetEventsResponse response = new GetEventsResponse();
		response=calendarRepository.buildEvents(request.getCalendarFileContent(),request.getCalendarFileName());
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getImportCalendarRequest")
	@ResponsePayload
	public GetImportCalendarResponse getImportCal(@RequestPayload GetImportCalendarRequest request) 
	{
		GetImportCalendarResponse response = new GetImportCalendarResponse();
		response=calendarRepository.getImportCalendar(request.getNewCalendar(),request.getExistingCalendar());
		return response;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInviteEventRequest")
	@ResponsePayload
	public GetInviteEventResponse getInviteEventRequest(@RequestPayload GetInviteEventRequest request) 
	{
		GetInviteEventResponse response  = calendarRepository.getInviteEventRequest(request.getFilecontent(), request.getDefaultCalendar());
		return response;
	}
	
	
	
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "geteventdetailsRequest")
	@ResponsePayload
	public GeteventdetailsResponse geteventdetailsRequest(@RequestPayload GeteventdetailsRequest request) 
	{
		GeteventdetailsResponse response = new GeteventdetailsResponse();
		response.setEventBean(calendarRepository.geteventdetailsRequest(request.getEventbean(),request.getFilecontent()));
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "changeColorRequest")
	@ResponsePayload
	public ChangeColorResponse changeColorRequest(@RequestPayload ChangeColorRequest request) 
	{
		
		return  calendarRepository.changecalendarcolor(request.getFilecontent(),request.getChangedcolor());
		
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDeleteEventRequest")
	@ResponsePayload
	public GetDeleteEventResponse getDeleteEventRequest(@RequestPayload GetDeleteEventRequest request) 
	{
		
		return calendarRepository.deleteEventRequest(request.getUid(), request.getFilecontent());
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCalendarDetailRequest")
	@ResponsePayload
	public GetCalendarDetailResponse getCalendarDetailRequest(@RequestPayload GetCalendarDetailRequest request) 
	{
		return calendarRepository.getCalendarDetailRequest(request.getCalname(), request.getFilecontent());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUpdateCalendarRequest")
	@ResponsePayload
	public GetUpdateCalendarResponse getUpdateCalendarRequest(@RequestPayload GetUpdateCalendarRequest request) 
	{
		return calendarRepository.getUpdateCalendarRequest(request.getCalendarBean(), request.getFilecontent());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilterEventsRequest")
	@ResponsePayload
	public GetFilterEventsResponse getFilterEventsRequest(@RequestPayload GetFilterEventsRequest request) 
	{
		GetFilterEventsResponse response = new GetFilterEventsResponse();
		response=calendarRepository.buildFilterEvents(request.getCalendarFileContent(),request.getCalendarFileName(), request.getFilterDate());
		return response;
	}
}
