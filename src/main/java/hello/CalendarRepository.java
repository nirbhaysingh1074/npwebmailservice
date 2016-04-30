package hello;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.server.UID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.UnfoldingReader;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterFactoryImpl;
import net.fortuna.ical4j.model.ParameterFactoryRegistry;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.PropertyFactoryRegistry;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;
import net.fortuna.ical4j.model.component.Standard;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.FreeBusy;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.TzOffsetFrom;
import net.fortuna.ical4j.model.property.TzOffsetTo;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.util.packed.PackedInts.Reader;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.FLAGS;
import com.webmail.calendar.CalendarBean;
import com.webmail.calendar.ChangeColorResponse;
import com.webmail.calendar.CreateCalendarResponse;
import com.webmail.calendar.CreateEventRequest;
import com.webmail.calendar.CreateEventResponse;
import com.webmail.calendar.DateTimeList;
import com.webmail.calendar.EventArray;
import com.webmail.calendar.EventBean;
import com.webmail.calendar.GetCalendarDetailResponse;
import com.webmail.calendar.GetCalendarMailResponse;
import com.webmail.calendar.GetDeleteEventResponse;
import com.webmail.calendar.GetEventsRequest;
/*import com.webmail.file.CreateFileRequest;
import com.webmail.file.CreateFileResponse;*/








import com.webmail.calendar.GetEventsResponse;
import com.webmail.calendar.GetFilterEventsResponse;
import com.webmail.calendar.GetImportCalendarResponse;
import com.webmail.calendar.GetInviteEventResponse;
import com.webmail.calendar.GetUpdateCalendarResponse;
import com.webmail.task.DeleteTaskResponse;

import scala.util.control.Exception.Finally;

@Component
public class CalendarRepository   implements EmbeddedServletContainerCustomizer{
	@Override
	public void customize(ConfigurableEmbeddedServletContainer arg0) {
		arg0.setPort(11235);
	}
	public FileOutputStream fileoutputstream = null;
	public FileInputStream fileinputstream = null;
	public CalendarOutputter calendaroutputter = new CalendarOutputter();
	public CalendarBuilder calendarbuilder = new CalendarBuilder();
	byte[] encodedFile = null;
	
	public java.util.Date npSetDate(java.util.Date dt)
	{
		java.util.Calendar npcal = java.util.Calendar.getInstance(); // creates calendar
		npcal.setTime(dt); // sets calendar time/date=====> you can set your own date here
		npcal.add(java.util.Calendar.HOUR_OF_DAY, -5); // adds one hour
		npcal.add(java.util.Calendar.MINUTE, -30); // adds one Minute
		return npcal.getTime(); // returns new date object, one hour in the future
	}
	
