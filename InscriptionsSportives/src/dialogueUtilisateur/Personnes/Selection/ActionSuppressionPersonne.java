package dialogueUtilisateur.Personnes.Selection;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionRetraitPersonneEquipe;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de supprimer la personne.
 * @author Jean
 *
 */
public class ActionSuppressionPersonne implements Action{
	
	private MenuSelectionPersonnes menu;

	public ActionSuppressionPersonne(MenuSelectionPersonnes menu){
		this.menu = menu;
	}

	public void optionSelectionnee(){
		char reponse = SaisiesConsole.saisieSuppression("la personne");
		if(reponse == 'o'){
			menu.setRetourAuto(true);
			try
			{
				menu.getPersonne().delete();
				System.out.println("\nPersonne bien effacée.");
			}
			catch (ExceptionRetraitPersonneEquipe e) 
			{
				System.out.println(e.toString());
			}
			
		}
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
