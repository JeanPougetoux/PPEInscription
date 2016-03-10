package dialogueUtilisateur.Personnes.Selection;

import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class MenuSelectionPersonnes extends Menu{
	
	public Inscriptions inscriptions;
	private Personne personne;
	
	
	public MenuSelectionPersonnes(Inscriptions inscriptions, Personne personne){
		super("\nGestion de la personne '" + personne.toString() +
				"'\nQue voulez-vous faire ?", "Gérer la personne", "p");
		this.inscriptions = inscriptions;
		this.personne = personne;
		this.ajoute(new Option("Voir ses équipes", "v", new ActionVoirEquipes(this)));
		this.ajoute(new Option("Ajouter à une équipe", "a", new ActionAjoutEquipe(this)));
		this.ajoute(new Option("Supprimer d'une équipe", "s", new ActionSuppressionEquipe(this)));
		this.ajoute(new Option("Voir les compétitions", "c", new ActionVoirCompetitions(this)));
		this.ajoute(new Option("Modifier la personne", "m", new ActionModificationPersonne(this)));
		this.ajoute(new Option("Supprimer la personne", "d", new ActionSuppressionPersonne(this)));
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
	 * Retourne la personne choisie
	 * @return objet Personne
	 */
	public Personne getPersonne(){
		return personne;
	}
}