	public byte[] createCalendar(String calid, String calcolor, String calMethod)
			throws Exception {

		
		File file = File.createTempFile("calendar", "ics");
		InputStream is = null;
		System.out.println("creating color property");
		PropertyFactoryImpl color = PropertyFactoryImpl.getInstance();
		Property calendarcolor = color.createProperty("COLOR");
		calendarcolor.setValue(calcolor);
		System.out.println("color property created");
		PropertyFactoryImpl name = PropertyFactoryImpl.getInstance();
		Property calendarname = name.createProperty("X-WR-CALNAME");
		calendarname.setValue(calid);
		System.out.println("name property created");

		try {
			fileoutputstream = new FileOutputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Silvereye//Silvereye Calendar 11.5//EN"));
		calendar.getProperties().add(
				net.fortuna.ical4j.model.property.Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		calendar.getProperties().add(calendarname);
		calendar.getProperties().add(calendarcolor);
		
		if(calMethod != null && calMethod.equalsIgnoreCase("PUBLISH"))
		{
			calendar.getProperties().add(Method.PUBLISH);
		}
		else if(calMethod != null && calMethod.equalsIgnoreCase("REQUEST"))
		{
			calendar.getProperties().add(Method.REQUEST);
		}
		else if(calMethod != null && calMethod.equalsIgnoreCase("REPLY"))
		{
			calendar.getProperties().add(Method.REPLY);
		}

	/*	
		 VTimeZone tz = VTimeZone.getDefault();
		 TzId tzParam = new TzId(tz.getProperties().getProperty(Property.TZID)
		         .getValue());*/
		
		
		VTimeZone vtz=new VTimeZone();
		try
		{
			net.fortuna.ical4j.model.property.TzId tz=new net.fortuna.ical4j.model.property.TzId("India Standard Time");
			vtz.getProperties().add(tz);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Standard std=new Standard();
		std.getProperties().add(new DtStart("16010101T000000"));
		std.getProperties().add(new TzOffsetFrom("+0530"));
		std.getProperties().add(new TzOffsetTo("+0530"));
		
		try
		{
		vtz.getObservances().add(std);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		calendar.getComponents().add(vtz);
		
		calendaroutputter.setValidating(false);
		try 
		{
			calendaroutputter.output(calendar, fileoutputstream);

			is = new FileInputStream(file);
			encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}/*
		 * finally{ file.delete(); }
		 */
		return encodedFile;
	}
	
	

	public CreateEventResponse createEvent(EventBean eventbean, byte[] filecontent, String orgname, String orgemail)
	{
		CreateEventResponse response=new CreateEventResponse();
		boolean ust=false;
		System.out.println(eventbean.getReminderdata());
		Calendar cal = new Calendar();
		VEvent event = new VEvent();
		InputStream inputstream = null;
		  byte[] encodedImage = org.apache.commons.codec.binary.Base64
                  .decodeBase64(filecontent);
          inputstream=new ByteArrayInputStream(encodedImage);
		
		try {
			cal = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int check=0;
		Dur dur=null;
		PeriodList pl=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		
		for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
		{
			VEvent vevent = (VEvent)it.next();
			if(vevent.getUid().getValue().equals(eventbean.getUid()))
			{
				event.getProperties().add(vevent.getUid());
				eventbean.setUid(vevent.getUid().getValue());
				cal.getComponents().remove(vevent);
				//---------EDIT event------------
				event.getProperties().add(new Summary(eventbean.getSummary()));
				
				
				event.getProperties().add(new LastModified(new DateTime()));
				DtStart dtstart=null;
				DtEnd dtend=null;
				//String c=eventbean.getAllday();	
				try
				{
					if(eventbean.getAllday() !=null && eventbean.getAllday().equals("off"))
					{
						
						dtstart=new DtStart(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime()));
						dtend=new DtEnd(dateFormat.format(eventbean.getEndeventdate().toGregorianCalendar().getTime()));
					}
					else
					{
						dtstart=new DtStart();
						dtend=new DtEnd();
						
//						dtstart.getParameters().add(new Value(dateFormat1.format(eventbean.getStarteventdate().toGregorianCalendar().getTime())));
//						dtstart.setDate(new Date(dateFormat1.parse(eventbean.getStarteventdate().toGregorianCalendar().getTime().toString())));
//						dtend.getParameters().add(new Value(dateFormat1.format(eventbean.getEndeventdate().toGregorianCalendar().getTime())));
//						dtend.setDate(new Date(dateFormat1.parse(eventbean.getEndeventdate().toGregorianCalendar().getTime().toString())));
						Date dt = new Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						Date dt1 = new Date(eventbean.getEndeventdate().toGregorianCalendar().getTime());
						dt1.setDate(dt1.getDate()+1);
						
						dtstart.setDate(dt);
						dtend.setDate(dt1);
						
					}
					
				}
				catch(ParseException e)
				{
					e.printStackTrace();
					response.setResponse(null);
					return response;
				}
				try
				{
				event.getProperties().add(dtstart);
				event.getProperties().add(dtend);
				if(eventbean.getLocation()!=null && !eventbean.getLocation().equals(""))
				event.getProperties().add(new Location(eventbean.getLocation()));
				
				if(eventbean.getDescription()!=null && !eventbean.getDescription().equals(""))
				event.getProperties().add(new Description(eventbean.getDescription()));
				event.getProperties().add(Status.VEVENT_CONFIRMED);
				event.getProperties().add(new Clazz(eventbean.getClazz()));
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				if(eventbean.getFreebusy()!=null && eventbean.getFreebusy().equals("free"))
				{
					event.getProperties().add(Transp.TRANSPARENT);
				}
				else
				{
					event.getProperties().add(Transp.OPAQUE);
				}
				
				if(vevent.getLocation()==null)
					vevent.getProperties().add(new Location(""));
				if(event.getLocation()==null)
					event.getProperties().add(new Location(""));
				
				if(!vevent.getSummary().equals(event.getSummary()) || !vevent.getStartDate().equals(event.getStartDate()) ||
						!vevent.getEndDate().equals(event.getEndDate()) || !vevent.getLocation().equals(event.getLocation())
						)
				{
					if(vevent.getProperty(Property.SEQUENCE).getValue()!=null)
					{
					int sequence = Integer.parseInt(vevent.getProperty(Property.SEQUENCE).getValue());
					event.getProperties().add(new Sequence(sequence + 1));
					ust=true;
					eventbean.setSequence(sequence+1);
					}
					else
					{
						event.getProperties().add(new Sequence(1));
						ust=true;
						eventbean.setSequence(1);
					}
				}
				else
				{
					event.getProperties().add(vevent.getProperty(Property.SEQUENCE));
				}
//				try 
//				{
//					event.getProperties().add(new FreeBusy(eventbean.getFreebusy()));
//				}
//				catch (ParseException e1) 
//				{
//					
//					e1.printStackTrace();
//				}
				if(eventbean.getFrequency()!=null && !eventbean.getFrequency().equalsIgnoreCase("no"))
				{
					Recur recur=null;
					if(eventbean.getCount()!=null)
					{
						recur=new Recur(eventbean.getFrequency(),eventbean.getCount());
						recur.setInterval(eventbean.getInterval());
						if(recur.getFrequency().equals("YEARLY"))
						{
							
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
						}
						else if(recur.getFrequency().equals("MONTHLY"))
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
						}
						else
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
						}
					}
					else if(eventbean.getUntil()!=null)
					{
						Date dt = new Date(eventbean.getUntil().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						recur=new Recur(eventbean.getFrequency(),new Date(dt));
						dur=new Dur(event.getStartDate().getDate(),recur.getUntil());
					}
					else
					{
						recur = new Recur(eventbean.getFrequency(),null);
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						event.getProperties().add(new RRule(recur));
						
						Date dt  =new Date(event.getEndDate().getDate().getTime());
						dt.setYear(dt.getYear() +5);
						dur=new Dur(event.getStartDate().getDate(),dt);
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
					
					
					if(eventbean.getCount()!=null || eventbean.getUntil()!=null)
					{
						// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							if(eventbean.getRepeatdatetimelist()!=null)
							{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
							}
						}
						recur.setInterval(eventbean.getInterval());
						/*if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
						recur.set*/
						event.getProperties().add(new RRule(recur));
						///////////method testing
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
					try {
						event.getProperties().add(new Organizer("Silvereye"));
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
					
				}
				
				try
				{
				Attendee attendee = null;
				if(eventbean.getNewguest()==null)
					eventbean.setNewguest("");
				if(eventbean.getOldguest()==null)
					eventbean.setOldguest("");
				if(eventbean.getNewguest() != "" || eventbean.getOldguest() != "")
				{
					String guests[] = (eventbean.getNewguest()+""+eventbean.getOldguest()).split(",");
					for(String guest : guests)
					{
						if(guest != null && guest.length() != 0)
						{
							String act="";
							String arrid1[]=guest.split("`");
		   					if(arrid1.length>1)
		   					{
		   						guest=arrid1[1];
		   						act=arrid1[0];
		   					}
		   					else
		   						guest=arrid1[0];
							attendee = new Attendee(URI.create("mailto:"+guest));
							attendee.getParameters().add(Role.REQ_PARTICIPANT);
							attendee.getParameters().add(CuType.INDIVIDUAL);
							if(act.equalsIgnoreCase("a"))
								attendee.getParameters().add(PartStat.ACCEPTED);
							else if(act.equalsIgnoreCase("t"))
								attendee.getParameters().add(PartStat.TENTATIVE);
							else if(act.equalsIgnoreCase("d"))
								attendee.getParameters().add(PartStat.DECLINED);
							else
									attendee.getParameters().add(PartStat.NEEDS_ACTION);
							
							event.getProperties().add(attendee);
						}
					}
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if(eventbean.getReminderdata()!=null && !eventbean.getReminderdata().equals(""))
				{
					String[] reminderarray = eventbean.getReminderdata().split(";");
					for (String reminderdata : reminderarray) 
					{
						String[] reminders = reminderdata.split("`");
						VAlarm alarm = new VAlarm();
						String triggerduration=null;
						if(reminders[2].equals("W") || reminders[2].equals("D"))
							triggerduration="P"+reminders[1]+reminders[2];
						else if (reminders[2].equals("M") || reminders[2].equals("H"))
							triggerduration="PT"+reminders[1]+reminders[2];
						if (reminders[0].equalsIgnoreCase("Pop-up")) {
							alarm.getProperties().add(Action.DISPLAY);	                  
						}
						else if (reminders[0].equalsIgnoreCase("Email"))
						{
							alarm.getProperties().add(Action.EMAIL);
							alarm.getProperties().add(new Summary("Alarm Notification"));
						}
						alarm.getProperties().add(new Description("Event Reminder"));
						Dur duration = new Dur(triggerduration);
						alarm.getProperties().add(new Trigger(duration));
						event.getAlarms().add(alarm);
					}
				}
				cal.getComponents().add(event);
						//---------------finish create event------------
				
				check++;				
			}			
		}
		
		//alarm
		

			if(check==0)
			{
				//---------create event----------
				
				event.getProperties().add(new Uid(UUID.randomUUID().toString()));
				eventbean.setUid(event.getUid().getValue());
				event.getProperties().add(new Created(new DateTime()));
				event.getProperties().add(new Summary(eventbean.getSummary()));
				Organizer organizer = new Organizer(URI.create("mailto:"+orgemail));
				organizer.getParameters().add(new Cn(orgname));
				event.getProperties().add(organizer);
				DtStart dtstart=null;
				DtEnd dtend=null;
				//String c=eventbean.getAllday();	
				try
				{
					if(eventbean.getAllday()!=null && eventbean.getAllday().equals("off"))
					{
						dtstart=new DtStart(dateFormat.format(eventbean.getStarteventdate().toGregorianCalendar().getTime()));
						dtend=new DtEnd(dateFormat.format(eventbean.getEndeventdate().toGregorianCalendar().getTime()));
					}
					else
					{
						dtstart=new DtStart();
						dtend=new DtEnd();
						
//						dtstart.getParameters().add(new Value(dateFormat1.format(eventbean.getStarteventdate().toGregorianCalendar().getTime())));
//						dtstart.setDate(new Date(dateFormat1.parse(eventbean.getStarteventdate().toGregorianCalendar().getTime().toString())));
//						dtend.getParameters().add(new Value(dateFormat1.format(eventbean.getEndeventdate().toGregorianCalendar().getTime())));
//						dtend.setDate(new Date(dateFormat1.parse(eventbean.getEndeventdate().toGregorianCalendar().getTime().toString())));
						Date dt = new Date(eventbean.getStarteventdate().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						Date dt1 = new Date(eventbean.getEndeventdate().toGregorianCalendar().getTime());
						dt1.setDate(dt1.getDate()+1);
						
						dtstart.setDate(dt);
						dtend.setDate(dt1);
						
					}
					
				}
				catch(ParseException e)
				{
					e.printStackTrace();
					response.setResponse(null);
					return response;
				}
				event.getProperties().add(dtstart);
				event.getProperties().add(dtend);
				if(eventbean.getLocation()!=null && !eventbean.getLocation().equals(""))
				event.getProperties().add(new Location(eventbean.getLocation()));
				if(eventbean.getDescription()!=null && !eventbean.getDescription().equals(""))
				event.getProperties().add(new Description(eventbean.getDescription()));
				event.getProperties().add(new Sequence(0));
				event.getProperties().add(Status.VEVENT_CONFIRMED);
				try
				{
				event.getProperties().add(new Clazz(eventbean.getClazz()));
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				
				if(eventbean.getFreebusy()!=null && eventbean.getFreebusy().equals("free"))
				{
					event.getProperties().add(Transp.TRANSPARENT);
				}
				else
				{
					event.getProperties().add(Transp.OPAQUE);
				}
				
				if(eventbean.getNewguest()==null)
					eventbean.setNewguest("");
				if(eventbean.getOldguest()==null)
					eventbean.setOldguest("");
				Attendee attendee = null;
				if(eventbean.getNewguest() != "" || eventbean.getOldguest() != "")
				{
					String guests[] = (eventbean.getNewguest()+""+eventbean.getOldguest()).split(",");
					for(String guest : guests)
					{
						if(guest != null && guest.length() != 0)
						{
							String act="";
							String arrid1[]=guest.split("`");
		   					if(arrid1.length>1)
		   					{
		   						guest=arrid1[1];
		   						act=arrid1[0];
		   					}
		   					else
		   						guest=arrid1[0];
							attendee = new Attendee(URI.create("mailto:"+guest));
							attendee.getParameters().add(Role.REQ_PARTICIPANT);
							attendee.getParameters().add(CuType.INDIVIDUAL);
							if(act.equalsIgnoreCase("a"))
								attendee.getParameters().add(PartStat.ACCEPTED);
							else if(act.equalsIgnoreCase("t"))
								attendee.getParameters().add(PartStat.TENTATIVE);
							else if(act.equalsIgnoreCase("d"))
								attendee.getParameters().add(PartStat.DECLINED);
							else
									attendee.getParameters().add(PartStat.NEEDS_ACTION);
							
							event.getProperties().add(attendee);
						}
					}
				}
				
				
//				try 
//				{
//					event.getProperties().add(new FreeBusy(eventbean.getFreebusy()));
//				}
//				catch (ParseException e1) 
//				{
//					
//					e1.printStackTrace();
//				}
				if(eventbean.getFrequency()!=null && !eventbean.getFrequency().equalsIgnoreCase("no"))
				{
					Recur recur=null;
					if(eventbean.getCount()!=null)
					{
						recur=new Recur(eventbean.getFrequency(),eventbean.getCount());
						recur.setInterval(eventbean.getInterval());
						if(recur.getFrequency().equals("YEARLY"))
						{
							
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
						}
						else if(recur.getFrequency().equals("MONTHLY"))
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
						}
						else
						{
							dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
						}
						
					}
					else if(eventbean.getUntil()!=null)
					{
						Date dt = new Date(eventbean.getUntil().toGregorianCalendar().getTime());
						dt.setDate(dt.getDate()+1);
						recur=new Recur(eventbean.getFrequency(),new Date(dt));
						dur=new Dur(event.getStartDate().getDate(),recur.getUntil());
					}
					else
					{
						recur = new Recur(eventbean.getFrequency(),null);
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						event.getProperties().add(new RRule(recur));
						
						Date dt  =new Date(event.getEndDate().getDate().getTime());
						dt.setYear(dt.getYear() +5);
						dur=new Dur(event.getStartDate().getDate(),dt);
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
					

					
					
					if(eventbean.getCount()!=null || eventbean.getUntil()!=null)
					{
						// for( Iterator<String> i=eventbean.getRepeatdatetimelist().getDateTime().iterator();i.hasNext();)
						if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
						{
							List<String> days=eventbean.getRepeatdatetimelist().getDateTime();
							for(String day:days)
								recur.getDayList().add(new WeekDay(day));
						}
						recur.setInterval(eventbean.getInterval());
						/*if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
						recur.set*/
						event.getProperties().add(new RRule(recur));
						///////////method testing
						response.setRepeatdates(calculaterepeatdate(event,event.getStartDate(), dur));
					}
//					try {
//						event.getProperties().add(new Organizer("sanjay-ovi"));
//					} catch (URISyntaxException e) {
//						e.printStackTrace();
//					}
					
				}
				if(eventbean.getReminderdata()!=null && !eventbean.getReminderdata().equals(""))
				{
					String[] reminderarray = eventbean.getReminderdata().split(";");
					for (String reminderdata : reminderarray) 
					{
						String[] reminders = reminderdata.split("`");
						VAlarm alarm = new VAlarm();
						String triggerduration=null;
						if(reminders[2].equals("W") || reminders[2].equals("D"))
							triggerduration="P"+reminders[1]+reminders[2];
						else if (reminders[2].equals("M") || reminders[2].equals("H"))
							triggerduration="PT"+reminders[1]+reminders[2];
						if (reminders[0].equalsIgnoreCase("Pop-up")) {
							alarm.getProperties().add(Action.DISPLAY);	                  
						}
						else if (reminders[0].equalsIgnoreCase("Email"))
						{
							alarm.getProperties().add(Action.EMAIL);
							alarm.getProperties().add(new Summary("Alarm Notification"));
						}
						alarm.getProperties().add(new Description("Event Reminder"));
						Dur duration = new Dur(triggerduration);
						alarm.getProperties().add(new Trigger(duration));
						
						event.getAlarms().add(alarm);
					}
				}
						//---------------finish create event------------
				cal.getComponents().add(event);
				//cal.getComponents().remove(component)
			}
		InputStream is = null;		
		try {
			File file = File.createTempFile("calendar", "ics");
			
			is = new FileInputStream(file);
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(cal, fileoutputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
				encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		response.setResponse(encodedFile);
		response.setEventcolor(cal.getProperty("COLOR").getValue());
		/*if(pl!=null)
		response.setRepeatdates(pl.toString());*/
		response.setUpdateStatus(ust);
		response.setEventuid(eventbean.getUid());
		return response;
	}

	public GetEventsResponse buildEvents(byte[] calendarfilecontent, String calendarfilename) 
	{
		Calendar calendar = new Calendar();
		GetEventsResponse res=new GetEventsResponse();
		EventArray eventlist = new EventArray();
		InputStream inputstream=null;
		 byte[] encodedImage = org.apache.commons.codec.binary.Base64
                 .decodeBase64(calendarfilecontent);
		try {
			inputstream = new ByteArrayInputStream(encodedImage);
			calendar = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Iterator<VEvent> it = calendar.getComponents("VEVENT").iterator(); it.hasNext();)
        {
			EventBean eventbean = new EventBean();
			VEvent component = (VEvent) it.next();
			
			eventbean.setSummary(component.getSummary().getValue());
			//date type changed
			try{
			GregorianCalendar gregoriancalendar=new GregorianCalendar();
			
			
				  if(component.getStartDate().getDate().toString().indexOf("T",0)<0)
				  {	
					  eventbean.setAllday("on");
					  String sdt=component.getStartDate().getDate().toString();
					  sdt+="T000000+05:30";
					  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
					  java.util.Date dt=component.getStartDate().getDate();
					  try {
						dt=dateFormat.parse(sdt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  	 gregoriancalendar.setTime(dt);
				  	eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					 
					 String sdt1=component.getEndDate().getDate().toString();
					  sdt1+="T000000+05:30";
					  java.util.Date dt1=component.getEndDate().getDate();
					  try {
						dt1=dateFormat.parse(sdt1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 gregoriancalendar.setTime(dt1);
					 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				  
				  }
				  else
				  {
					  eventbean.setAllday("off");
					  gregoriancalendar.setTime(component.getStartDate().getDate());
					  eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						 gregoriancalendar.setTime(component.getEndDate().getDate());
						 eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				  }
				 
			
			
			}
			catch(DatatypeConfigurationException e)
			{
				e.printStackTrace();
			}
			// date type changed end
			
			eventbean.setUid(component.getUid().getValue());
			eventbean.setCalendar(calendarfilename);
			try
			{
				if(component.getClassification().getValue()!=null)
					eventbean.setClazz(component.getClassification().getValue());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				PropertyList list = component.getProperties(Property.ATTENDEE);
				String guests = "";
				for(Object obj : list)
				{
					if (obj instanceof Attendee) 
					{
						Attendee guest = (Attendee) obj;
						String name = guest.getCalAddress().toString();
						name = name.replace("mailto:", "");
						name = name.replace("MAILTO:", "");
						String status = guest.getParameter(Parameter.PARTSTAT).getValue();
						if(guests!=null && guests.length()>0)
						{
							guests +=",";
						}
						
						if(status.equals("ACCEPTED"))
						{
							guests += "a`"+name;
						}
						else if(status.equals("DECLINED"))
						{
							guests += "d`"+name;
						}
						else if(status.equals("TENTATIVE"))
						{
							guests += "t`"+name;
						}
						else if(status.equals("IN_PROCESS"))
						{
							guests += "i`"+name;
						}
						else
						{
							guests += "n`"+name;
						}
						//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
					}
				}
				eventbean.setOldguest(guests);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			try
			{
			if(calendar.getProperty("COLOR").getValue()!=null)
			eventbean.setColor(calendar.getProperty("COLOR").getValue());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				eventbean.setColor("");
			}
			
			if(component.getTransparency() != null)
			{
				if(component.getTransparency().equals(Transp.TRANSPARENT))
				{
					eventbean.setFreebusy("free");
				}
				else
				{
					eventbean.setFreebusy("busy");
				}
			}
			
			if(component.getProperty("RRULE")!=null)
			{
				RRule rrule=(RRule)component.getProperty("RRULE");
				Recur recur=rrule.getRecur();
				try {
					Date startdate=new Date(component.getStartDate().getValue());
					
					//String until=recur.getUntil();
					
					/*if(recur.getCount()!=0 || recur.getUntil()==null)
					{*/
						Dur dur=null;
						if(recur.getCount()>0)
						{
							if(recur.getFrequency().equals("YEARLY"))
							{
								
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
							}
							else if(recur.getFrequency().equals("MONTHLY"))
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
							}
							else
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
							}
						 
						}
						else if(recur.getUntil() != null)
						{
							dur=new Dur(component.getStartDate().getDate(),recur.getUntil());
						}
						else
						{
							Date dt  =new Date(component.getEndDate().getDate().getTime());
							dt.setYear(dt.getYear() +5);
							dur=new Dur(component.getStartDate().getDate(),dt);
						}
						//DateTime stdate=new DateTime(component.getStartDate().getValue());
						
						///////////method testing
						try
						{
						eventbean.setRepeatdatetimelist(calculaterepeatdate(component,component.getStartDate(), dur));
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
					
						/////////methid testing end here
						/*PeriodList pl=component.calculateRecurrenceSet(new Period(new DateTime(component.getStartDate().getValue()), dur));
						for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
						{
							Period period=(Period)pit.next();
							try {
								dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
				/*eventbean.setRepeatdatetimelist(dtl);*/
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			eventlist.getEventList().add(eventbean);
		}
		res.setEventList(eventlist);
		try
		{
		if(calendar.getProperty("X-WR-CALNAME").getValue()!=null)
		res.setCalendarname(calendar.getProperty("X-WR-CALNAME").getValue());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			res.setCalendarname("");
		}
		try
		{
		if(calendar.getProperty("COLOR").getValue()!=null)
		res.setCalendarcolor(calendar.getProperty("COLOR").getValue());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			res.setCalendarcolor("");
		}
		return res ;

	}
	
	public byte[] importEventsToDefaultCalendar(byte[] fromCalendarfilecontent, byte[] toCalendarfilecontent)
	{
		Calendar calFrom = new Calendar();
		Calendar calTo = new Calendar();
		InputStream inputstream=null;
		byte[] encodedImageFrom = org.apache.commons.codec.binary.Base64.decodeBase64(fromCalendarfilecontent);
		byte[] encodedImageTo = org.apache.commons.codec.binary.Base64.decodeBase64(toCalendarfilecontent);
		try 
		{
			inputstream = new ByteArrayInputStream(encodedImageTo);
			calTo = calendarbuilder.build(inputstream);

			inputstream = new ByteArrayInputStream(encodedImageFrom);
			calFrom = calendarbuilder.build(inputstream);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for (Iterator<VEvent> it = calFrom.getComponents("VEVENT").iterator(); it.hasNext();)
        {
			VEvent event = (VEvent) it.next();
			calTo.getComponents().add(event);
        }
		
		InputStream is = null;
		calendaroutputter.setValidating(false);
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			calendaroutputter.output(calTo, fileoutputstream);

			is = new FileInputStream(file);
			encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		return encodedFile;
	}
	
	public GetInviteEventResponse getInviteEventRequest(byte[] filecontect, byte[] defaultCalendar)
	{
		GetInviteEventResponse res = new GetInviteEventResponse();
		
		Calendar calFrom = new Calendar();
		Calendar calTo = new Calendar();
		InputStream inputstream=null;
		byte[] encodedImageFrom = org.apache.commons.codec.binary.Base64.decodeBase64(filecontect);
		byte[] encodedImageTo = org.apache.commons.codec.binary.Base64.decodeBase64(defaultCalendar);
		try 
		{
			inputstream = new ByteArrayInputStream(encodedImageFrom);
			calFrom = calendarbuilder.build(inputstream);
			
			inputstream = new ByteArrayInputStream(encodedImageTo);
			calTo = calendarbuilder.build(inputstream);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		EventArray eventlist = new EventArray();
		
		
		for (Iterator<VEvent> it = calFrom.getComponents("VEVENT").iterator(); it.hasNext();)
        {
			VEvent event = (VEvent) it.next();
			
			VEvent vevent = null;
			for (Iterator<VEvent> itr = calTo.getComponents("VEVENT").iterator(); itr.hasNext();)
	        {
				vevent = (VEvent) itr.next();
				
				if(event.getUid().getValue().equals(vevent.getUid().getValue()))
				{
					calTo.getComponents().remove(vevent);
					break;
				}
	        }
			
//			calTo.getProperty("COLOR").getValue();
			calTo.getComponents().add(event);
			
			EventBean eventbean = new EventBean();
			eventbean.setSummary(event.getSummary().getValue());
			//date type changed
			if(event.getLocation().getValue()!=null)
			eventbean.setLocation(event.getLocation().getValue());
			try
			{
				GregorianCalendar gregoriancalendar=new GregorianCalendar();
				gregoriancalendar.setTime(event.getStartDate().getDate());
				if(event.getStartDate().getDate().toString().indexOf("T",0)<0)
					eventbean.setAllday("on");
				else
					eventbean.setAllday("off");
				eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				gregoriancalendar.setTime(event.getEndDate().getDate());
				eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				
			}
			catch(DatatypeConfigurationException e)
			{
				e.printStackTrace();
			}
			// date type changed end
			eventbean.setUid(event.getUid().getValue());
			PropertyList list = event.getProperties(Property.ATTENDEE);
			String guests = "";
			for(Object obj : list)
			{
				if (obj instanceof Attendee) 
				{
					Attendee guest = (Attendee) obj;
					String name = guest.getCalAddress().toString();
					name = name.replace("mailto:", "");
					name = name.replace("MAILTO:", "");
					String status ="";
					try
					{
						status=guest.getParameter(Parameter.PARTSTAT).getValue();
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
					}
					
					if(status.equals("ACCEPTED"))
					{
						guests += ",a`"+name;
					}
					else if(status.equals("DECLINED"))
					{
						guests += ",d`"+name;
					}
					else if(status.equals("TENTATIVE"))
					{
						guests += ",t`"+name;
					}
					else if(status.equals("IN_PROCESS"))
					{
						guests += ",i`"+name;
					}
					else
					{
						guests += ",n`"+name;
					}
				//	System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
				}
			}
			eventbean.setOldguest(guests);
			
			eventlist.getEventList().add(eventbean);
			
        }
		
		res.setEventList(eventlist);
		InputStream is = null;
		calendaroutputter.setValidating(false);
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			
			calendaroutputter.output(calTo, fileoutputstream);

			is = new FileInputStream(file);
			encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (ValidationException e) 
		{
			e.printStackTrace();
		}
		
		res.setFilecontent(encodedFile);
		return res;
	}
	
	
	public GetImportCalendarResponse getImportCalendar(byte[] filecontect, byte[] defaultCalendar)
	{
		GetImportCalendarResponse res = new GetImportCalendarResponse();
		
		Calendar calFrom = new Calendar();
		Calendar calTo = new Calendar();
		InputStream inputstream=null;
		byte[] encodedImageFrom = org.apache.commons.codec.binary.Base64.decodeBase64(filecontect);
		byte[] encodedImageTo = org.apache.commons.codec.binary.Base64.decodeBase64(defaultCalendar);
		try 
		{
			inputstream = new ByteArrayInputStream(encodedImageFrom);
			calFrom = calendarbuilder.build(inputstream);
			
			inputstream = new ByteArrayInputStream(encodedImageTo);
			calTo = calendarbuilder.build(inputstream);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		EventArray eventlist = new EventArray();
		
		
		for (Iterator<VEvent> it = calFrom.getComponents("VEVENT").iterator(); it.hasNext();)
        {
			VEvent event = (VEvent) it.next();
			
			calTo.getComponents().add(event);
			
        }
		
		
		InputStream is = null;
		calendaroutputter.setValidating(false);
		try 
		{
			File file = File.createTempFile("calendar", "ics");
			fileoutputstream = new FileOutputStream(file);
			
			calendaroutputter.output(calTo, fileoutputstream);

			is = new FileInputStream(file);
			encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (ValidationException e) 
		{
			e.printStackTrace();
		}
		
		res.setFilecontent(encodedFile);
		return res;
	}
	
	
	public EventBean geteventdetailsRequest(EventBean event, byte[] filecontent)
	{
	
		System.out.println(event.getUid());
		System.out.println(event.getCalendar());
		
		Calendar calendar = new Calendar();
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
		InputStream inputstream=null;
		try 
		{
			inputstream =new ByteArrayInputStream (encodedImage);
			calendar = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (@SuppressWarnings("unchecked")
		Iterator<VEvent> it = calendar.getComponents("VEVENT").iterator(); it.hasNext();)
		{			VEvent componentevent = (VEvent)it.next();
			if(componentevent.getUid().getValue().equals(event.getUid()))
			{
				event.setSummary(componentevent.getSummary().getValue());
				//date type change start
				
				  GregorianCalendar gregoriancalendar=new GregorianCalendar();
				
				  
				  try 
				  {
					  if(componentevent.getStartDate().getDate().toString().indexOf("T",0)<0)
					  {	
						  event.setAllday("on");
						  String sdt=componentevent.getStartDate().getDate().toString();
						  sdt+="T000000+05:30";
						  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss+05:30");
						  java.util.Date dt=componentevent.getStartDate().getDate();
						  try {
							dt=dateFormat.parse(sdt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  	 gregoriancalendar.setTime(dt);
						 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
						 
						 String sdt1=componentevent.getEndDate().getDate().toString();
						  sdt1+="T000000+05:30";
						  java.util.Date dt1=componentevent.getEndDate().getDate();
						  try {
							dt1=dateFormat.parse(sdt1);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 gregoriancalendar.setTime(dt1);
						 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					  
					  }
					  else
					  {
						  event.setAllday("off");
						  gregoriancalendar.setTime(componentevent.getStartDate().getDate());
							 event.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
							 gregoriancalendar.setTime(componentevent.getEndDate().getDate());
							 event.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					  }
					 
				  }
				  catch (DatatypeConfigurationException e1) {
						e1.printStackTrace();
				  }
				//date type change end
				  
				  try
				  {
					  if(componentevent.getProperty(Property.SEQUENCE).getValue()!=null)
						{
						int sequence = Integer.parseInt(componentevent.getProperty(Property.SEQUENCE).getValue());
						event.setSequence(sequence);
						}
						else
						{
							event.setSequence(0);
						} 
				  }
				  catch(Exception ee)
				  {
					  ee.printStackTrace();
				  }
				if(componentevent.getLocation()!=null)
				event.setLocation(componentevent.getLocation().getValue());
				if(componentevent.getDescription()!=null)
				event.setDescription(componentevent.getDescription().getValue());
				try
				{
				if(componentevent.getClassification().getValue()!=null)
				event.setClazz(componentevent.getClassification().getValue());
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
				if(componentevent.getTransparency() != null)
				{
					if(componentevent.getTransparency().equals(Transp.TRANSPARENT))
					{
						event.setFreebusy("free");
					}
					else
					{
						event.setFreebusy("busy");
					}
				}
				
				
				PropertyList list = componentevent.getProperties(Property.ATTENDEE);
				String guests = "";
				for(Object obj : list)
				{
					if (obj instanceof Attendee) 
					{
						Attendee guest = (Attendee) obj;
						String name = guest.getCalAddress().toString();
						name = name.replace("mailto:", "");
						name = name.replace("MAILTO:", "");
						String status ="";
						try
						{
						status=	guest.getParameter(Parameter.PARTSTAT).getValue();
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
						if(status.equals("ACCEPTED"))
						{
							guests += ",a`"+name;
						}
						else if(status.equals("DECLINED"))
						{
							guests += ",d`"+name;
						}
						else if(status.equals("TENTATIVE"))
						{
							guests += ",t`"+name;
						}
						else if(status.equals("IN_PROCESS"))
						{
							guests += ",i`"+name;
						}
						else
						{
							guests += ",n`"+name;
						}
						//System.out.println("Attendee Status : " + guest.getParameters(Parameter.PARTSTAT));
					}
				}
				event.setOldguest(guests);
				
		       RRule rrule=(RRule)componentevent.getProperty("RRULE");
		       if(rrule!=null)
		       {
		       Recur recur=rrule.getRecur();
		       if(recur.getCount()>0)
		       {
		    	   event.setCount(recur.getCount());
		       }
		       else if(recur.getUntil() != null)
		       {
		    	   gregoriancalendar.setTime(recur.getUntil());
					try {
						event.setUntil( DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
					} catch (DatatypeConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		    	  // event.setUntil(recur.getUntil().toString());
		       }
		       else
		       {
		    	   
		       }
		       
		       event.setFrequency(recur.getFrequency());
		       event.setInterval(recur.getInterval()); 
		       if(recur.getFrequency().equalsIgnoreCase(recur.WEEKLY))
		       {
		    	   DateTimeList dt=new DateTimeList();
		       for(Iterator<WeekDay> itw =recur.getDayList().iterator(); itw.hasNext();)
		    	   dt.getDateTime().add(itw.next().toString());
		    	   event.setRepeatdatetimelist(dt);
		       }
		       if(recur.getFrequency().equalsIgnoreCase(recur.MONTHLY))
		    	   System.out.println();//////////////////////////////////////////////
		       }
				event.setReminderdata(""); 
	           for( Iterator<VAlarm> iterator=componentevent.getAlarms().iterator();iterator.hasNext();)
	           {
	        	   VAlarm alarm=(VAlarm)iterator.next();
	        	   //String action=alarm.getAction().getValue();
	        	   if(alarm.getAction().getValue().equals("DISPLAY"))
	        	   {
	        		   event.setReminderdata(event.getReminderdata()+"Pop-up"+"`");
	        	   }
	        	   else if (alarm.getAction().getValue().equals("EMAIL")) {
	        		   event.setReminderdata(event.getReminderdata()+"Email"+"`");
	        	   }
	        	   
	        	   Dur duration=new Dur(alarm.getTrigger().getValue());
	        	  
	        	// System.out.println((triggerduration.substring(1, triggerduration.indexOf("D")).equals("0")));
	        	   if (duration.getWeeks()!=0)
	        	   {
	        		   event.setReminderdata(event.getReminderdata()+duration.getWeeks()+"`"+"W"+";");
	        	   }
	        	   else  if(duration.getHours()!=0)
	               {
	        		  
	        		   event.setReminderdata(event.getReminderdata()+duration.getHours()+"`"+"H"+";");
	        	   }
	        	   else if (duration.getDays()!=0) {
	        		   event.setReminderdata(event.getReminderdata()+duration.getDays()+"`"+"D"+";");
	        	   }
	        	   else if (duration.getMinutes()!=0) {
	        		   event.setReminderdata(event.getReminderdata()+duration.getMinutes()+"`"+"M"+";");
	        	   }
	           }
	           System.out.println(event.getReminderdata());
			}
		}
		return event;
	}
	
	public DateTimeList calculaterepeatdate(VEvent tempevent, DtStart startdate, Dur dur)
	{
		DateTimeList dtl=new DateTimeList();
		VEvent component=new VEvent();
		PeriodList pl=null;
		try
		{
			
			pl = tempevent.calculateRecurrenceSet(new Period(new DateTime(startdate.getValue()), dur));
			
		}
		catch (ParseException e1) 
		{
			e1.printStackTrace();
		}
		for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
		{
			Period period=(Period)pit.next();
			try 
			{
				dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	
		return dtl;
   
	}
	public ChangeColorResponse changecalendarcolor(byte[] filecontent, String changedcolor) {
		
		ChangeColorResponse res = new ChangeColorResponse();
		Calendar cal=new Calendar();
		InputStream inputstream = null;
		  byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(filecontent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try {
			cal = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			cal.getProperty("COLOR").setValue(changedcolor);
			EventArray eventarray = new EventArray();
			EventBean eventBean = null;
			for (Iterator<VEvent> it = cal.getComponents("VEVENT").iterator(); it.hasNext();)
			{	
					VEvent componentevent = (VEvent)it.next();
					eventBean = new EventBean();
					eventBean.setUid(componentevent.getUid().getValue());
					eventarray.getEventList().add(eventBean);
			}
			res.setEventList(eventarray);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream is = null;		
		try {
			File file = File.createTempFile("calendar", "ics");
			
			is = new FileInputStream(file);
			fileoutputstream = new FileOutputStream(file);
			calendaroutputter.setValidating(false);
			calendaroutputter.output(cal, fileoutputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
				encodedFile = org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(is));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		res.setFilecontent(encodedFile);
		res.setSuccess(true);
		return res;
	}
	public GetDeleteEventResponse deleteEventRequest(String uid, byte[] fileContent)
	{
		GetDeleteEventResponse response = new GetDeleteEventResponse();
		
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(fileContent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		for (Iterator<VEvent> it = calendar.getComponents("VEVENT").iterator(); it.hasNext();)
        {
			VEvent  task = (VEvent)it.next();
			if(task.getUid().getValue().equals(uid))
			{
				calendar.getComponents().remove(task);
				response.setResponse(true);
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
		response.setFilecontent(encodedFile);
		
		return response;
	}
	public GetCalendarDetailResponse getCalendarDetailRequest(String calname, byte[] fileContent)
	{
		GetCalendarDetailResponse response = new GetCalendarDetailResponse();
		
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(fileContent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setCalendarname(calendar.getProperty("X-WR-CALNAME").getValue());
		calendarBean.setColor(calendar.getProperty("COLOR").getValue());
		Property location = calendar.getProperty("LOCATION");
		if(location != null)
		{
			calendarBean.setLocation(location.getValue());
		}
		Property description = calendar.getProperty("DESCRIPTION");
		if(description != null)
		{
			calendarBean.setDescription(description.getValue());
		}
		response.setCalendarBean(calendarBean);
		
		return response;
	}
	public GetUpdateCalendarResponse getUpdateCalendarRequest(CalendarBean calBean, byte[] fileContent)
	{
		GetUpdateCalendarResponse response = new GetUpdateCalendarResponse();
		
		Calendar calendar = new Calendar();
		
		
		InputStream inputstream = null;
		byte[] encodedImage = org.apache.commons.codec.binary.Base64
                .decodeBase64(fileContent);
        inputstream=new ByteArrayInputStream(encodedImage);
		
		try
		{
			calendar = calendarbuilder.build(inputstream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try 
		{
			if(! calendar.getProperties().getProperty("X-WR-CALNAME").getValue().equals(calBean.getCalendarname()))
			{
				calendar.getProperties().getProperty("X-WR-CALNAME").setValue(calBean.getCalendarname());
			}
			Property loc = calendar.getProperties().getProperty("LOCATION");
			if(loc != null)
			{
				calendar.getProperties().getProperty("LOCATION").setValue(calBean.getLocation());
			}
			else
			{
				PropertyFactoryImpl name = PropertyFactoryImpl.getInstance();
				Property calLocaltion = name.createProperty("LOCATION");
				calLocaltion.setValue(calBean.getLocation());
				calendar.getProperties().add(calLocaltion);
			}
			Property desc = calendar.getProperties().getProperty("DESCRIPTION");
			if(desc != null)
			{
				calendar.getProperties().getProperty("DESCRIPTION").setValue(calBean.getDescription());
			}
			else
			{
				PropertyFactoryImpl name = PropertyFactoryImpl.getInstance();
				Property calDesc = name.createProperty("DESCRIPTION");
				calDesc.setValue(calBean.getDescription());
				calendar.getProperties().add(calDesc);
			}
			
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		response.setFilecontent(encodedFile);
		response.setSuccess(true);
		return response;
	}
	
	public GetFilterEventsResponse buildFilterEvents(byte[] calendarfilecontent, String calendarfilename,XMLGregorianCalendar filterDate) 
	{
		Calendar calendar = new Calendar();
		GetFilterEventsResponse res=new GetFilterEventsResponse();
		EventArray eventlist = new EventArray();
		InputStream inputstream=null;
		 byte[] encodedImage = org.apache.commons.codec.binary.Base64
                 .decodeBase64(calendarfilecontent);
		try {
			inputstream = new ByteArrayInputStream(encodedImage);
			calendar = calendarbuilder.build(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Period p = new Period(new DateTime(filterDate.toGregorianCalendar().getTime()), new Dur(60, 0, 0,0));
		Rule[] rules = new Rule[1];
        rules[0] = new PeriodRule(p);
        Filter f = new Filter(rules, Filter.MATCH_ALL);

		for (Iterator<VEvent> it = f.filter(calendar.getComponents(net.fortuna.ical4j.model.Component.VEVENT)).iterator(); it.hasNext();)
        {
			EventBean eventbean = new EventBean();
			VEvent component = (VEvent) it.next();
			
			eventbean.setSummary(component.getSummary().getValue());
			//date type changed
			try{
			GregorianCalendar gregoriancalendar=new GregorianCalendar();
			gregoriancalendar.setTime(component.getStartDate().getDate());
			if(component.getStartDate().getDate().toString().indexOf("T",0)<0 )
			{
				eventbean.setAllday("on");
				eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
				gregoriancalendar.setTime(component.getEndDate().getDate());
				gregoriancalendar.add(java.util.Calendar.DAY_OF_YEAR, -1);
				eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
			}
			else
			{
				eventbean.setAllday("off");
			eventbean.setStarteventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
			gregoriancalendar.setTime(component.getEndDate().getDate());
			eventbean.setEndeventdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregoriancalendar));
			}
			
			}
			catch(DatatypeConfigurationException e)
			{
				e.printStackTrace();
			}
			// date type changed end
			eventbean.setUid(component.getUid().getValue());
			eventbean.setCalendar(calendarfilename);
			try
			{
			if(component.getClassification().getValue()!=null)
			eventbean.setClazz(component.getClassification().getValue());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			eventbean.setColor(calendar.getProperty("COLOR").getValue());
			if(component.getProperty("RRULE")!=null)
			{
				RRule rrule=(RRule)component.getProperty("RRULE");
				Recur recur=rrule.getRecur();
				try {
					Date startdate=new Date(component.getStartDate().getValue());
					
					//String until=recur.getUntil();
					
					/*if(recur.getCount()!=0 || recur.getUntil()==null)
					{*/
						Dur dur=null;
						if(recur.getCount()>0)
						{
							if(recur.getFrequency().equals("YEARLY"))
							{
								
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*365)+"D");
							}
							else if(recur.getFrequency().equals("MONTHLY"))
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval()*30)+"D");
							}
							else
							{
								dur=new Dur("P"+Integer.toString(recur.getCount() * recur.getInterval())+recur.getFrequency());
							}
						 
						}
						else if(recur.getUntil() != null)
						{
							dur=new Dur(component.getStartDate().getDate(),recur.getUntil());
						}
						else
						{
							Date dt  =new Date(component.getEndDate().getDate().getTime());
							dt.setYear(dt.getYear() +5);
							dur=new Dur(component.getStartDate().getDate(),dt);
						}
						//DateTime stdate=new DateTime(component.getStartDate().getValue());
						
						///////////method testing
						eventbean.setRepeatdatetimelist(calculaterepeatdate(component,component.getStartDate(), dur));
					
						/////////methid testing end here
						/*PeriodList pl=component.calculateRecurrenceSet(new Period(new DateTime(component.getStartDate().getValue()), dur));
						for (Iterator<Period> pit = pl.iterator(); pit.hasNext();)
						{
							Period period=(Period)pit.next();
							try {
								dtl.getDateTime().add(period.getStart().toString()+"`"+period.getEnd().toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
				/*eventbean.setRepeatdatetimelist(dtl);*/
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			eventlist.getEventList().add(eventbean);
		}
		res.setEventList(eventlist);
		res.setCalendarname(calendar.getProperty("X-WR-CALNAME").getValue());
		res.setCalendarcolor(calendar.getProperty("COLOR").getValue());
		return res ;

	}
	
	
	private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
	 
	
	public GetCalendarMailResponse sendCalendarMail(String webamilIp, String webamilId, String webamilPassword, String webamilFromName, String webamilHost, String webamilPort, String webamilTo, String webamilSubject, String webamilBodyContent, String webamilCalendarContent, String webmailXMailer)
	{
		GetCalendarMailResponse res = new GetCalendarMailResponse();
		

		
		final String username = webamilId;//change accordingly
		final String password = webamilPassword;//change accordingly
		

		Session ses =Connections.smtpConnectionNP(webamilHost, webamilPort, webamilId, webamilPassword);
		
		try {
			
			try {
				webamilFromName=MimeUtility.encodeText(webamilFromName, "utf-8", "B");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    Message message = new MimeMessage(ses);
		   try
		   {
		    message.setFrom(new InternetAddress(username, webamilFromName));
		   }
		   catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			   message.setFrom(new InternetAddress(username));
				e.printStackTrace();
			}
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(webamilTo));
		   
		   
		    
		    
		    message.setHeader("X-Originating-IP", webamilIp);
		    message.setHeader("X-Mailer", webmailXMailer);
		    
		    
		    java.util.Date dtn=new java.util.Date();
		    SimpleDateFormat form = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			String     DateToStr = form.format(dtn);
			message.setHeader("Date", DateToStr);
			message.setSubject(webamilSubject);
			
			MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap)MimetypesFileTypeMap.getDefaultFileTypeMap();
		    mimetypes.addMimeTypes("text/calendar ics ICS");
			
		    MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
		    mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			
		    Multipart multipart = new MimeMultipart("alternative");
		    
		    
		    //part 1, html text
		    
		    BodyPart messageBodyPart = new MimeBodyPart();
		    String content = webamilBodyContent;
		    messageBodyPart.setContent(content, "text/html; charset=utf-8");
	 	    multipart.addBodyPart(messageBodyPart);
			
	 	    // Add part two, the calendar
	 	    
	 	    BodyPart calendarPart = new MimeBodyPart();
	 
	        
	        calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
	        calendarPart.setContent(webamilCalendarContent, "text/calendar; method=REQUEST; charset=\"utf-8\"");
	 	    multipart.addBodyPart(calendarPart);
	 
	 	    message.setContent(multipart);
	 	   
		    Transport.send(message);
		    
		    System.out.println("Message sent...");
		    
		    
			res.setSetMailStatus(true);
		   		    
		    
//		    store.close();
		    System.out.println("Done");

		} catch (MessagingException e)
		{
			e.printStackTrace();
			res.setSetMailStatus(false);
			
		}
		
		return res;
	}
	
	
	
	
 
    
	
}
