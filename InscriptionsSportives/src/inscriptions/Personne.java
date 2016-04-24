package inscriptions;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionMailPersonne;


/**
 * Représente une personne physique pouvant s'inscrire à une compétition.
 */

public class Personne extends Candidat 
{
	private static final long serialVersionUID = 4434646724271327254L;
	private String prenom, mail;
	private Set<Equipe> equipes;
	
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail,int id)
	{
		super(inscriptions, nom,id);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}

	/**
	 * Retourne le prénom de la personne.
	 * @return
	 */
	
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Modifie le prénom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
		if (super.getInscription().persistance  == super.getInscription().BDD)
			super.getInscription().pers.updatePrenomPersonne(this,prenom);
		this.prenom = prenom;
	}

	/**
	 * Retourne l'adresse électronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse électronique de la personne.
	 * @param mail
	 * @throws ExceptionMailPersonne 
	 */
	
	public void setMail(String mail) throws ExceptionMailPersonne
	{
		if (super.getInscription().persistance  == super.getInscription().BDD)
			super.getInscription().pers.updateMailPersonne(this,mail);
		this.mail = mail;
	}

	/**
	 * Retoure les équipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		if(super.getInscription().persistance == super.getInscription().BDD)
			super.getInscription().pers.insererCandidatDansEquipe(this.getId(),equipe.getId());
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)  
	{
		if(super.getInscription().persistance == super.getInscription().BDD)
			super.getInscription().pers.retirerPersonneEquipe(this.getId(),equipe.getId());
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()  
	{
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " " + Utilitaires.getMajuscule(this.getPrenom());
	}
}
