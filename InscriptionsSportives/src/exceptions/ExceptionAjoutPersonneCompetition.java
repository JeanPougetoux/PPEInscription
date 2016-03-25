package exceptions;

public class ExceptionAjoutPersonneCompetition extends ExceptionPrincipale {

	private String erreur;
	public ExceptionAjoutPersonneCompetition(String erreur) {
		this.erreur = erreur;
	}
	
	public String toString()
	{
		String message ="";
		if(erreur == "equipe")
			message =  "Vous ne pouvez inscrire une personne dans une compétition réservée aux équipes";
		else if (erreur == "inscriptions")
			message =  "les inscriptions sont closes";
		return message;
	}
}
