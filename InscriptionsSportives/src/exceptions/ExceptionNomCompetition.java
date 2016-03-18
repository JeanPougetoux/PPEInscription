package exceptions;

public class ExceptionNomCompetition extends ExceptionPrincipale
{

	private String nom;
	public ExceptionNomCompetition(String nom)
	{
		this.nom = nom;
	}
	
	@Override
	public String toString() 
	{
		return super.toString() + "\n * Le nom "+ nom+ " est déjà utilisé par une autre compétition";
	}
}
