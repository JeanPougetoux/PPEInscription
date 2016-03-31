package exceptions;

public class ExceptionNomEquipe extends ExceptionPrincipale
{
	private String nomEquipe;
	
	public ExceptionNomEquipe(String nomEquipe) 
	{
		this.nomEquipe = nomEquipe;
	}
	
	@Override
	public String toString() 
	{
		return " Le nom "+ nomEquipe + " est déjà utilisé par une équipe ";
	}
	
}
