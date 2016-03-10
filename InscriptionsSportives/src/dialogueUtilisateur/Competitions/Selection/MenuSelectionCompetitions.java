package dialogueUtilisateur.Competitions.Selection;

import dialogueUtilisateur.Utilitaires;
import inscriptions.Competition;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

/**
 * Menu après avoir sélectionner une compétition, propose plusieurs actions à effectuer.
 * @author Jean
 *
 */
public class MenuSelectionCompetitions extends Menu{
	
	private Competition competition;
	private Inscriptions inscriptions;
	
	public MenuSelectionCompetitions(Competition competition, Inscriptions inscriptions){
		super("\nGestion de la compétition '" + competition.getNom() + "'\n"
				+ "Date de clôture : " + competition.getDateCloture() + "\nEn équipe : " 
				+ Utilitaires.getOuiNon(competition.estEnEquipe()) 
				+ "\nQue voulez-vous faire ?", "Gérer les compétitions", "c");
		this.competition = competition;
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Voir les candidats", "v", new ActionVoirCandidats(this)));
		this.ajoute(new Option("Ajouter un candidat", "a", new ActionAjoutCandidat(this)));
		this.ajoute(new Option("Supprimer un candidat", "s", new ActionSuppressionCandidat(this)));
		this.ajoute(new Option("Modifier la compétition", "m", new ActionModificationCompetition(this)));
		this.ajoute(new Option("Supprimer la compétition", "d", new ActionSuppressionCompetition(this)));
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
	 * Retourne la compétition choisie
	 * @return objet Competition
	 */
	public Competition getCompetition(){
		return competition;
	}
}
