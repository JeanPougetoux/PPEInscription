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
		//inscription = getParticipantsCompetitions(inscription);
		
		return inscription;
		
	}
	
	/**
	 * Remplis le tableau de candidats avec les personnes simples
	 * @param inscription
	 * @return
	 * @throws SQLException
	 */
	public Inscriptions getPersonnes(Inscriptions inscription) throws SQLException
	
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
	public Inscriptions getEquipes(Inscriptions inscription) throws SQLException
	
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
	public Inscriptions getCompetitions(Inscriptions inscription) throws SQLException
	
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

	public void retirerEquipe(String nom) {
		
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
	
	
	
	
}