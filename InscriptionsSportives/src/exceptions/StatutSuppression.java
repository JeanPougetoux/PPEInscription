package exceptions;

public class StatutSuppression
{

	private String message;
	public StatutSuppression(String message)
	{
		this.message = message;
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
