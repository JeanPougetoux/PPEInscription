package dialogueUtilisateur.Competitions.Gestion;

import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class MenuGestionCompetitions extends Menu{
	
	private Inscriptions inscriptions;
	
	public MenuGestionCompetitions(Inscriptions inscriptions){
		super("\nGestion des compétitions\nQue voulez-vous faire ?",
				"Gérer les compétitions", "c");
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Détails des compétitions", "v", new ActionVoirCompetitions(this)));
		this.ajoute(new Option("Ajouter une compétition", "a", new ActionAjoutCompetition(this)));
		this.ajoute(new Option("Sélectionner une compétition", "s", new ActionSelectionCompetition(this)));
		this.ajouteRevenir("r");
	}
	
	/**
	 * Retourne l'objet inscriptions
	 * @return objet Inscription
	 */
	public Inscriptions getInscriptions(){
		return inscriptions;
	}
}
