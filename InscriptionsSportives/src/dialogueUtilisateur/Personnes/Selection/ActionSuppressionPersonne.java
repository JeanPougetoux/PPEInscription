package dialogueUtilisateur.Personnes.Selection;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import utilitaires.ligneDeCommande.Action;

public class ActionSuppressionPersonne implements Action{
	
	private MenuSelectionPersonnes menu;
	/**
	 * Permet de supprimer la personne.
	 * @param menu
	 */
	public ActionSuppressionPersonne(MenuSelectionPersonnes menu){
		this.menu = menu;
	}

	public void optionSelectionnee(){
		char reponse = SaisiesConsole.saisieSuppression("la personne");
		if(reponse == 'o'){
			menu.setRetourAuto(true);
			menu.getPersonne().delete();
			System.out.println("\nPersonne bien effacée.");
		}
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
