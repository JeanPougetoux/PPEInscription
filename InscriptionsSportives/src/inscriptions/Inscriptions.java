package inscriptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;
import donnees.persistance;
/**
 * Point d'entrée dans l'application, un seul objet de type Inscription
 * permet de gérer les compétitions, candidats (de type equipe ou personne)
 * ainsi que d'inscrire des candidats à  des compétition.
 */

public class Inscriptions implements Serializable
{
	public static final int SERIALIZATION = 0,
							BDD = 1;
	public static int persistance = BDD;
	public static persistance pers = null;
	
	private static final long serialVersionUID = -3095339436048473524L;
	private static final String FILE_NAME = "Inscriptions.srz";
	private static Inscriptions inscriptions;
	
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();

	private Inscriptions()
	{
	}
	
	/**
	 * Retourne les compétitions.
	 * @return
	 */
	
	public SortedSet<Competition> getCompetitions()
	{
		return Collections.unmodifiableSortedSet(competitions);
	}
	
	/**
	 * Retourne tous les candidats (personnes et équipes confondues).
	 * @return
	 */
	
	public SortedSet<Candidat> getCandidats()
	{
		return Collections.unmodifiableSortedSet(candidats);
	}

	/**
	 * Créée une compétition. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Competition}.
	 * @param nom
	 * @param dateCloture
	 * @param enEquipe
	 * @return
	 */
	
	public Competition createCompetition(String nom, 
			LocalDate dateCloture, boolean enEquipe)
	{
		Competition competition = new Competition(this, nom, dateCloture, enEquipe);
		if (persistance == BDD)
			pers.ajoutCompetition(nom,dateCloture,enEquipe);
		competitions.add(competition);
		return competition;
	}

	/**
	 * Créée une Candidat de type Personne. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Personne}.

	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Personne createPersonne(String nom, String prenom, String mail)
	{
		Personne personne = new Personne(this, nom, prenom, mail);
		if (persistance == BDD)
			pers.ajoutPersonne(nom,prenom,mail);
		candidats.add(personne);
		return personne;
	}
	
	/**
	 * Créée une Candidat de type équipe. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Equipe}.
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Equipe createEquipe(String nom)
	{
		Equipe equipe = new Equipe(this, nom);
		if (persistance == BDD)
			pers.ajoutEquipe(nom);
		candidats.add(equipe);
		return equipe;
	}
	
	void remove(Competition competition)
	{
		competitions.remove(competition);
		if (persistance == BDD)
			pers.retirerCompetition(competition.getNom());
	}
	
	void remove(Candidat candidat)
	{
		candidats.remove(candidat);
		if (persistance == BDD)
		{
			if (candidat instanceof Personne)
				pers.retirerPersonne(((Personne) candidat).getMail());
			else
				pers.retirerEquipe(candidat.getNom());
		}
	}
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà .
	 * @return l'unique objet de type {@link Inscriptions}.
	 */
	
	public static Inscriptions getInscriptions()
	{
		if (persistance == BDD)
		{
			pers = new persistance();
			try 
			{
				inscriptions = pers.getBase(new Inscriptions());
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			return  inscriptions;
		}
		else
		{
			if (inscriptions == null)
			{
				inscriptions = readObject();
				if (inscriptions == null)
					inscriptions = new Inscriptions();
			}
			return inscriptions;
		}
		
	}

	/**
	 * Retourne un object inscriptions vide. Ne modifie pas les compétitions
	 * et candidats déjà  existants.
	 */
	
	public Inscriptions reinitialiser()
	{
		inscriptions = new Inscriptions();
		return getInscriptions();
	}

	/**
	 * Efface toutes les modifications sur Inscriptions depuis la dernià¨re sauvegarde.
	 * Ne modifie pas les compétitions et candidats déjà  existants.
	 */
	
	public Inscriptions recharger()
	{
		inscriptions = null;
		return getInscriptions();
	}
	
	private static Inscriptions readObject()
	{
		ObjectInputStream ois = null;
		try
		{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ois = new ObjectInputStream(fis);
			return (Inscriptions)(ois.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
		finally
		{
				try
				{
					if (ois != null)
						ois.close();
				} 
				catch (IOException e){}
		}	
	}
	
	/**
	 * Sauvegarde le gestionnaire pour qu'il soit ouvert automatiquement 
	 * lors d'une exécution ultérieure du programme.
	 * @throws IOException 
	 */
	
	public void sauvegarder() throws IOException
	{
		ObjectOutputStream oos = null;
		try
		{
			FileOutputStream fis = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fis);
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			} 
			catch (IOException e){}
		}
	}
	
	@Override
	public String toString()
	{
		return "Candidats : " + getCandidats().toString()
			+ "\nCompetitions  " + getCompetitions().toString();
	}
	
	public static void main(String[] args)
	{
	
		/*Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", LocalDate.parse("2016-05-25"), false);
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"), 
				boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		flechettes.add(tony);
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		lesManouches.add(boris);
		lesManouches.add(tony);
		*/
		//Equipe truands = inscriptions.createEquipe("les truands");
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		
		System.out.println(inscriptions.getCandidats());
		System.out.println(inscriptions.getCompetitions());
		
		System.out.println("\n**************\n");
		System.out.println(inscriptions.getCandidats());
		System.out.println(inscriptions.getCompetitions());
		
	
		/*inscriptions.remove(truands);
		System.out.println("\n**************\n");
		
		System.out.println(inscriptions.getCandidats());
		System.out.println("\n**************\n");
		System.out.println(inscriptions.getCompetitions());
		/*try
		{
			inscriptions.sauvegarder();
		} 
		catch (IOException e)
		{
			System.out.println("Sauvegarde impossible." + e);
		}*/
	}
}
