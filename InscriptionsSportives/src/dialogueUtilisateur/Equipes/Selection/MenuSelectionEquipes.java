package dialogueUtilisateur.Equipes.Selection;

import inscriptions.Equipe;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

/**
 * Menu après avoir sélectionner une équipe, propose plusieurs actions à effectuer.
 * @author Jean
 *
 */
public class MenuSelectionEquipes extends Menu{
	
	private Inscriptions inscriptions;
	private Equipe equipe;	
	
	public MenuSelectionEquipes(Inscriptions inscriptions, Equipe equipe){
		super("\nGestion de l'équipe '" + equipe.getNom() + "'\nQue voulez-vous faire ?",
				"Gérer les équipes", "e");
		this.inscriptions = inscriptions;
		this.equipe = equipe;
		this.ajoute(new Option("Voir les membres de l'équipe", "v", new ActionVoirMembres(this)));
		this.ajoute(new Option("Ajouter une personne à l'équipe", "a", new ActionAjoutPersonne(this)));
		this.ajoute(new Option("Supprimer un membre de l'équipe", "s", new ActionSuppressionPersonne(this)));
		this.ajoute(new Option("Voir les compétitions de l'équipe", "c", new ActionVoirCompetitions(this)));
		this.ajoute(new Option("Modifier le nom de l'équipe", "m", new ActionModificationEquipe(this)));
		this.ajoute(new Option("Supprimer l'équipe", "d", new ActionSuppressionEquipe(this)));
		this.ajouteRevenir("r");
	}

	/**
	 * Retourne l'inscription en cours
	 * @return objet Inscriptions
	 */
	public Inscriptions getInscriptions(){
		return inscriptions;
	}
	
	/**
	 * Retourne l'équipe choisie
	 * @return objet Equipe
	 */
	public Equipe getEquipe(){
		return equipe;
	}
}
