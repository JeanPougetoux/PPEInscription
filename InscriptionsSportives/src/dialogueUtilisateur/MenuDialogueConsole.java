package dialogueUtilisateur;


import dialogueUtilisateur.Competitions.Gestion.MenuGestionCompetitions;
import dialogueUtilisateur.Equipes.Gestion.MenuGestionEquipes;
import dialogueUtilisateur.Personnes.Gestion.MenuGestionPersonnes;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;

/**
 * Premier menu ouvert dans la classe Main, propose le choix entre
 * compétition, équipe et personne.
 * @author Jean
 *
 */
public class MenuDialogueConsole extends Menu{

	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	
	public MenuDialogueConsole(){
		super("Bienvenue dans le gestionnaire de compétitions M2L\n"
				+ "Que voulez-vous faire ?");
		this.ajoute(new MenuGestionCompetitions(inscriptions));
		this.ajoute(new MenuGestionEquipes(inscriptions));
		this.ajoute(new MenuGestionPersonnes(inscriptions));
		this.ajouteQuitter("q");
	}
}
