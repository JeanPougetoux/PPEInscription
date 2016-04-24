package interfaceGraphique.view.Competition;

import java.io.IOException;
import java.time.LocalDate;

import javax.management.RuntimeErrorException;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionCompetition;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.AjoutCompetition;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller de la vue AjoutCompetition.fxml
 * qui est appellé par la classe AjoutCompetition.
 * @author Jean
 *
 */
public class AjoutCompetitionController {

	@FXML
    private TextField nomCompetition;
	@FXML
	private DatePicker dateCloture;
	@FXML
	private ComboBox<String> enEquipe;
	@FXML 
	private Button valider;
	@FXML
	private Button annuler;
	@FXML
	private Label messageErreur;
	private AjoutCompetition stageAjout;
	private GestionCompetitions stageGestion;
	
	public AjoutCompetitionController(){
		
	}
	
	@FXML
	private void initialize(){
		messageErreur.setVisible(false);
		enEquipe.getItems().addAll("oui", "non");
		enEquipe.setValue("oui");
	}	
	
	/**
	 * Initialise les variables correspondant aux fenêtre AjoutCompetition et 
	 * GestionCompetitions.
	 * @param stageAjout
	 * @param stageGestion
	 */
	public void setStagesCompetition(AjoutCompetition stageAjout, GestionCompetitions stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
	}
	
	/**
	 * @return le nom de la compétition.
	 */
	public String getStringNom() 
	{
		return nomCompetition.getText();
	}
	
	/**
	 * @return la date de clôture de la compétition.
	 */
	public LocalDate getLocalDateCloture() 
	{
		return dateCloture.getValue();
	}
	
	/**
	 * @return le statut de la compétition (en équipe ou non).
	 */
	public boolean getEnEquipe(){
		if(enEquipe.getValue().equals("oui"))
			return true;
		else
			return false;
	}
	
	/**
	 * Correspond à l'action du bouton valider.
	 * Vérifie que les champs soient bons et créer la compétition.
	 */
	public void actionValider(){
		
		if(!getStringNom().isEmpty() && getLocalDateCloture() != null)
		{
			try 
			{
				stageGestion.getList().add(MonAppli.getInscriptions().createCompetition(
													this.getStringNom(), this.getLocalDateCloture(),
													this.getEnEquipe(),
													MonAppli.getInscriptions().pers.getLastInsertCompetition()));
				try 
				{
					MonAppli.getInscriptions().sauvegarder();
					stageAjout.close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			catch (ExceptionCompetition e1)
			{
				GestionDesErreurs.afficherMessage(messageErreur, e1.toString(), "erreur");
			} 
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			GestionDesErreurs.afficherMessage(messageErreur,"Tous les champs doivent être renseignés", "erreur");
		}
			
			
			
	}
	
	/**
	 * Correspond à l'action du bouton annuler.
	 * Ferme la fenêtre.
	 */
	public void actionAnnuler(){
		stageAjout.close();
	}
}