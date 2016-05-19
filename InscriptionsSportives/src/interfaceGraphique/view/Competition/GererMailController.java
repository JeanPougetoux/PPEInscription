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
    	else if(stage.estCompetition())
    	{
        		this.valider.setDisable(true);
    	        this.annuler.setDisable(true);
        		GestionDesErreurs.afficherMessage(information, "Envoie des messages en cours, veuillez patienter...", "infos");
        		
        		 final Service<Void> calculateService = new Service<Void>() 
     	        {

     	            @Override
     	            protected Task<Void> createTask() 
     	            {
     	                return new Task<Void>() 
     	                {
    						@Override
     	                    protected  Void call() throws Exception 
     	                    {
    							if(GestionMail.open())
    							{
    								Set <Candidat> candidats =   stage.getCompet().getCandidats();
        	 	               		
        	 	               		for(Candidat c : candidats)
        	 	               		{
        	 	               			if(c instanceof Personne)
        	 	               			{
        	 	               				GestionMail.sendMessage(sujet.getText(), message.getText(), ((Personne) c).getMail());
        	 	               				
        	 	               			}
        	 	               			else if(c instanceof Equipe)
        	 	               			{
        	 	               				Set<Personne> membres = ((Equipe) c).getMembres();
        	 	               				for(Personne p : membres)
        	 	               				{
        	 	               					GestionMail.sendMessage(sujet.getText(), message.getText(), p.getMail());
        	 	               				}
        	 	               			}
        	 	               		}
        	 	               		GestionMail.close();
        	 	               		return null;
    							}
    							else
        						{
        							GestionDesErreurs.afficherMessage(information, "pas de connexion internet", "erreur");
        						}
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
    	                default:
    	    				this.valider.setDisable(false);
    	        	        this.annuler.setDisable(false);
    	    				GestionDesErreurs.afficherMessage(information, "Connexion internet innexistante", "erreur");
    	                	break;
    	           }
    	        });
    	        calculateService.start();
    	}
    	else if (stage.estPersonnel())
    	{
    		this.valider.setDisable(true);
	        this.annuler.setDisable(true);
    		GestionDesErreurs.afficherMessage(information, "Envoie du message en cours, veuillez patienter...", "infos");
    		
    		 final Service<Void> calculateService = new Service<Void>() 
  	        {

  	            @Override
  	            protected Task<Void> createTask() 
  	            {
  	                return new Task<Void>() 
  	                {
 						@Override
  	                    protected  Void call() throws Exception 
  	                    {
 							if(GestionMail.open())
 							{
 								GestionMail.sendMessage(sujet.getText(), message.getText(), stage.getPersonne().getMail());
 	 							GestionMail.close();
 							}
 							else
 							{
 								return null;
 							}
 							
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
	                	GestionDesErreurs.afficherMessage(information, "Connexion internet innexistante", "erreur");
	                	break;
	                case CANCELLED:
	                	GestionDesErreurs.afficherMessage(information, "Connexion internet innexistante", "erreur");
	                	break;
	                case SUCCEEDED:
	                	this.valider.setDisable(false);
	        	        this.annuler.setDisable(false);
	        	        GestionDesErreurs.afficherMessage(information, "Message envoyé avec succès !", "infos");
	                    break;
			default:
				this.valider.setDisable(false);
    	        this.annuler.setDisable(false);
				GestionDesErreurs.afficherMessage(information, "Connexion internet innexistante", "erreur");
            	break;
	            }
	        });
	        calculateService.start();
    		 
    	}
    	
    }

	public TextField getMessage() {
		return message;
	}

	public void setMessage(TextField message) {
		this.message = message;
	}
    
   
}

