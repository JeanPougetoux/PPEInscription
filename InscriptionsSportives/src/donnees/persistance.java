package donnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.mysql.jdbc.PreparedStatement;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;

public class persistance {

	private String url = "jdbc:mysql://localhost:3306/ppe_inscriptions";
	private String user = "root";
	private String passwd = "";
	private Connection conn = null;
	private Statement statement = null;
	ResultSet result = null;
    
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
			Candidat c = inscription.createPersonne(result.getString("candidat_nom"), result.getString("personne_prenom"), result.getString("personne_mail"));
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
			Equipe e = inscription.createEquipe(result.getString("candidat_nom"));
			
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		result = statement.executeQuery("select * from competition");
		while (result.next()) {
			LocalDate date = LocalDate.parse(result.getString("competition_date_cloture"), formatter);
			Competition c = inscription.createCompetition(result.getString("competition_nom"), date, result.getBoolean("competition_equipe"));
			
		}
		return inscription;
	}
	
	
	public void ajoutEquipe(String nom)
	{
		try 
		{
			String query = "call insertEquipe(?)";
			java.sql.PreparedStatement prepare = conn.prepareStatement(query);
			prepare.setString(1, nom);
			prepare.executeQuery();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void ajoutPersonne(String nom, String prenom, String mail) {
		try 
		{
			String query = "call insertPersonne(?,?,?)";
			java.sql.PreparedStatement prepare = conn.prepareStatement(query);
			prepare.setString(1, nom);
			prepare.setString(2, prenom);
			prepare.setString(3, mail);
			prepare.executeQuery();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
