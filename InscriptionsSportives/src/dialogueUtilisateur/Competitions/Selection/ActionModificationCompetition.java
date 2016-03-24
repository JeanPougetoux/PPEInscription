package dialogueUtilisateur.Competitions.Selection;

import java.time.LocalDate;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionCompetition;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

/**
 * Permet de modifier chaque champ de la compétition.
 * @author Jean
 *
 */
public class ActionModificationCompetition implements Action{
	
	private MenuSelectionCompetitions menu;
	
	public ActionModificationCompetition(MenuSelectionCompetitions menu){
		this.menu = menu;
	}

	public void optionSelectionnee() {
		menu.setRetourAuto(true);
		int mod = 0;
		do{
			mod++;
			switch(mod){
				case 1:
					String nom = EntreesSorties.getString("\nVoulez-vous changer le nom de la compétition ?\n" +
							"Le nom actuel est " + menu.getCompetition().getNom() + ".\n" +
							"Laissez l'espace vide pour ne rien changer, 'q' pour quitter.");
					if(!nom.isEmpty() && !nom.equals("q")){
						menu.getCompetition().setNom(nom);
						System.out.println("Le nom a bien été changé.");
					}
					else{
						mod = Utilitaires.getMod(mod, nom, true, true);
					}
					break;
				case 2: 
					Object date = SaisiesConsole.saisieDateCompetition("\nSaisir la date de clôture de la "
							+ "compétition (au format yyyy-MM-dd).\nLa date actuelle est " +
							menu.getCompetition().getDateCloture() + ".\nLaissez l'espace" +
							" vide pour ne rien changer, 'q' pour quitter, 'r' pour revenir.", 
							menu.getCompetition().getDateCloture());
					if(date instanceof LocalDate){
						menu.getCompetition().setDateCloture((LocalDate)date);
						System.out.println("La date de cloture a bien été changée.");
					} 
					else{
						mod = Utilitaires.getMod(mod, date, true, true);
					}
					break;
				case 3:
					Object estEnEquipe = SaisiesConsole.saisieEquipeCompetition("\nLa compétition est-elle pour "
							+ "les équipes ou les personnes seules ?\n(tapez 'e' pour équipes ou 'p' pour "
							+ "personnes)\nLa compétition autorise-elle actuellement les équipes : " 
							+ Utilitaires.getOuiNon(menu.getCompetition().estEnEquipe()) + ".\n" + "Laissez l'espace vide pour ne rien changer, "
							+ "'q' pour quitter, 'r' pour revenir.", true);
					
					if(estEnEquipe instanceof Boolean){
						if(menu.getCompetition().getCandidats().isEmpty()){
							try {
								menu.getCompetition().setEstEnEquipe((boolean)estEnEquipe);
							} catch (ExceptionCompetition e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
							System.out.println("Le mode de compétition est bien changé.");
						}
						else{
							System.out.println("Vous devez enlever tous les candidats de la compétition avant "
									+ "de changer son statut.");
						}
					}
					else if(estEnEquipe instanceof String){
						mod = Utilitaires.getMod(mod, estEnEquipe, true, true);
					}
					break;
			}
		}while(mod < 5);
		Utilitaires.sauvegarde(menu.getInscriptions());
	}
}
