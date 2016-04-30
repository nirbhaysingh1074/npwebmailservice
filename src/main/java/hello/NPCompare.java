package hello;

import java.util.Comparator;

import javax.mail.Message;
import javax.mail.MessagingException;



public class NPCompare implements Comparator<Message>  {

@Override
public int compare(Message obj1, Message obj2) {
    try {
		return (obj1.getReceivedDate()).compareTo((obj2.getReceivedDate()));
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
}

}
