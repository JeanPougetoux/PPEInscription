package dialogueUtilisateur.Competitions.Gestion;

import java.time.LocalDate;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import inscriptions.Inscriptions;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;

public class ActionAjoutCompetition implements Action{
	
	private Inscriptions inscriptions;
	
	/**
	 * Construit l'action liée au choix de 'ajouter une compétition' dans le menu compétitions.
	 */
	public ActionAjoutCompetition(MenuGestionCompetitions menu){
		this.inscriptions = menu.getInscriptions();
	}
	
	public void optionSelectionnee(){
		int mod = 0;
		String nomCompetition = null;
		Object dateCloture = null, estEnEquipe = null;
		do{
			mod++;
			switch(mod){
			case 1:
				nomCompetition = EntreesSorties.getString("\nSaisir le nom de la compétition.'q' pour quitter.");
				mod = Utilitaires.getMod(mod, nomCompetition, false);
				break;
			case 2:
				dateCloture = SaisiesConsole.saisieDateCompetition("\nSaisir la date de clôture de la "
						+ "compétition (au format yyyy-MM-dd)\n'q' pour quitter, 'r' pour revenir.", null);
				mod = Utilitaires.getMod(mod, dateCloture, false);
				break;
			case 3:
				estEnEquipe = SaisiesConsole.saisieEquipeCompetition("\nLa compétition est-elle pour les équipes "
						+ "ou les personnes seules ?\n(tapez 'e' pour équipes ou 'p' pour personnes)\n"
						+ "'q' pour quitter, 'r' pour revenir.", false);
				mod = Utilitaires.getMod(mod, estEnEquipe, false);
				break;
			}
		}while(mod < 4);
		if(mod < 5){
			inscriptions.createCompetition(nomCompetition, (LocalDate)dateCloture, (boolean)estEnEquipe);
			System.out.println("\nLa compétition est bien ajoutée.");
			Utilitaires.sauvegarde(inscriptions);
		}
	}
}
