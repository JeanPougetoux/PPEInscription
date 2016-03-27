package mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("m2lcompetitions@gmail.com","pougetoux123");
			}
		});
		
		Message msg = new MimeMessage(session);
		try 
		{
			Address adresse = new InternetAddress("thomasecalle@pataprout.com");
			
			msg.setFrom(adresse);
			Address toAdresse = new InternetAddress(destinataire);
			
			msg.addRecipient(Message.RecipientType.TO, toAdresse);
			
			msg.setSubject(subject);
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setContent(text, "text/html");
			
			Multipart multipart = new MimeMultipart();
			
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			
			Transport transport = session.getTransport("smtp");
			
			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());
			
			transport.close();
			return true;
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		if(sendMessage("salut","<h1>voici un titre</h1><p>un paragraphe</p><p>et un deuxieme paragraphe</p>","thomasecalle@hotmail.fr"));
		{
			System.out.println("message envoyé !");
		}
	}
}
