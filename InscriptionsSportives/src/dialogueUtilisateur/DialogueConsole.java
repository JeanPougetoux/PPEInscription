package dialogueUtilisateur;


import dialogueUtilisateur.Competitions.Gestion.MenuGestionCompetitions;
import dialogueUtilisateur.Equipes.Gestion.MenuGestionEquipes;
import dialogueUtilisateur.Personnes.Gestion.MenuGestionPersonnes;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;

public class DialogueConsole extends Menu{

	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	
	public DialogueConsole(){
		super("Bienvenue dans le gestionnaire de compétitions M2L\n"
				+ "Que voulez-vous faire ?");
		this.ajoute(new MenuGestionCompetitions(inscriptions));
		this.ajoute(new MenuGestionEquipes(inscriptions));
		this.ajoute(new MenuGestionPersonnes(inscriptions));
		this.ajouteQuitter("q");
	}
}
