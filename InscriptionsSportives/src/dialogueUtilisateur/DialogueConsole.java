package dialogueUtilisateur;


import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;

public class DialogueConsole {

	private GestionCompetitions c;
	private GestionEquipes e;
	private GestionPersonnes p;
	
	public DialogueConsole(){
		c = new GestionCompetitions(Inscriptions.getInscriptions());
		e = new GestionEquipes(Inscriptions.getInscriptions());
		p = new GestionPersonnes(Inscriptions.getInscriptions());
	}
	
	/**
	 * Retourne l'instance du menu principal
	 * @return instance du menu de type menu
	 */
	public Menu getMenuPrincipal(){
		Menu menuPrincipal = new Menu("Bienvenue dans le gestionnaire de compétitions M2L\n"
				+ "Que voulez-vous faire ?");
		menuPrincipal.ajoute(c.getMenu());
		menuPrincipal.ajoute(e.getMenu());
		menuPrincipal.ajoute(p.getMenu());
		menuPrincipal.ajouteQuitter("q");
		return menuPrincipal;
	}
}
