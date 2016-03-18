package exceptions;

public class ExceptionPrincipale extends Exception {

	public ExceptionPrincipale() 
	{
		
	}
	
	@Override
	public String toString() 
	{
		return "\n Attention : L'application a décelé une erreur :";
	}
}
