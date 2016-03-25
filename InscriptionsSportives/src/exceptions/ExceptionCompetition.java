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
	
	@Override
	public String toString() 
	{
		if(erreur == "nom")
			return super.toString() + "\n * Le nom "+ nom + " est déjà utilisé par une autre compétition";
		else
			return "La compétition"+nom+" possède déjà des participants, elle ne peut donc plus être modifié sur sa catégorie.";
	}
}
