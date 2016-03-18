package inscriptions;

public class StatutSuppression 
{
	private boolean etat;
	private String message;
	
	public StatutSuppression(boolean etat, String message) 
	{
		this.etat = etat;
		this.message = message;
	}
	
	public boolean suppEffectuee()
	{
		return etat;
	}
	
	public String getMessage()
	{
		return message;
	}
}
