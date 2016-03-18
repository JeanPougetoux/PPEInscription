package inscriptions;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import exceptions.ExceptionRetraitPersonneEquipe;

/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant 
 * s'inscrire à une compétition.
 * 
 */

public class Equipe extends Candidat
{
	private static final long serialVersionUID = 4147819927233466035L;
	private SortedSet<Personne> membres = new TreeSet<>();
	
	Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'équipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
			
		return membres.add(membre);
	}

	
	/**
	 * Supprime une personne de l'équipe.
	 * Si l'équipe devient alors vide, elle est désinscrite des compétitions  
	 * @param membre
	 * @return
	 * @throws ExceptionRetraitPersonneEquipe 
	 */
	
	public StatutSuppression remove(Personne membre) 
	{		
		membre.remove(this);
		membres.remove(membre);
		if(this.getMembres().isEmpty())
		{
			for (Competition c : this.getCompetitions())
			{
				c.remove(this);
			}
			return new StatutSuppression(true,"Attention, votre équipe étant vide, elle a été automatiquement"
					+ "désinscrite de ses compétitions");
		}
		return new StatutSuppression(true,"personne "+ membre.getNom() +" bien supprimée");
	}
	
	@Override
	public void delete() throws ExceptionRetraitPersonneEquipe
	{
		super.delete();
	}
	
	@Override
	public String toString()
	{
		return "Equipe '" + super.toString() + "'";
	}
}
