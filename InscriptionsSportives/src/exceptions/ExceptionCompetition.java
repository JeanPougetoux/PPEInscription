package exceptions;

public class ExceptionCompetition extends ExceptionPrincipale
{
	private String erreur;
	private String nom;
	public ExceptionCompetition(String nom,String typeErreur)
	{
		this.erreur = typeErreur;
		this.nom = nom;
	}
	public ExceptionCompetition(String typeErreur)
	{
		this.erreur = typeErreur;
	}
	
	@Override
	public String toString() 
	{
		if(erreur == "nom")
			return  " Le nom "+ nom + " est déjà utilisé par une autre compétition";
		else if(erreur == "boolean")
			return "On ne peut modifier cet aspect (participants deja inscris)";
		else
			return "Date déjà passée";
	}
}
