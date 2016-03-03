package dialogueUtilisateur;


import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class DialogueConsole {

	private GestionCompetitions c;
	private GestionEquipes e;
	
	public DialogueConsole(){
		c = new GestionCompetitions(Inscriptions.getInscriptions());
		e = new GestionEquipes(Inscriptions.getInscriptions());
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
		//menuPrincipal.ajoute();
		menuPrincipal.ajouteQuitter("q");
		return menuPrincipal;
	}
	

	
//	/**
//	 * Permet de créer le menu candidats et y ajout les différentes options
//	 * ainsi que l'action 'revenir'.
//	 * @return le menu candidat de type menu.
//	 */
//	public Menu buildMenuPersonne(){
//		Menu candidats = new Menu("Gestion des personnes\nQue-voulez-vous faire ?",
//				"Gérer les personnes", "p");
//		candidats.ajoute(new Option("Voir les personnes", "v", getActionVoirCandidats()));
//		candidats.ajoute(new Option("Ajouter une personne", "a", getActionAjoutCandidat()));
//		candidats.ajoute(new Option("Sélectionner une personne", "s", getActionSelectionCandidat()));
//		candidats.ajouteRevenir("r");
//		return candidats;
//	}
	


}
