package dialogueUtilisateur;


import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Menu;

public class DialogueConsole {

	private GestionCompetitions c;
	private GestionEquipes e;
	private GestionPersonnes p;
	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	
	public DialogueConsole(){
		c = new GestionCompetitions(inscriptions);
		e = new GestionEquipes(inscriptions);
		p = new GestionPersonnes(inscriptions);
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
