package dialogueUtilisateur;

import java.time.LocalDate;

import utilitaires.EntreesSorties;

public class SaisiesConsole {

	public static Object saisieDateCompetition(String texte, LocalDate compet){
		do
		{
			String message = EntreesSorties.getString(texte);
			if(compet != null)
				if(message.isEmpty())
					return message;
				else if(message.equals("q") || message.equals("r")){
					return message;
				}
			
			try
			{
				LocalDate dateCloture = LocalDate.parse(message);
				if(compet != null){
					if(dateCloture.isAfter(compet))
						return dateCloture;
					else
						System.out.println("Vous ne pouvez pas avancer la date de cloture.");
				}
				else{
					return dateCloture;
				}
			}
			catch(Exception e)
			{
				System.out.println("Veuillez saisir votre date au format yyyy-MM-dd");
			}
		}
		while(true);
	}
	
	public static Object saisieEquipeCompetition(String message, boolean modif){
		do
		{
			String choix = (EntreesSorties.getString(message));
			if(modif && choix.isEmpty()){
				return null;
			}
			else if(choix.equals("r") || choix.equals("q")){
				return choix;
			}
			else if(choix.equals("e")){
				return true;
			}
			else if(choix.equals("p")){
				return false;
			}
			else{
				System.out.println("Veuillez saisir 'e' ou 'p'.");
			}	
		}
		while(true);
	}
	
	public static char saisieSuppression(String texte){
		do
		{
			char reponse = EntreesSorties.getString("Etes-vous sûr de vouloir supprimer " + texte + " ?\n"
													+ "Saisir 'o' ou 'n' : ").charAt(0);
			if(reponse == 'o' || reponse == 'n'){
				return reponse;
			}
		}
		while(true);
	}
}
