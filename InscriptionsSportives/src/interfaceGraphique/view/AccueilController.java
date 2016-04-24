package interfaceGraphique.view;

import inscriptions.Candidat;
import inscriptions.Competition;
import interfaceGraphique.controls.MonAppli;
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
		for(Candidat c : MonAppli.getInscriptions().getCandidats())
			System.out.println(c.getId()+" : "+c.getNom());
		System.out.println("****************************************");
		for(Competition comp : MonAppli.getInscriptions().getCompetitions())
			System.out.println(comp.getId()+ " : "+comp.getNom());
	}
	
	/**
	 * Correspond à l'action du clic sur le bouton équipes.
	 */
	public void boutonEquipe(){
		GestionEquipe fenetreEquipe = new GestionEquipe();
		fenetreEquipe.show();
		for(Candidat c : MonAppli.getInscriptions().getCandidats())
			System.out.println(c.getId()+" : "+c.getNom());
		System.out.println("****************************************");
		for(Competition comp : MonAppli.getInscriptions().getCompetitions())
			System.out.println(comp.getId()+ " : "+comp.getNom());
	}
	
	/**
	 * Correspond à l'action du clic sur le bouton personnes.
	 */
	public void boutonPersonne()
	{
		GestionPersonne fenetrePersonne = new GestionPersonne();
		fenetrePersonne.show();
		for(Candidat c : MonAppli.getInscriptions().getCandidats())
			System.out.println(c.getId()+" : "+c.getNom());
		System.out.println("****************************************");
		for(Competition comp : MonAppli.getInscriptions().getCompetitions())
			System.out.println(comp.getId()+ " : "+comp.getNom());
	}
}
