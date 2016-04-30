package hello;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

import com.webmail.task.DeleteSelectedTaskRequest;
import com.webmail.task.DeleteSelectedTaskResponse;
import com.webmail.task.DeleteTaskResponse;
import com.webmail.task.GetAddTaskResponse;
import com.webmail.task.GetTaskDetailResponse;
import com.webmail.task.GetTasksResponse;
import com.webmail.task.GetUpdateTaskResponse;
import com.webmail.task.TaskArray;
import com.webmail.task.TaskBean;

import ezvcard.property.Timezone;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.PercentComplete;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.UidGenerator;

@Component
public class WebmailTaskRepository   implements EmbeddedServletContainerCustomizer
{
	
	public FileOutputStream fileoutputstream = null;
	public FileInputStream fileinputstream = null;
	public CalendarOutputter calendaroutputter = new CalendarOutputter();
	public CalendarBuilder calendarbuilder = new CalendarBuilder();
	byte[] encodedFile = null;
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer arg0) {
		arg0.setPort(11235);
	}
	
	public GetAddTaskResponse addTask(TaskBean taskBean ,byte[] filecontent)
	{
		GetAddTaskResponse response = new GetAddTaskResponse();
		Calendar cal = new Calendar();
		VToDo task = new VToDo();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			cal = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		task.getProperties().add(new DtStart(new net.fortuna.ical4j.model.DateTime(taskBean.getStartdate().toGregorianCalendar().getTime())));
		task.getProperties().add(new DtEnd(new net.fortuna.ical4j.model.DateTime(taskBean.getEnddate().toGregorianCalendar().getTime())));
		task.getProperties().add(new Summary(taskBean.getDetail()));
		task.getProperties().add(new Description(taskBean.getTaskdesc()));
		task.getProperties().add(new Priority(taskBean.getPriority()));
		task.getProperties().add(new PercentComplete(taskBean.getProgress()));
		
		String uid =UUID.randomUUID().toString();
		task.getProperties().add(new Uid(uid));
		task.getProperties().add(new Status(taskBean.getStatus()));
		cal.getComponents().add(task);
		
		InputStream is = null;		
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(cal, fileoutputstream);
			is = new FileInputStream(file);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try
		{
					encodedFile = org.apache.commons.codec.binary.Base64
						.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		response.setResponse(encodedFile);
		response.setUid(uid);
		return response;
	}
	
	public TaskArray getTasksRequest(byte[] calendarfilecontent, String calendarfilename)
	{
		Calendar calendar = new Calendar();
		TaskArray tasklist = new TaskArray();
		InputStream inputstream=null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64.decodeBase64(calendarfilecontent);
		try
		{
			inputstream = new ByteArrayInputStream(encodedImage);
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
        {
			TaskBean taskBean = new TaskBean();
			VToDo  task = (VToDo)it.next();
			
			taskBean.setDetail(task.getSummary().getValue());
			
			try
			{
				GregorianCalendar gregoriancalendar=new GregorianCalendar();
				if(task.getStartDate() != null)
				{
					gregoriancalendar.setTime(task.getStartDate().getDate());
					taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				}
				DtEnd end=(DtEnd)task.getProperty(Property.DTEND);

				if(end != null)
				{
					gregoriancalendar.setTime(end.getDate());
					taskBean.setEnddate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				}
				Property prop = task.getProperties().getProperty("MODIFYDATE");
				if(prop != null)
				{
					gregoriancalendar.setTime(new DateTime(prop.getValue()));
					taskBean.setModifydate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				}
			}
			catch(DatatypeConfigurationException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			taskBean.setUid(task.getUid().getValue());
			taskBean.setStatus(task.getStatus().getValue());
			taskBean.setTaskdesc(task.getDescription().getValue());
			tasklist.getTaskList().add(taskBean);
        }
		
		return tasklist;
	}
	public DeleteTaskResponse deleteTask(String uid , byte[] filecontent)
	{
		DeleteTaskResponse response = new DeleteTaskResponse();
		
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
        {
			VToDo  task = (VToDo)it.next();
			if(task.getUid().getValue().equals(uid))
			{
				calendar.getComponents().remove(task);
				response.setTaskdeleted(true);
			}
			
        }
		InputStream is = null;		
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(calendar, fileoutputstream);
			is = new FileInputStream(file);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try
		{
					encodedFile = org.apache.commons.codec.binary.Base64
						.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		response.setResponse(encodedFile);
		
		return response;
	}
	
	public DeleteSelectedTaskResponse deleteSelectedTask(String uid , byte[] filecontent)
	{
		DeleteSelectedTaskResponse response = new DeleteSelectedTaskResponse();
		
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
        {
			VToDo  task = (VToDo)it.next();
			if(uid.contains(task.getUid().getValue()))
			{
				calendar.getComponents().remove(task);
				response.setTaskdeleted(true);
			}
			
        }
		InputStream is = null;		
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(calendar, fileoutputstream);
			is = new FileInputStream(file);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try
		{
					encodedFile = org.apache.commons.codec.binary.Base64
						.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		response.setResponse(encodedFile);
		
		return response;
	}
	public GetTaskDetailResponse getTaskDetail(String uid , byte[] filecontent)
	{
		GetTaskDetailResponse response = new GetTaskDetailResponse();
		Calendar calendar = new Calendar();
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		TaskBean taskBean = null;
		for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
        {
			VToDo  task = (VToDo)it.next();
			if(task.getUid().getValue().equals(uid))
			{
				taskBean = new TaskBean();
				taskBean.setDetail(task.getSummary().getValue());
				
				try
				{
					GregorianCalendar gregoriancalendar=new GregorianCalendar();
					if(task.getStartDate() != null)
					{
						gregoriancalendar.setTime(task.getStartDate().getDate());
						taskBean.setStartdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					DtEnd end=(DtEnd)task.getProperty(Property.DTEND);

					if(end != null)
					{
						gregoriancalendar.setTime(end.getDate());
						taskBean.setEnddate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
					if(task.getLastModified() != null)
					{
						gregoriancalendar.setTime(task.getLastModified().getDate());
						taskBean.setModifydate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					}
				}
				catch(DatatypeConfigurationException e)
				{
					e.printStackTrace();
				}
				
				
				taskBean.setUid(task.getUid().getValue());
				taskBean.setStatus(task.getStatus().getValue());
				taskBean.setTaskdesc(task.getDescription().getValue());
				taskBean.setPriority(Integer.parseInt(task.getPriority().getValue()));
				taskBean.setProgress(Integer.parseInt(task.getPercentComplete().getValue()));
				response.getTaskList().add(taskBean);
				break;
			}
			
        }
		return response;
	}
	
	public GetUpdateTaskResponse updateTask(TaskBean taskBean ,byte[] filecontent)
	{
		GetUpdateTaskResponse response = new GetUpdateTaskResponse();
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Iterator<VToDo> it = calendar.getComponents("VTODO").iterator(); it.hasNext();)
        {
			VToDo  task = (VToDo)it.next();
			if(task.getUid().getValue().equals(taskBean.getUid()))
			{
				calendar.getComponents().remove(task);
				task = new VToDo();
				task.getProperties().add(new Uid(taskBean.getUid()));
				task.getProperties().add(new DtStart(new net.fortuna.ical4j.model.DateTime(taskBean.getStartdate().toGregorianCalendar().getTime())));
				task.getProperties().add(new DtEnd(new net.fortuna.ical4j.model.DateTime(taskBean.getEnddate().toGregorianCalendar().getTime())));
				task.getProperties().add(new Summary(taskBean.getDetail()));
				task.getProperties().add(new Description(taskBean.getTaskdesc()));
				task.getProperties().add(new Priority(taskBean.getPriority()));
				task.getProperties().add(new PercentComplete(taskBean.getProgress()));
				
				String property = "MODIFYDATE";
				Property prop = task.getProperties().getProperty(property);
				if(prop != null)
				{
					task.getProperties().remove(prop);
				}
				Date date = new Date();
				SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
				prop=new XProperty(property,dateFormat.format(date));
				task.getProperties().add(prop);
				task.getProperties().add(new Status(taskBean.getStatus()));
				calendar.getComponents().add(task);
				
			}
        }
		InputStream is = null;		
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(calendar, fileoutputstream);
			is = new FileInputStream(file);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try
		{
			encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		response.setResponse(encodedFile);
		response.setUpdateSuccess(true);
		return response;
	}
	
}
