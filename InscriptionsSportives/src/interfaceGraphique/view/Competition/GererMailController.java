package interfaceGraphique.view.Competition;

import java.util.Set;



import javafx.scene.control.Label;

import inscriptions.Candidat;
import inscriptions.Personne;
import interfaceGraphique.controls.Competition.GererCandidats;
import interfaceGraphique.controls.Competition.GererMail;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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

public class GererMailController {


	@FXML
    private TableView<Candidat> candidatsCompetition = new TableView<Candidat>();
    
	
	@FXML
    private TextField sujet;
	@FXML
    private TextField message;
	
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
    	if (this.sujet.getText().isEmpty() || this.message.getText().isEmpty())
    	{
    			generationInfos("Le sujet et le message doivent tout deux être renseignés","erreur");
    	}
    	else
    	{
    		generationInfos("Envoie en cours veuillez patienter...","infos");
    		
    		Set <Candidat> candidats = stage.getCompet().getCandidats();
    		int compteur = 0;
    		
    		for(Candidat c : candidats)
    		{
    			if(c instanceof Personne)
    			{
    				compteur ++;
        			if(GestionMail.sendMessage(this.sujet.getText(), this.message.getText(),((Personne) c).getMail()));
        			{
        				generationInfos("Message "+compteur+" bien envoyé ("+compteur+"/"+candidats.size()+")","infos");
        			}
    			}
    			
    		}
    		
    	}
    }
    
    public void generationInfos(String message,String type)
	{
    	if(type == "erreur")
    		this.information.setTextFill(Color.web("#FF0000"));
    	else
    		this.information.setTextFill(Color.web("green"));
    	this.information.setText(message);
    	
		this.information.setVisible(true);
	}
    
   
}

