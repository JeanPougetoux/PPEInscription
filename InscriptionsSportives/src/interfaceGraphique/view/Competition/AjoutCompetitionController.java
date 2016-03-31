package interfaceGraphique.view.Competition;

import java.io.IOException;
import java.time.LocalDate;

import javax.management.RuntimeErrorException;

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
		messageErreur(null);
		enEquipe.getItems().addAll("oui", "non");
		enEquipe.setValue("oui");
	}	
	
	public void setStagesCompetition(AjoutCompetition stageAjout, GestionCompetitions stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
	}
	
	public String getStringNom() 
	{
		return nomCompetition.getText();
	}
	
	public LocalDate getLocalDateCloture() 
	{
		return dateCloture.getValue();
	}
	
	public boolean getEnEquipe(){
		if(enEquipe.getValue().equals("oui"))
			return true;
		else
			return false;
	}
	
	public void messageErreur(Object o){
    	if(o == null){
    		messageErreur.setVisible(false);
    	}
    	else{
    		messageErreur.setVisible(true);
    		messageErreur.setText(o.toString());
    	}
    }
	
	public void actionValider(){
		messageErreur(null);
		
		if(!getStringNom().isEmpty() && getLocalDateCloture() != null)
		{
			try 
			{
				stageGestion.getList().add(MonAppli.getInscriptions().createCompetition(
													this.getStringNom(), this.getLocalDateCloture(),
													this.getEnEquipe()));
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
				messageErreur(e1.toString());
			} 
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			messageErreur("Tous les champs doivent être renseignés");
		}
			
			
			
	}
	
	public void actionAnnuler(){
		stageAjout.close();
	}
}