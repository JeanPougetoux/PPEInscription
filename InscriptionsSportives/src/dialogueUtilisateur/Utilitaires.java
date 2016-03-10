package dialogueUtilisateur;

import java.io.IOException;
import java.util.regex.Pattern;

import inscriptions.Inscriptions;

public class Utilitaires {
	
	/**
	 * Retourne la chaîne avec une majuscule au début de chaque mot.
	 * @param texte
	 * @return une chaine
	 */
	public static String getMajuscule(String texte){
		String chaine2 = "";
		Pattern patern = Pattern.compile(" ");    
        String[] sousChaines = patern.split(texte);
        for(int i = 0; i < sousChaines.length; i++)
        {
          if(!sousChaines[i].equals(""))
          {
             String ch2 = sousChaines[i].substring(0, 1);
             ch2 = ch2.toUpperCase();
             ch2+= sousChaines[i].substring(1);
             if(i != sousChaines.length - 1)
            	 ch2 += " ";
             chaine2+= ch2;
          }  
        }
		return chaine2;
	}
	
	/**
	 * Permet de tenter de sauvegarder l'inscription sinon renvoie une erreur
	 * @param inscription
	 */
	public static void sauvegarde(Inscriptions inscriptions){
		try {
			inscriptions.sauvegarder();
		} catch (IOException e) {
			System.out.println("Sauvegarde impossible");
		}
	}
	
	/**
	 * Retourne le mod selon le choix effectué
	 * @param mod
	 */
	public static int getMod(int mod, Object content, boolean modif){
		if(content.toString().equals("q"))
			return 5;
		else if(mod > 1 && content.toString().equals("r"))
			return mod - 2;
		else if(content.toString().isEmpty() && !modif){
			System.out.println("Le champ est vide.");
			return mod - 1;
		}
		return mod;
	}
	
	/**
	 * Retourne oui si true non si false
	 * @param reponse
	 * @return oui ou non
	 */
	public static String getOuiNon(boolean reponse){
		if(reponse)
			return "oui";
		else if(!reponse)
			return "non";
		return null;
	}
}
