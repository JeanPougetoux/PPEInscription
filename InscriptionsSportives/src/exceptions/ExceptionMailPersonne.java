package exceptions;

public class ExceptionMailPersonne extends ExceptionPrincipale
{
	private String mail;
	
	public ExceptionMailPersonne(String mail)
	{
		this.mail = mail;
	}
	
	@Override
	public String toString() 
	{
		return "L'adresse mail "+ mail + " est déjà utilisée par une autre personne ";
	}
	
}
