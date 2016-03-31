package inscriptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
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
	private Set<Personne> membres = new TreeSet<>();
	
	Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	
	public Set<Personne> getMembres()
	{
		return Collections.unmodifiableSet(membres);
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
	
	public void remove(Personne membre) 
	{		
		membre.remove(this);
		membres.remove(membre);
		if(this.getMembres().isEmpty())
		{
			for (Competition c : this.getCompetitions())
			{
				c.remove(this);
			}
			
		}
		
	}
	
	@Override
	public void delete() 
	{
		super.delete();
	}
	
	@Override
	public String toString()
	{
		return "Equipe '" + super.toString() + "'";
	}
}
