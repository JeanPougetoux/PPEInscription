package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionNomEquipe;
import exceptions.ExceptionRetraitPersonneEquipe;

/**
 * Candidat à  un événement sportif, soit une personne physique, soit une équipe.
 *
 */

public abstract class Candidat implements Comparable<Candidat>, Serializable
{
	private static final long serialVersionUID = -6035399822298694746L;
	private Inscriptions inscriptions;
	private String nom;
	private int id;
	private Set<Competition> competitions;
	
	Candidat(Inscriptions inscriptions, String nom,int id)
	{
		this.inscriptions = inscriptions;
		this.nom = nom;
		competitions = new TreeSet<>();
		this.setId(id);
	}
	
	public Inscriptions getInscription()
	{
		return this.inscriptions;
	}

	/**
	 * Retourne le nom du candidat.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Modifie le nom du candidat.
	 * @param nom
	 * @throws ExceptionNomEquipe 
	 */
	
	public void setNom(String nom) throws ExceptionNomEquipe
	{
		if (inscriptions.persistance == inscriptions.BDD)
			inscriptions.pers.updateNomCandidat(this,nom);
		this.nom = nom;
	}

	/**
	 * Retourne toutes les compétitions auxquelles ce candidat est inscrit.s
	 * @return
	 */

	public Set<Competition> getCompetitions()
	{
		return Collections.unmodifiableSet(competitions);
	}
	
	boolean add(Competition competition)
	{	
		if(inscriptions.persistance == inscriptions.BDD)
			inscriptions.pers.ajouterCompetitionCandidat(this, competition);
		return competitions.add(competition);
		
	}

	boolean remove(Competition competition)
	{
		if (inscriptions.persistance == inscriptions.BDD){
			 inscriptions.pers.retirerCandidatCompetition(this,competition);
		}
		return competitions.remove(competition);
	}

	/**
	 * Supprime un candidat de l'application.
	 * @throws ExceptionRetraitPersonneEquipe 
	 */
	
	public void delete() 
	{
		
		for (Competition c : competitions)
			c.remove(this);
		inscriptions.remove(this);
		if(this.inscriptions.persistance == this.inscriptions.BDD && this instanceof Personne)
			this.inscriptions.pers.retirerPersonne(getId());
		else if (this.inscriptions.persistance == this.inscriptions.BDD && this instanceof Equipe)
			this.inscriptions.pers.retirerEquipe(getId());
		
		
			
			
			
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		Candidat c = this;
		if(c instanceof Equipe)
			return c.getNom().compareTo(o.getNom());
		else if(c instanceof Personne && o instanceof Personne)
			return ((Personne)c).getMail().compareTo(((Personne)o).getMail());
		return 0;
				
	}
	
	@Override
	public String toString()
	{
		return Utilitaires.getMajuscule(getNom());
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
