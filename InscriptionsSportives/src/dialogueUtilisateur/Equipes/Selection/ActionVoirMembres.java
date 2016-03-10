package dialogueUtilisateur.Equipes.Selection;

import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

public class ActionVoirMembres implements Action{
	
	private MenuSelectionEquipes menu;
	
	/**
	 * Permet de voir tous les membres de l'équipe
	 */
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
