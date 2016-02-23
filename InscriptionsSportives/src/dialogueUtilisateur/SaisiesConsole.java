package dialogueUtilisateur;

import java.time.LocalDate;

import utilitaires.EntreesSorties;

public class SaisiesConsole {

	public static LocalDate saisieDateCompetition(){
		do
		{
			String message = EntreesSorties.getString("Saisir la date de clôture de la compétition (au format"
														+ " yyyy-MM-dd)");
			try
			{
				LocalDate dateCloture = LocalDate.parse(message);
				return dateCloture;
			}
			catch(Exception e)
			{
				System.out.println("Veuillez saisir votre date au format yyyy-MM-dd");
			}
		}
		while(true);
	}
	
	public static boolean saisieEquipeCompetition(){
		do
		{
			char choix = (EntreesSorties.getString("La compétition est-elle pour les équipes ou les personnes seules ?\n" 
														+ "(tapez 'e' pour équipes ou 'p' pour personnes)")).charAt(0);
			if(choix == 'e'){
				return true;
			}
			else if(choix == 'p'){
				return false;
			}
			else{
				System.out.println("Veuillez saisir 'e' ou 'p'.");
			}	
		}
		while(true);
	}
	
	public static char saisieSuppressionCompetition(){
		do
		{
			char reponse = EntreesSorties.getString("Etes-vous sûr de vouloir supprimer la compétition ?\n"
													+ "Saisir 'o' ou 'n'.").charAt(0);
			if(reponse == 'o' || reponse == 'n'){
				return reponse;
			}
		}
		while(true);
	}
}
