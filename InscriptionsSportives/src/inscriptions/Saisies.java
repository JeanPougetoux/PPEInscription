package inscriptions;

import java.time.LocalDate;

import utilitaires.EntreesSorties;

public class Saisies {

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
			String reponse = EntreesSorties.getString("La compétition est-elle pour les équipes ou les personnes seules ?\n" 
														+ " (tapez 'e' pour équipes ou 'p' pour personnes)");
			if(reponse == Character.toString('e')){
				return true;
			}
			else if(reponse == Character.toString('p')){
				return false;
			}
			else{
				System.out.println("Veuillez saisir 'e' ou 'p'.");
			}	
		}
		while(true);
	}
}
