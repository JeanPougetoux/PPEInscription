package donnees;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mysql.jdbc.PreparedStatement;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class persistance {

	private String url = "jdbc:mysql://localhost:3306/ppe_inscriptions";
	private String user = "root";
	private String passwd = "";
	private Connection conn = null;
	private Statement statement = null;
	java.sql.PreparedStatement prepare = null;
	ResultSet result = null;
	String query;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	
	
    
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
	 */
	public Inscriptions getBase(Inscriptions inscription) throws SQLException
	{
		inscription = getPersonnes(inscription);
		inscription = getEquipes(inscription);
		inscription = getCompetitions(inscription);
		inscription = getJoueursEquipes(inscription);
		inscription = getParticipantsCompetitions(inscription);
		
		return inscription;
		
	}
	
	
	/**
	 * Nous allons récuperer toutes les compétitions et leurs participants respectifs, équipes ou personnes seules
	 * @param inscription
	 * @return
	 */
	private Inscriptions getParticipantsCompetitions(Inscriptions inscription) {
		
		try 
		{
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("select candidat_nom,competition_nom from participants_competitions");
			
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
							ResultSet result2 = statement2.executeQuery("SELECT personne_mail FROM personnes WHERE candidat_nom = '"+result.getString("candidat_nom")+"'");
							
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
			e.printStackTrace();
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
			result = statement.executeQuery("SELECT joueur, equipe FROM joueurs_equipes ");
			
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
			e.printStackTrace();
		}
		return inscription;
	}

	/**
	 * Remplis le tableau de candidats avec les personnes simples
	 * @param inscription
	 * @return
	 * @throws SQLException
	 */
	private Inscriptions getPersonnes(Inscriptions inscription) throws SQLException
	
	{
		result = statement.executeQuery("select * from personnes");
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
	 */
	private Inscriptions getEquipes(Inscriptions inscription) throws SQLException
	
	{
		result = statement.executeQuery("select * from equipes");
		while (result.next()) {
			
			inscription.createEquipe(result.getString("candidat_nom"));
			
		}
		return inscription;
	}
	
	
	/**
	 * Remplis le tableau des compétitions
	 * @param inscription
	 * @return
	 * @throws SQLException
	 */
	private Inscriptions getCompetitions(Inscriptions inscription) throws SQLException
	
	{
		result = statement.executeQuery("select * from competition");
		while (result.next()) {
			LocalDate date = LocalDate.parse(result.getString("competition_date_cloture"), formatter);
			inscription.createCompetition(result.getString("competition_nom"), date, result.getBoolean("competition_equipe"));
			
		}
		return inscription;
	}
	
	/**
	 * Ajoute une équipe dans la base de données
	 * @param nom
	 */
	public void ajoutEquipe(String nom)
	{
		try 
		{
			query = "call insertEquipe(?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, nom);
			prepare.executeQuery();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute une personne dans la base de données
	 * @param nom
	 * @param prenom
	 * @param mail
	 */
	public void ajoutPersonne(String nom, String prenom, String mail) 
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
			e.printStackTrace();
		}
		
	}

	/**
	 * Ajoute une compétiton dans la base de données
	 * @param nom
	 * @param dateCloture
	 * @param enEquipe
	 */
	public void ajoutCompetition(String nom, LocalDate dateCloture, boolean enEquipe) 
	{
		try 
		{ 
			query = "call insertCompetition(?,?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1, nom);
			prepare.setDate(2,Date.valueOf(dateCloture));
			prepare.setBoolean(3,enEquipe);
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Retire une personne d'une équipe, si l'équie est alors vide, celle ci est désinscrite des compétitions
	 * @param mail
	 * @param nom
	 */
	public void retirerPersonneEquipe(String mail, String nom)
	{
		
		try 
		{

			query = "call retirerPersonneEquipe(?,?)";
			prepare = conn.prepareStatement(query);
			prepare.setString(1,mail);
			prepare.setString(2,nom);
		
			prepare.executeQuery();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
				result = statement.executeQuery("SELECT id_personne as id_candidat,id_competition FROM personne p,competition c"
				+ " WHERE p.personne_mail = '"+((Personne) candidat).getMail()+"'"
				+ " AND c.competition_nom = '"+competition.getNom()+"'");
				
			}
			else
			{
				result = statement.executeQuery("SELECT id_candidat,id_competition FROM equipes,competition"
				+ " WHERE equipes.candidat_nom = '"+candidat.getNom()+"'"
				+ " AND competition.competition_nom = '"+competition.getNom()+"'");
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

	public void updateNomCompetition(Competition comp, String nom) {
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
			//e.printStackTrace();
		}
		
	}

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
				/*query = "call updateNomEquipe(?,?)";
				prepare = conn.prepareStatement(query);
				prepare.setString(1, nom);
				prepare.setString(2,candidat.get());*/
			}
			prepare.executeQuery();
			
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		}
		
	}
}
