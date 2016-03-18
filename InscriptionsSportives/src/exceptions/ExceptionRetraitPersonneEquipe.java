package exceptions;

public class ExceptionRetraitPersonneEquipe extends ExceptionPrincipale
{

	public ExceptionRetraitPersonneEquipe() 
	{
		
	}
	
	@Override
	public String toString() 
	{
		return super.toString() + "\n Votre equipe ne comportant plus de joueurs, elle a ete desinscrite des competitions";
	}
}
