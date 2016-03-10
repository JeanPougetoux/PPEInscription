package dialogueUtilisateur.Personnes.Gestion;

import dialogueUtilisateur.Utilitaires;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

public class ActionAjoutPersonne implements Action{

	private MenuGestionPersonnes menu;
		
	/**
	 * Permet l'ajout d'une nouvelle personne.
	 */
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
				mod = Utilitaires.getMod(mod, nom, false);
				break;
			case 2:
				prenom = EntreesSorties.getString("Veuillez saisir le prénom. 'q' pour quitter, 'r' pour revenir.");
				mod = Utilitaires.getMod(mod, prenom, false);
				break;
			case 3:
				mail = EntreesSorties.getString("Veuillez saisir le mail. 'q' pour quitter, 'r' pour revenir.");
				mod = Utilitaires.getMod(mod, mail, false);
				break;
			}
		}while(mod < 4);
		if(mod < 5){
			menu.getInscriptions().createPersonne(nom, prenom, mail);
			System.out.println("\nLa personne vient bien d'être créée.");
			Utilitaires.sauvegarde(menu.getInscriptions());
		}
	}
}
