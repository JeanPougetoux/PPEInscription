package dialogueUtilisateur.Equipes.Selection;

import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de voir tous les membres de l'équipe.
 * @author Jean
 *
 */
public class ActionVoirMembres implements Action{
	
	private MenuSelectionEquipes menu;
	
	public ActionVoirMembres(MenuSelectionEquipes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee() {
		if(menu.getEquipe().getMembres().isEmpty()){
			System.out.println("Il n'y a aucun membre.");
		}
		else{
			int i=1;
			System.out.println("Les membres sont : \n");
			for(Personne p : menu.getEquipe().getMembres()){
				System.out.println(i + ": " + p.toString());
				i++;
			}
		}
	}

}
