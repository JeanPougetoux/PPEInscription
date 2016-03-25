package dialogueUtilisateur.Equipes.Selection;

import inscriptions.Competition;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de voir tous les membres de l'équipe.
 * @author Jean
 *
 */
public class ActionVoirCompetitions implements Action{
	
	private MenuSelectionEquipes menu;

	public ActionVoirCompetitions(MenuSelectionEquipes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee() {
		if(menu.getEquipe().getCompetitions().isEmpty()){
			System.out.println("\nEquipe inscrite à aucune compétition.");
		}
		else{
			int i=1;
			System.out.println("\nLes compétitions auxquelles est inscrite l'équipe sont : \n");
			for(Competition c : menu.getEquipe().getCompetitions()){
				System.out.println(i + ": " + c.toString());
				i++;
			}
		}
	}

}
