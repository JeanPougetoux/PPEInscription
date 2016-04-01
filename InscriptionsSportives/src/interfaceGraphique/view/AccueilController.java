package interfaceGraphique.view;

import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Personne.GestionPersonne;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller de la vue AccueilController.fxml
 * et appellé par la classe Accueil.
 * @author Jean
 *
 */
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
	
	/**
	 * Correspond à l'action du clic sur le bouton compétitions.
	 */
	public void boutonCompetition(){
		GestionCompetitions fenetreCompetition = new GestionCompetitions();
		fenetreCompetition.show();
	}
	
	/**
	 * Correspond à l'action du clic sur le bouton équipes.
	 */
	public void boutonEquipe(){
		GestionEquipe fenetreEquipe = new GestionEquipe();
		fenetreEquipe.show();
	}
	
	/**
	 * Correspond à l'action du clic sur le bouton personnes.
	 */
	public void boutonPersonne()
	{
		GestionPersonne fenetrePersonne = new GestionPersonne();
		fenetrePersonne.show();
	}
}
