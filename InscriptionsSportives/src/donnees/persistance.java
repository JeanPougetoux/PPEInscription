package donnees;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import exceptions.ExceptionMailPersonne;
import exceptions.ExceptionAjoutPersonneCompetition;
import exceptions.ExceptionCompetition;
import exceptions.ExceptionNomEquipe;
import exceptions.ExceptionPrincipale;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class persistance {
	
	public static final String URLLOCALE = "localhost:3306/ppe_inscriptions";
	public static final String URLSERVER = "mysql.m2l.local/jpougetoux";
	public static String URLFINALE = "";
	public static String USER = "";
	public static String PASS = "";
	private String url = "jdbc:mysql://" + URLFINALE;
	private String user = USER;
	private String passwd = PASS;
	private Connection conn = null;
	private Statement statement = null;
	private boolean initialisation = false;
	java.sql.PreparedStatement prepare = null;
	ResultSet result = null;
	String query;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	public boolean getInitialisation()
	{
		return initialisation;
	}
    /**
     * Cree une connexion à la base de données
     */
	public persistance() 
	{
		try 
		{
			conn = DriverManager.getConnection(url, user, passwd);
			statement =  conn.createStatement();
		} 
		catch (Exception e) 
		{
			System.out.println("problème de connexion à la base de données");
		}
	}
	
	/**
	 * Permet d'initialiser l'inscription avec les données de la Base de données
	 * @param inscription
	 * @return
	 * @throws SQLException
	 * @throws  
	 * @throws ExceptionNomEquipe 
	 * @throws ExceptionMailPersonne 
	 */
	public Inscriptions getBase(Inscriptions inscription) throws SQLException, ExceptionPrincipale
	{
		this.initialisation = true;
		inscription = getPersonnes(inscription);
		inscription = getEquipes(inscription);
		inscription = getCompetitions(inscription);
		inscription = getJoueursEquipes(inscription);
		inscription = getParticipantsCompetitions(inscription);
		this.initialisation = false;
		return inscription;
		
	}
	
	
	/**
	 * Nous allons récuperer toutes les compétitions et leurs participants respectifs, équipes ou personnes seules
	 * @param inscription
	 * @return
	 * @throws ExceptionAjoutPersonneCompetition 
	 */
	private Inscriptions getParticipantsCompetitions(Inscriptions inscription) throws ExceptionAjoutPersonneCompetition {
		
		try 
		{
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("call selectParticipantsCompetitions()");
			
			// On parcours toutes les lignes de participants_competition
			while(result.next())
			{
				
				for (Competition comp : inscription.getCompetitions()) 
				{
					
					if (comp.getNom().equals(result.getString("competition_nom")))
					{
						
						//Si la compétition se joue en équipe alors nous reconnaissons une équipe par son nom
						if (comp.estEnEquipe())
						{
							for (Candidat c : inscription.getCandidats()) 
							{
								if (c.getNom().equals(result.getString("candidat_nom")) && c instanceof Equipe)
									comp.add((Equipe) c);
									
							}	
						}
						// Si la compétition est solitaire, nous allons rechercher ses candidats via leur adresse E-mail
						else
						{
							Statement statement2 = conn.createStatement();
							ResultSet result2 = statement2.executeQuery("call selectMail('"+result.getString("candidat_nom")+"')");
							
							while(result2.next())
							{
								for (Candidat c : inscription.getCandidats()) 
								{
									if ( c instanceof Personne && ((Personne) c).getMail().equals(result2.getString("personne_mail")))
										comp.add((Personne) c);
									
								}
							}
							
						}
					}
						
				}
				
				
			}
		}
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		return inscription;
	}
	
	/**
	 * Nous mettons les candidats dans leurs équipes respectives
	 * @param inscription
	 * @return
	 */
	private Inscriptions getJoueursEquipes(Inscriptions inscription) {
		try 
		{
			result = statement.executeQuery("call selectJoueursEquipes() ");
			
			while(result.next())
			{
				for (Candidat c : inscription.getCandidats())
				{
					if(c.getNom().equals(result.getString("joueur"))&& c instanceof Personne)
					{
						for (Candidat equipe : inscription.getCandidats())
						{
							if(equipe.getNom().equals(result.getString("equipe")) && equipe instanceof Equipe)
								((Equipe) equipe).add((Personne) c);
									
						}
					}
				}
			}
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		return inscription;
	}

	/**
	 * Remplis le tableau de candidats avec les personnes simples.
	 * @param inscription
	 * @return
	 * @throws SQLException
	 * @throws ExceptionMailPersonne 
	 */
	private Inscriptions getPersonnes(Inscriptions inscription) throws SQLException, ExceptionMailPersonne
	
	{
		result = statement.executeQuery("call selectPersonnes()");
		while (result.next()) {
			
			inscription.createPersonne(result.getString("candidat_nom"), result.getString("personne_prenom"), result.getString("personne_mail"));
		}
		return inscription;
	}
	
	/**
	 * Remplis le tableau de candidats avec les équipes
	 * @param inscription
	 * @return
	 * @throws SQLException
	 * @throws ExceptionNomEquipe 
	 */
	private Inscriptions getEquipes(Inscriptions inscription) throws SQLException, ExceptionNomEquipe
	
	{
		result = statement.executeQuery("call selectEquipes()");
		while (result.next()) 
		{
			
			inscription.createEquipe(result.getString("candidat_nom"));
			
		}
		return inscription;
	}
	
	
	/**
	 * Remplis le tableau des compétitions
	 * @param inscription
	 * @return
	 * @throws SQLException
	 * @throws ExceptionNomCompetition 
	 */
	private Inscriptions getCompetitions(Inscriptions inscription) throws SQLException, ExceptionCompetition
	
	{
		result = statement.executeQuery("call selectCompetitions()");
		while (result.next()) {
			LocalDate date = LocalDate.parse(result.getString("competition_date_cloture"), formatter);
			inscription.createCompetition(result.getString("competition_nom"), date, result.getBoolean("competition_equipe"));
			
		}
		return inscription;
	}
	
	/**
	 * Ajoute une équipe dans la base de données
	 * @param nom
	 * @throws ExceptionNomEquipe 
	 * @throws SQLException 
	 */
	public void ajoutEquipe(String nom) throws ExceptionNomEquipe  
	{
		if (!getInitialisation())
		{
			query = "call insertEquipe(?)";
			
			try 
			{
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom.toLowerCase());
				prepare.executeQuery();
			}
			catch (SQLException e) 
			{
				throw new ExceptionNomEquipe(nom);
			}
			
		}
	}

	/**
	 * Ajoute une personne dans la base de données
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @throws ExceptionMailPersonne 
	 */
	public void ajoutPersonne(String nom, String prenom, String mail) throws ExceptionMailPersonne 
	{
		if(!getInitialisation())
		{
			try 
			{
				query = "call insertPersonne(?,?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom);
				prepare.setString(2, prenom);
				prepare.setString(3, mail);
				prepare.executeQuery();
			} catch (SQLException e) 
			{
				throw new ExceptionMailPersonne(mail);
			}
		}
		
		
	}

	/**
	 * Ajoute une compétiton dans la base de données
	 * @param nom
	 * @param dateCloture
	 * @param enEquipe
	 * @throws ExceptionNomCompetition 
	 */
	public void ajoutCompetition(String nom, LocalDate dateCloture, boolean enEquipe) throws ExceptionCompetition 
	{
		if(!getInitialisation())
		{
			try 
			{ 
				query = "call insertCompetition(?,?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom.toLowerCase());
				prepare.setDate(2,Date.valueOf(dateCloture));
				prepare.setBoolean(3,enEquipe);
				prepare.executeQuery();
			} 
			catch (SQLException e) 
			{
				throw new ExceptionCompetition(nom,"nom");
			}
		}
		
		
	}
	
	
	/**
	 * Permet de retirer une compétition de l'application, supprime alors tous les liens avec les participants
	 * @param nom
	 */
	public void retirerCompetition(String nom) 
	{
		try 
		{
			query = "call retirerCompetition('"+nom+"')";
			statement.executeQuery(query);
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	/**
	 * Supprime une personne de l'application, la retire alors de ses possibles équipes et compétitions
	 * @param mail
	 */
	public void retirerPersonne(String mail) {
		
		query = "call retirerPersonne('"+mail+"')";
		try 
		{
			statement.executeQuery(query);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
	}

	/**
	 * Supprime une équipe de l'application, retire automatiquement les joueurs de cette équipe et les compétitions associées
	 * @param nom
	 */
	public void retirerEquipe(String nom) 
	{
		
		query = "call retirerEquipe('"+nom+"')";
		try 
		{
			statement.executeQuery(query);
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Retire une personne d'une équipe, si l'équie est alors vide, celle ci est désinscrite des compétitions
	 * @param mail
	 * @param nom
	 * @throws ExceptionRetraitPersonneEquipe 
	 */
	public void retirerPersonneEquipe(String mail, String nom)  
	{
			query = "call retirerPersonneEquipe(?,?)";
			try 
			{
				prepare = conn.prepareStatement(query);
				prepare.setString(1,mail);
				prepare.setString(2,nom);
			
				prepare.executeQuery();
			} 
			catch (SQLException e) 
			{
				
			}
			
	}

	
	/**
	 * Retire un candidat (équipe ou personne) d'une compétitions
	 * @param candidat
	 * @param competition
	 */
	public void retirerCandidatCompetition(Candidat candidat, Competition competition) {
		
		try 
		{ 
			
			
			if(candidat instanceof Personne)
			{
				query = "call retirerPersonneCompetition(?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, ((Personne) candidat).getMail());
				prepare.setString(2,competition.getNom());
			}
			else
			{
				query = "call retirerEquipeCompetition(?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, candidat.getNom());
				prepare.setString(2,competition.getNom());
			}
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
	}

	/** 
	 * Nous ajoutons une compétitions au candidat
	 * @param candidat
	 */
	public void ajouterCompetitionCandidat(Candidat candidat, Competition competition) {
		int id_candidat = 0,id_competition = 0;
		try 
		{ 
			
			if(candidat instanceof Personne)
			{
				result = statement.executeQuery("call selectIdPersonneCompetition('"+((Personne) candidat).getMail()+"','"+competition.getNom()+"')");
			}
			else
			{
				result = statement.executeQuery("call selectIdEquipeCompetition('"+candidat.getNom()+"','"+competition.getNom()+"')");
			
			}
			while(result.next())
			{
				id_candidat = result.getInt("id_candidat");
				id_competition = result.getInt("id_competition");
			}
			Statement statement2 = conn.createStatement();
			statement2.executeQuery("call insertParticiper("+id_candidat+","+id_competition+")");
			
		} 
		
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
	}

	/**
	 * Permet l'insertion d'un candidat dans une équipe
	 * @param mail
	 * @param nom
	 */
	public void insererCandidatDansEquipe(String mail, String nom) {
		try 
		{ 
			
			query = "call insererCandidatDansEquipe(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, mail);
			prepare.setString(2,nom);
		
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		
	}
	

	/**
	 * Permet de modifier le nom d'une compétition
	 * @param comp
	 * @param nom
	 * @throws ExceptionCompetition 
	 */
	public void updateNomCompetition(Competition comp, String nom) throws ExceptionCompetition {
		try 
		{ 
			
			query = "call updateNomCompetition(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, nom);
			prepare.setString(2,comp.getNom());
		
		
			
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			throw new ExceptionCompetition(nom,"boolean");
		}
		
	}
	/**
	 * Permet de modifier la date d'une compétition
	 * @param comp
	 * @param nom
	 */
	public void updateDateCompetition(LocalDate dateCloture, String nom) {
		try 
		{ 
			
			query = "call updateDateCompetition(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setDate(1,Date.valueOf(dateCloture));
			prepare.setString(2,nom);
		
		
			
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet de modifier le booleen de compettiion
	 * @param comp
	 * @param nom
	 * @throws ExceptionCompetition 
	 */
	public void updateCompetitionBoolean(boolean bool, String nom) throws ExceptionCompetition {
		try 
		{ 
			
			query = "call updateCompetitionBoolean(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1,nom);
			prepare.setBoolean(2, bool);
		
		
			
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			throw new ExceptionCompetition(nom,"boolean");
		}
		
	}
	/**
	 * Permet de modifier le nom d'un candidat
	 * @param candidat
	 * @param nom
	 */
	public void updateNomCandidat(Candidat candidat, String nom) {
		try 
		{ 
			if (candidat instanceof Personne)
			{
				query = "call updateNomPersonne(?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom);
				prepare.setString(2,((Personne)candidat).getMail());
			}
			else
			{
				query = "call updateNomEquipe(?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom);
				prepare.setString(2,candidat.getNom());
			}
			prepare.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet de modifier le mail d'un candidat
	 * @param candidat
	 * @param nom
	 * @throws ExceptionMailPersonne 
	 */
	public void updateMailPersonne(Personne personne, String mail) throws ExceptionMailPersonne {
		try 
		{ 

			query = "call updateMailPersonne(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, personne.getMail());
			prepare.setString(2,mail);
			prepare.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			throw new ExceptionMailPersonne(mail);
		}
		
	}
	
	
	/**
	 * Permet de modifier le prenom d'un candidat
	 * @param candidat
	 * @param nom
	 * @throws ExceptionMailPersonne 
	 */
	public void updatePrenomPersonne(Personne personne, String prenom) 
	{
		try 
		{ 

			query = "call updatePrenomPersonne(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, personne.getMail());
			prepare.setString(2,prenom);
			prepare.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			System.out.println("erreur");
		}
		
	}
	
	/**
	 * Permet de savoir si la connexion a échoué ou résussi
	 * @param utilisateur
	 * @param password
	 * @return
	 */
	public static boolean estConnecte(String utilisateur,String password)
	{
		ResultSet resultat = null;
		String query = "call seConnecter(?,?)";
		
		try 
		{
			Connection conn = DriverManager.getConnection(URLFINALE, USER, PASS);
			java.sql.PreparedStatement prepare = conn.prepareStatement(query);
			prepare.setString(1, utilisateur);
			prepare.setString(2, encryptPassword(password));
			resultat = prepare.executeQuery();
			return (resultat.first());
		} 
		catch (SQLException e) 
		{
			System.out.println("problème de connexion");
		}
		return false;
	}

	/**
	 * Permet d'encrypter en SHA1 le mot de passe rentré par l'utilisateur
	 * @param password
	 * @return
	 */
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	/**
	 * Encrypte de byte à héxa
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
