package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
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
	private Set<Competition> competitions;
	
	Candidat(Inscriptions inscriptions, String nom)
	{
		this.inscriptions = inscriptions;
		this.nom = nom;
		competitions = new TreeSet<>();
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
	 */
	
	public void setNom(String nom)
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
	
	public void delete() throws ExceptionRetraitPersonneEquipe
	{
		for (Competition c : competitions)
			c.remove(this);
		inscriptions.remove(this);
		if(this.inscriptions.persistance == this.inscriptions.BDD && this instanceof Personne)
			this.inscriptions.pers.retirerPersonne(((Personne) this).getMail());
		else if (this.inscriptions.persistance == this.inscriptions.BDD && this instanceof Equipe)
			this.inscriptions.pers.retirerEquipe(((Equipe) this).getNom());
			
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return Utilitaires.getMajuscule(getNom());
	}
}
