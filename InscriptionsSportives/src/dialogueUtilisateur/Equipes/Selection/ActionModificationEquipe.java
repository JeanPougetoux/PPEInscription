package dialogueUtilisateur.Equipes.Selection;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionNomEquipe;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de modifier le nom de l'équipe.
 * @author Jean
 *
 */
public class ActionModificationEquipe implements Action{
	
	private MenuSelectionEquipes menu;
	
	public ActionModificationEquipe(MenuSelectionEquipes menu){
		this.menu = menu;
	}

	public void optionSelectionnee(){
		menu.setRetourAuto(true);
		String nouveauNom = EntreesSorties.getString("Veuillez saisir le nouveau nom de l'équipe ou 'r' pour revenir"
											+ ", ancien nom : " + menu.getEquipe().getNom() + " nouveau nom : ");
		if(nouveauNom != "r" && !nouveauNom.isEmpty()){
			try {
				menu.getEquipe().setNom(nouveauNom);
				System.out.println("\nLe nom de l'équipe a bien été changé en : " + menu.getEquipe().getNom());
			} catch (ExceptionNomEquipe e) {
				System.out.println(e.toString());
			}
			
		}
		else if(nouveauNom.isEmpty()){
			System.out.println("Chaine vide.");
		}
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
