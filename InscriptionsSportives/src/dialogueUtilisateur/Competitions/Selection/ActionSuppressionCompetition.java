package dialogueUtilisateur.Competitions.Selection;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import utilitaires.ligneDeCommande.Action;

/**
 * Permet de supprimer la compétition.
 * @author Jean
 *
 */
public class ActionSuppressionCompetition implements Action{

	private MenuSelectionCompetitions menu;

	public ActionSuppressionCompetition(MenuSelectionCompetitions menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee() {
		char reponse = SaisiesConsole.saisieSuppression("la compétition");
		if(reponse == 'o'){
			menu.setRetourAuto(true);
			menu.getCompetition().delete();
			System.out.println("Compétition bien effacée.");
			Utilitaires.sauvegarde(menu.getInscriptions());
		}
	}
}
