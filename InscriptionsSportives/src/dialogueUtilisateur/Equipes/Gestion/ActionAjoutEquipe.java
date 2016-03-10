package dialogueUtilisateur.Equipes.Gestion;

import dialogueUtilisateur.Utilitaires;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

public class ActionAjoutEquipe implements Action{

	private MenuGestionEquipes menu;
	
	/**
	 * Construit l'action liée au choix de 'ajouter une équipe' dans le menu Equipe.
	 */
	public ActionAjoutEquipe(MenuGestionEquipes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		String nomEquipe = EntreesSorties.getString("\nSaisir le nom de l'équipe.\n'a' pour annuler.");
		if(!nomEquipe.isEmpty() && !nomEquipe.equals("a")){
			menu.getInscriptions().createEquipe(nomEquipe);
			System.out.println("Equipe bien ajoutée.");
			Utilitaires.sauvegarde(menu.getInscriptions());
		}
	}
}
