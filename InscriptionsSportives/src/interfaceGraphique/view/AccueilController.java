package interfaceGraphique.view;

import interfaceGraphique.controls.Accueil;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Personne.GestionPersonne;
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

	}	
	
	public void boutonCompetition(){
		GestionCompetitions fenetreCompetition = new GestionCompetitions();
		fenetreCompetition.show();
	}
	
	public void boutonEquipe(){
		GestionEquipe fenetreEquipe = new GestionEquipe();
		fenetreEquipe.show();
	}

	public void boutonPersonne()
	{
		GestionPersonne fenetrePersonne = new GestionPersonne();
		fenetrePersonne.show();
	}
}
