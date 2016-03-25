package dialogueUtilisateur.Personnes.Gestion;

import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

/**
 * Menu de gestion des personnes, permet de voir leurs détails, d'en ajouter une, etc.
 * @author Jean
 *
 */
public class MenuGestionPersonnes extends Menu{

	private Inscriptions inscriptions;
	public MenuGestionPersonnes(Inscriptions inscriptions){
		super("\nGestion des personnes\nQue-voulez-vous faire ?",
				"Gérer les personnes", "p");
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Détails des personnes", "v", new ActionVoirPersonnes(this)));
		this.ajoute(new Option("Ajouter une personne", "a", new ActionAjoutPersonne(this)));
		this.ajoute(new Option("Sélectionner une personne", "s", new ActionSelectionPersonne(this)));
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
