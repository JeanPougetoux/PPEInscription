package dialogueUtilisateur;

import java.time.LocalDate;

import utilitaires.EntreesSorties;

public class SaisiesConsole {

	/**
	 * Permet de saisir la date d'une compétition.
	 * Retourne null si on est en mode modification et que l'input est vide.
	 * Retourne q ou r si ils sont tapés.
	 * Sinon vérifie que l'on a bien saisie la date au format, 
	 * sinon message d'erreur.
	 * @param texte
	 * @param compet
	 * @return un message ou bien la date de cloture.
	 */
	public static Object saisieDateCompetition(String texte, LocalDate compet){
		do
		{
			String message = EntreesSorties.getString(texte);
			if(compet != null && message.isEmpty())
				return message;
			else if(message.equals("q") || message.equals("r"))
				return message;
			
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
	
	/**
	 * Permet de saisie p ou e pour personne ou equipe.
	 * retourne null si on est en modification et que le champs est vide.
	 * retourne r ou q si ils sont tapés.
	 * Sinon retourn true pour e et false pour p.
	 * @param message
	 * @param modif
	 * @return
	 */
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
	
	/**
	 * Permet de saisir uniquement o ou n.
	 * @param texte
	 * @return o ou n.
	 */
	public static char saisieSuppression(String texte){
		do
		{
			char reponse = EntreesSorties.getString("Etes-vous sûr de vouloir supprimer " 
													+ texte + " ?\n"
													+ "Saisir 'o' ou 'n' : ").charAt(0);
			if(reponse == 'o' || reponse == 'n'){
				return reponse;
			}
		}
		while(true);
	}
	
	/**
	 * Retourne vrai si la chaîne ne contient pas de chiffre, sinon false
	 * @return boolean
	 */
	public static boolean verifChiffreChaine(String chaine){
		if (chaine.matches("[a-zA-Z\\p{Blank}]+"))
			return true;
		else
			System.out.println("Veuillez ne saisir que des caractères.");
			return false;
	}
}
