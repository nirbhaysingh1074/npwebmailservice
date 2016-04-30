package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.webmail.calendar.CreateEventRequest;
import com.webmail.calendar.CreateEventResponse;
import com.webmail.compose.GetComposeMailRequest;
import com.webmail.compose.GetComposeMailResponse;
import com.webmail.task.DeleteSelectedTaskRequest;
import com.webmail.task.DeleteSelectedTaskResponse;
import com.webmail.task.DeleteTaskRequest;
import com.webmail.task.DeleteTaskResponse;
import com.webmail.task.GetAddTaskRequest;
import com.webmail.task.GetAddTaskResponse;
import com.webmail.task.GetTaskDetailRequest;
import com.webmail.task.GetTaskDetailResponse;
import com.webmail.task.GetTasksRequest;
import com.webmail.task.GetTasksResponse;
import com.webmail.task.GetUpdateTaskRequest;
import com.webmail.task.GetUpdateTaskResponse;
import com.webmail.task.TaskArray;

@Endpoint
public class WebmailTaskEndpoint 
{
	private static final String NAMESPACE_URI = "http://webmail.com/Task";
	
	private WebmailTaskRepository webmailTaskRepository;
	
	@Autowired 
	public WebmailTaskEndpoint(WebmailTaskRepository webmailTaskRepository)
	{
		this.webmailTaskRepository = webmailTaskRepository;
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddTaskRequest")
	@ResponsePayload
	public GetAddTaskResponse getAddTaskRequest(@RequestPayload GetAddTaskRequest request) 
	{
		GetAddTaskResponse response = new GetAddTaskResponse();
		response=webmailTaskRepository.addTask(request.getEventbean(),request.getFilecontent());
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUpdateTaskRequest")
	@ResponsePayload
	public GetUpdateTaskResponse getUpdateTaskRequest(@RequestPayload GetUpdateTaskRequest request) 
	{
		GetUpdateTaskResponse response = new GetUpdateTaskResponse();
		response=webmailTaskRepository.updateTask(request.getEventbean(),request.getFilecontent());
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTasksRequest")
	@ResponsePayload
	public GetTasksResponse getTasksRequest(@RequestPayload GetTasksRequest request) 
	{
		GetTasksResponse res = new GetTasksResponse();
		TaskArray aa = webmailTaskRepository.getTasksRequest(request.getCalendarFileContent(), request.getTaskFileName());
		res.setTaskList(aa);
		return  res;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTaskRequest")
	@ResponsePayload
	public DeleteTaskResponse deleteTaskRequest(@RequestPayload DeleteTaskRequest request) 
	{
		return  webmailTaskRepository.deleteTask(request.getUid(), request.getCalendarFileContent());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteSelectedTaskRequest")
	@ResponsePayload
	public DeleteSelectedTaskResponse deleteSelectedTaskRequest(@RequestPayload DeleteSelectedTaskRequest request) 
	{
		return  webmailTaskRepository.deleteSelectedTask(request.getUid(), request.getCalendarFileContent());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTaskDetailRequest")
	@ResponsePayload
	public GetTaskDetailResponse getTaskDetailRequest(@RequestPayload GetTaskDetailRequest request) 
	{
		return  webmailTaskRepository.getTaskDetail(request.getUid(), request.getCalendarFileContent());
	}
}
