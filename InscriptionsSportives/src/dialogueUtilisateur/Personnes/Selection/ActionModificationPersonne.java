package dialogueUtilisateur.Personnes.Selection;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionMailPersonne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de modifier chacun des champs pour une personne.
 * @author Jean
 *
 */
public class ActionModificationPersonne implements Action{

	private MenuSelectionPersonnes menu;

	public ActionModificationPersonne(MenuSelectionPersonnes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		menu.setRetourAuto(true);
		int mod = 0;
		String nom = "", prenom = "", mail = "";
		do{
			mod++;
			switch(mod){
			case 1:
				nom = EntreesSorties.getString("\nVeuillez saisir le nouveau nom. Ancien nom : " + 
									menu.getPersonne().getNom() + "\n'q' pour quitter, laissez vide pour ne rien changer.");
				mod = Utilitaires.getMod(mod, nom, true, false);
				if(!nom.isEmpty() && mod == 1){
					menu.getPersonne().setNom(nom);
					System.out.println("Le nom est bien changé en : " + menu.getPersonne().getNom());
				}
				break;
			case 2:
				prenom = EntreesSorties.getString("\nVeuillez saisir le prénom. Ancien prénom : " +
									menu.getPersonne().getPrenom() + "\n'q' pour quitter, 'r' pour revenir,"
											+ " laissez vide pour ne rien changer.");
				mod = Utilitaires.getMod(mod, prenom, true, false);
				if(!prenom.isEmpty() && mod == 2){
					menu.getPersonne().setPrenom(prenom);
					System.out.println("Le prénom a bien été changé en : " + menu.getPersonne().getPrenom());
				}
				break;
			case 3:
				mail = EntreesSorties.getString("\nVeuillez saisir le mail. Ancien mail : " +
									menu.getPersonne().getMail() + "\n'q' pour quitter, 'r' pour revenir,"
											+ " laissez vide pour ne rien changer.");
				mod = Utilitaires.getMod(mod, mail, true, true);
				if(!mail.isEmpty() && mod == 3){
					try {
						menu.getPersonne().setMail(mail);
						System.out.println("Le mail a bien été changé en : " + menu.getPersonne().getMail());
					} catch (ExceptionMailPersonne e) {
						// TODO Auto-generated catch block
						System.out.println(e.toString());
					}
					
				}
				break;
			}
		}while(mod < 5);
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
