package dialogueUtilisateur.Equipes.Gestion;

import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

/**
 * Menu de gestion des équipes, permet de voir les détails de celles-ci, d'en ajouter, etc.
 * @author Jean
 *
 */
public class MenuGestionEquipes extends Menu{

	private Inscriptions inscriptions;
	
	public MenuGestionEquipes(Inscriptions inscriptions){
		super("\nGestion des équipes\nQue-voulez-vous faire ?",
				"Gérer les équipes", "e");
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Détails des équipes", "v", new ActionVoirEquipes(this)));
		this.ajoute(new Option("Ajouter une équipe", "a", new ActionAjoutEquipe(this)));
		this.ajoute(new Option("Sélectionner une équipe", "s", new ActionSelectionEquipe(this)));
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
