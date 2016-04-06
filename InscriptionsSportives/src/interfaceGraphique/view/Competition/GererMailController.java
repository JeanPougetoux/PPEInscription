package interfaceGraphique.view.Competition;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

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

import dialogueUtilisateur.GestionDesErreurs;
import javafx.scene.control.Label;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.Competition.GererCandidats;
import interfaceGraphique.controls.Competition.GererMail;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import mail.GestionMail;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;

public class GererMailController {


	@FXML
    private TableView<Candidat> candidatsCompetition = new TableView<Candidat>();
    
	
	@FXML
    private TextField sujet;
	@FXML
    private TextField message;
	
	@FXML
	private Button valider;
	

	@FXML
	private Button annuler;
	@FXML
    private Label information;
	
    private GererMail stage;
    
    public GererMailController()
    {
    	
    }
    
    @FXML
    private void initialize()
    {
    	
    }
    
    public void setClass(GererMail stage)
    {
    	this.stage = stage;
    	candidatsCompetition.setItems(stage.getListCandidats());
    	this.information.setVisible(false);
    }
    
    public void handleAnnuler()
    {
    	stage.close();
    }
    
    public void handleValider()
    {
    	if (this.sujet.getText().isEmpty() || this.getMessage().getText().isEmpty())
    	{
    		GestionDesErreurs.afficherMessage(information, "Le sujet et le message doivent tout deux être renseignés", "erreur");
    	}
    	else
    	{
    		this.valider.setDisable(true);
	        this.annuler.setDisable(true);
    		GestionDesErreurs.afficherMessage(information, "Envoie des messages en cours, veuillez patienter...", "infos");
    		
    		 final Service<Void> calculateService = new Service<Void>() 
 	        {

 	            @Override
 	            protected Task<Void> createTask() 
 	            {
 	                return new Task<Void>() {
 	                	
 	                	
 	                    

						@Override
 	                    protected  Void call() throws Exception 
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
	 	               		
	 	               		Transport transport = session.getTransport("smtp");
	               			
	               			transport.connect();
	 	               		
	 	               		
 	                    	Set <Candidat> candidats = stage.getCompet().getCandidats();
	 	               		int compteur = 0;
	 	               		
	 	               		for(Candidat c : candidats)
	 	               		{
	 	               			if(c instanceof Personne)
	 	               			{
	 	               				compteur++;
	 	               				
			 	               		Message msg = new MimeMessage(session);
			 	               		try 
			 	               		{
			 	               			Address adresse = new InternetAddress("thomasecalle@pataprout.com");
			 	               			
			 	               			msg.setFrom(adresse);
			 	               			Address toAdresse = new InternetAddress(((Personne) c).getMail());
			 	               			
			 	               			msg.addRecipient(Message.RecipientType.TO, toAdresse);
			 	               			
			 	               			msg.setSubject(sujet.getText());
			 	               			
			 	               			MimeBodyPart messageBodyPart = new MimeBodyPart();
			 	               			
			 	               			messageBodyPart.setContent(message.getText(), "text/html");
			 	               			
			 	               			Multipart multipart = new MimeMultipart();
			 	               			
			 	               			multipart.addBodyPart(messageBodyPart);
			 	               			msg.setContent(multipart);
			 	               			
			 	               			
			 	               			transport.sendMessage(msg, msg.getAllRecipients());
		
			 	               		} 
			 	               		catch (MessagingException e) 
			 	               		{
			 	               			e.printStackTrace();
			 	               		}
	 	               			}
	 	               			else if(c instanceof Equipe)
	 	               			{
	 	               				Set<Personne> membres = ((Equipe) c).getMembres();
	 	               				for(Personne p : membres)
	 	               				{
		 	               				compteur++;
		 	               				
				 	               		
				 	               		
				 	               		Message msg = new MimeMessage(session);
				 	               		try 
				 	               		{
				 	               			Address adresse = new InternetAddress("thomasecalle@pataprout.com");
				 	               			
				 	               			msg.setFrom(adresse);
				 	               			Address toAdresse = new InternetAddress(((Personne) c).getMail());
				 	               			
				 	               			msg.addRecipient(Message.RecipientType.TO, toAdresse);
				 	               			
				 	               			msg.setSubject(sujet.getText());
				 	               			
				 	               			MimeBodyPart messageBodyPart = new MimeBodyPart();
				 	               			
				 	               			messageBodyPart.setContent(message.getText(), "text/html");
				 	               			
				 	               			Multipart multipart = new MimeMultipart();
				 	               			
				 	               			multipart.addBodyPart(messageBodyPart);
				 	               			msg.setContent(multipart);
				 	               			
				 	               			
				 	               			transport.sendMessage(msg, msg.getAllRecipients());
				 	               			
				 	               			
				 	               		} 
				 	               		catch (MessagingException e) 
				 	               		{
				 	               			e.printStackTrace();
				 	               		}
	 	               				}
	 	               			}
	 	               			
	 	               		}
	 	               	transport.close();
	 	               		return null;
 	                    }
 	                };
 	            }
 	        };
 	        
 	        
 	      calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> 
	        {
	           switch (newValue) 
	           {
	                case FAILED:
	                case CANCELLED:
	                case SUCCEEDED:
	                	this.valider.setDisable(false);
	        	        this.annuler.setDisable(false);
	        	        GestionDesErreurs.afficherMessage(information, "Tous les messages ont été envoyé avec succès !", "infos");
	                    break;
	            }
	        });
 	        
 	  
	        calculateService.start();
	        
    		
    		
    		/*Set <Candidat> candidats = stage.getCompet().getCandidats();
        		int compteur = 0;
        		
        		for(Candidat c : candidats)
        		{
        			if(c instanceof Personne)
        			{
        				compteur++;
        				GestionMail.sendMessage(sujet.getText(),getMessage().getText(),((Personne)c).getMail());
					}
        			
        			else if(c instanceof Equipe)
        			{
        				Set<Personne> membres = ((Equipe) c).getMembres();
        				for(Personne p : membres)
        				{
        					compteur ++;
                			GestionMail.sendMessage(sujet.getText(),getMessage().getText(),p.getMail());
        				}
        			}
        			
        		}
        		GestionDesErreurs.afficherMessage(information, "tous les messages ont été envoyés !", "infos");*/
    		
    		
    	}
    }

	public TextField getMessage() {
		return message;
	}

	public void setMessage(TextField message) {
		this.message = message;
	}
    
   
}

