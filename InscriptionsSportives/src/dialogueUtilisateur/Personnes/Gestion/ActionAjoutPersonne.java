package dialogueUtilisateur.Personnes.Gestion;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionMailPersonne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant l'ajout d'une nouvelle personne.
 * @author Jean
 *
 */
public class ActionAjoutPersonne implements Action{

	private MenuGestionPersonnes menu;

	public ActionAjoutPersonne(MenuGestionPersonnes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		int mod = 0;
		String nom = "", prenom = "", mail = "";
		do{
			mod++;
			switch(mod){
			case 1:
				nom = EntreesSorties.getString("Veuillez saisir le nom. 'q' pour quitter.");
				mod = Utilitaires.getMod(mod, nom, false, false);
				break;
			case 2:
				prenom = EntreesSorties.getString("Veuillez saisir le prénom. 'q' pour quitter, 'r' pour revenir.");
				mod = Utilitaires.getMod(mod, prenom, false, false);
				break;
			case 3:
				mail = EntreesSorties.getString("Veuillez saisir le mail. 'q' pour quitter, 'r' pour revenir.");
				mod = Utilitaires.getMod(mod, mail, false, true);
				break;
			}
		}while(mod < 4);
		if(mod < 5){
			try 
			{
				menu.getInscriptions().createPersonne(nom, prenom, mail,menu.getInscriptions().pers.getLastInsertCandidat());
				System.out.println("\nLa personne vient bien d'être créée.");
			} 
			catch (ExceptionMailPersonne e) 
			{
				System.out.println(e.toString());
			}
			
			Utilitaires.sauvegarde(menu.getInscriptions());
		}
	}
}
