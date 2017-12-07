package epimed_web.service.mail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import epimed_web.entity.mongodb.admin.User;
import epimed_web.entity.mongodb.jobs.Job;
import epimed_web.service.mongodb.UserService;

@Service
public class MailService {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH.mm.ssSSS");
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private UserService userService;

	/** ====================================================================================== */
	
	public void sendMailToAdmin(String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setFrom("EpiMed Database <iab-epimed@univ-grenoble-alpes.fr>");
		message.setReplyTo("ekaterina.flin@univ-grenoble-alpes.fr");
		message.setTo("ekaterina.flin@univ-grenoble-alpes.fr"); 
		message.setSubject("[EpiMed Database] " + subject); 
		message.setText(text);
		emailSender.send(message);
	}

	/** ====================================================================================== */

	public void sendRequestByMail(String requestType, Job job) {

		String mailSubject = "You have a new request " + job.getId();

		User user = userService.getUser();

		if (user==null) {
			user = userService.generateDefaultUser();
		}

		String ipAddress = userService.getIp();

		String mailText = ""
				+ "Request type = " + requestType + "\n\n"
				+ "Job ID = " + job.getId() + "\n\n"
				+ "Job type = " + job.getType() + "\n\n"
				+ "Object = " + job.getMainObject() + "\n\n"
				+ "Recognized accession ID elements = " + job.getElements() + "\n\n"
				+ "User = " + user.getFirstName() + " " + user.getLastName() + "\n\n"
				+ "IP = " + ipAddress + "\n\n"
				+ "Date = " + dateFormat.format(new Date()) + "\n\n"
				;
		this.sendMailToAdmin(mailSubject, mailText);

	}
	
	/** ====================================================================================== */

	public void sendRequestByMail(String requestType, String database, String idSeries, String [] elements, String jobid) {

		String mailSubject = "You have a new request";
		
		if (jobid!=null) {
			mailSubject = mailSubject + " " + jobid;
		}

		User user = userService.getUser();

		if (user==null) {
			user = userService.generateDefaultUser();
		}

		String ipAddress = userService.getIp();

		String mailText = ""
				+ "Request type = " + requestType + "\n\n"
				+ "Job ID = " + jobid + "\n\n"
				+ "External database = " + database + "\n\n"
				+ "Accession ID = " + idSeries + "\n\n"
				+ "Recognized accession ID elements = " + Arrays.toString(elements) + "\n\n"
				+ "User = " + user.getFirstName() + " " + user.getLastName() + "\n\n"
				+ "IP = " + ipAddress + "\n\n"
				+ "Date = " + dateFormat.format(new Date()) + "\n\n"
				;
		this.sendMailToAdmin(mailSubject, mailText);

	}
	
	/** ====================================================================================== */

}
