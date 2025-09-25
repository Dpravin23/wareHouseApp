package in.nit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	@Autowired
	private JavaMailSender sender;
	
	public boolean send(
			String to,
			String subject,
			String text
			
			)
	{
	
		boolean sent;
		try {
			//create mimeMesssage
			MimeMessage message=sender.createMimeMessage();
			//use helper classs object
			MimeMessageHelper helper =new MimeMessageHelper(message);
			//set details to object
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			
			//send mail
			sender.send(message);
						
			sent=true;
		}catch(Exception e) {
			sent=false;
			e.printStackTrace();
		}
		System.out.println("message sent"+sent);
		 return sent;
	}
 
}


