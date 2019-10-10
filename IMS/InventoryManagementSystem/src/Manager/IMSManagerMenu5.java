package Manager;

import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Constant.IMSConstants;

public class IMSManagerMenu5 {
	  /**
	   * triggerMail
	   * This method triggers an email to the requested user with the inventory details.
	   */
	public  void triggerMail() {
		//Scanner to get user input
		System.out.println(IMSConstants.ENTEREMAIL);
		Scanner sc = new Scanner(System.in);
		String sendersmail = sc.nextLine();
	    String host = "smtp.gmail.com";
	 // Sender's email ID 
	    String from = IMSConstants.USERNAME;
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", IMSConstants.PASSWORD);
	    props.put("mail.smtp.port", "587"); 
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    try{
	    	 // Get the Session object.
	        Session session = Session.getDefaultInstance(props, null);
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(from));
	        message.addRecipients(Message.RecipientType.TO, sendersmail);
	     // Set Subject: header field
	        message.setSubject("IMS");
	     // Set Content: text field
	        message.setText(IMSConstants.MESSAGE);
	        String filename = IMSConstants.HARDWARE+IMSConstants.TXT;
	        //Attach File: text field
	         DataSource source = new FileDataSource(filename);
	         message.setDataHandler(new DataHandler(source));
	         message.setFileName(filename);
	        Transport transport = session.getTransport("smtp");
	     // Connecting with username and password
	        transport.connect("smtp.gmail.com", IMSConstants.USERNAME, IMSConstants.PASSWORD);
	        System.out.println(IMSConstants.MAILSUCCESS+" "+sendersmail);
	        transport.sendMessage(message, message.getAllRecipients());
	    }catch(MessagingException e){
	        e.printStackTrace();
	    }
	}
}
