package dialogueUtilisateur.Personnes.Selection;

import inscriptions.Equipe;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de voir les équipes auquelles appartient la personne.
 * @author Jean
 *
 */
public class ActionVoirEquipes implements Action{

	private MenuSelectionPersonnes menu;
	
	public ActionVoirEquipes(MenuSelectionPersonnes menu){
		this.menu = menu;
	}
	public void optionSelectionnee(){
		if(menu.getPersonne().getEquipes().isEmpty()){
			System.out.println("\nLa personne n'est membre d'aucune équipe.");
		}
		else{
			int i = 1;
			for(Equipe e : menu.getPersonne().getEquipes()){
				System.out.println("\n" + i + " : " + e.getNom());
				i++;
			}
		}
	}
}
