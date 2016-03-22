package interfaceGraphique.view;

import interfaceGraphique.controls.Accueil;
import interfaceGraphique.controls.GestionCompetitions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccueilController {

	@FXML
	private Button competition;
	@FXML
	private Button equipe;
	@FXML
	private Button personne;
	
	public AccueilController(){
		
	}
	
	@FXML
	private void initialize(){
		competition.setOnAction(new ActionCompetition());
	}	
}

class ActionCompetition implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		GestionCompetitions fenetreCompetition = new GestionCompetitions();
		fenetreCompetition.show();
	}
	
}
