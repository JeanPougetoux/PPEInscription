package dialogueUtilisateur.Equipes.Selection;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de supprimer l'équipe.
 * @author Jean
 *
 */
public class ActionSuppressionEquipe implements Action{
	
	private MenuSelectionEquipes menu;

	public ActionSuppressionEquipe(MenuSelectionEquipes menu){
		this.menu = menu;
	}

	public void optionSelectionnee(){
		char reponse = SaisiesConsole.saisieSuppression("l'équipe");
		if(reponse == 'o'){
			menu.setRetourAuto(true);
			
				menu.getEquipe().delete();
				System.out.println("Equipe bien effacée.");
			
			
		}
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
