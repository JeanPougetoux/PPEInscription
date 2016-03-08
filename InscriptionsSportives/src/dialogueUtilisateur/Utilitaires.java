package dialogueUtilisateur;

import java.util.regex.Pattern;

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
             ch2+= sousChaines[i].substring(1) + " ";
             chaine2+= ch2;
          }  
        }
		return chaine2;
	}
}
