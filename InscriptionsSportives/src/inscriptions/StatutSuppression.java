package inscriptions;

public class StatutSuppression 
{
	private boolean erreur;
	private String message;
	
	public StatutSuppression(boolean erreur, String message) 
	{
		this.erreur = erreur;
		this.message = message;
	}
	
	public boolean informationRelevee()
	{
		return erreur;
	}
	
	public String getMessage()
	{
		return message;
	}
}
