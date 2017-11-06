package epimed_web.controller.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import epimed_web.service.log.ApplicationLogger;

@Controller
public class MailController extends ApplicationLogger {

	@Autowired
    public JavaMailSender emailSender;
	
	
	/** ====================================================================================== */

	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setFrom("EpiMed Database <iab-epimed@univ-grenoble-alpes.fr>");
		message.setReplyTo("ekaterina.flin@univ-grenoble-alpes.fr");
        message.setTo("ekaterina.flin@univ-grenoble-alpes.fr"); 
        message.setSubject("TEST"); 
        message.setText("IT WORKS!");
        emailSender.send(message);

        model.addAttribute("message", "A e-mail has been sent to " + message.toString());
        
		return "message";
	}

	/** ====================================================================================== */

	
}
