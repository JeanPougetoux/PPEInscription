package interfaceGraphique.view.Competition;

import java.io.IOException;
import java.time.LocalDate;

import exceptions.ExceptionCompetition;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.AjoutCompetition;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AjoutCompetitionController {

	@FXML
    private TextField nomCompetition;
	@FXML
	private DatePicker dateCloture;
	@FXML
	private ComboBox<String> enEquipe;
	@FXML 
	private Button valider;
	@FXML
	private Button annuler;
	
	public AjoutCompetitionController(){
		
	}
	
	@FXML
	private void initialize(){
		enEquipe.getItems().addAll("oui", "non");
		enEquipe.promptTextProperty().set("oui");
	}	
	
	public void setStagesCompetition(AjoutCompetition stageAjout, GestionCompetitions stageGestion){
		annuler.setOnAction(new ActionAnnuler(stageAjout));
		valider.setOnAction(new ActionValider(stageAjout, this, stageGestion));
	}
	
	public String getStringNom(){
		return nomCompetition.getText();
	}
	
	public LocalDate getLocalDateCloture(){
		return dateCloture.getValue();
	}
	
	public boolean getEnEquipe(){
		if(enEquipe.getPromptText().equals("oui"))
			return true;
		else
			return false;
	}
}

class ActionAnnuler implements EventHandler<ActionEvent>{

	private AjoutCompetition stage;
	public ActionAnnuler(AjoutCompetition stage) {
		this.stage = stage;
	}
	@Override
	public void handle(ActionEvent event) {
		stage.close();
	}
	
}

class ActionValider implements EventHandler<ActionEvent>{

	private AjoutCompetition stageAjout;
	private GestionCompetitions stageGestion;
	private AjoutCompetitionController controlAjout;
	public ActionValider(AjoutCompetition stageAjout, AjoutCompetitionController controlAjout
							, GestionCompetitions stageGestion) {
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
		this.controlAjout = controlAjout;
	}
	@Override
	public void handle(ActionEvent event) {
		try {
			stageGestion.getList().add(MonAppli.getInscriptions().createCompetition(
												controlAjout.getStringNom(), controlAjout.getLocalDateCloture(),
												controlAjout.getEnEquipe()));
		} catch (ExceptionCompetition e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stageAjout.close();
		
	}
	
}
