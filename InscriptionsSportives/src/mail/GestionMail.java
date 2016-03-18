package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class GestionMail 
{

	/**
	 * Permet d'envoyer un mail avec un sujet, un texte et un destinataire
	 * @param subject
	 * @param text
	 * @param destinataire
	 * @return
	 */
	public static boolean sendMessage(String subject, String text, String destinataire) 
	{ 
	    // 1 -> Création de la session 
	    Properties properties = new Properties(); 
	    properties.setProperty("mail.transport.protocol", "smtp"); 
	    properties.setProperty("mail.smtp.host", "smtp.gmail.com"); 
	    properties.setProperty("mail.smtp.user", "m2lcompetitions@gmail.com"); 
	    properties.setProperty("mail.from", "m2lcompetitions@gmail.com"); 
	    properties.setProperty("mail.smtp.starttls.enable", "true");
	    Session session = Session.getInstance(properties); 
	    
	    
	    // 2 -> Création du message 
	    
	    MimeMessage message = new MimeMessage(session); 
	    Transport transport = null;
	    
	    try 
	    { 
	        message.setText(text); 
	        message.setSubject(subject); 
	        message.addRecipients(Message.RecipientType.TO, destinataire); 
	    } 
	    catch (MessagingException e) 
	    { 
	        e.printStackTrace(); 
	    } 
	    
	    // 3 -> Envoi du message 
	    try 
	    { 
	        transport = session.getTransport("smtp"); 
	        transport.connect("m2lcompetitions@gmail.com", "pougetoux123"); 
	        transport.sendMessage(message, message.getAllRecipients()); 
	    } 
	    catch (MessagingException e)
	    { 
	        e.printStackTrace(); 
	    } 
	    finally 
	    { 
	        try 
	        { 
	            if (transport != null) 
	            { 
	                transport.close(); 
	            } 
	        } 
	        catch (MessagingException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	    } 
	    return true;
	}
}
