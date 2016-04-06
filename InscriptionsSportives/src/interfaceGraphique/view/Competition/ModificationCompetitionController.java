package interfaceGraphique.view.Competition;

import java.io.IOException;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionCompetition;
import exceptions.ExceptionDateCompetition;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.ModificationCompetition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificationCompetitionController {

	@FXML
	private Label messageErreur;
	@FXML
	private TextField textNom;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<String> comboEquipe;
	@FXML
	private Button valider;
	@FXML
	private Button annuler;
	private ModificationCompetition stageModif;
	private GestionCompetitionsController stageGestion;
	
	public ModificationCompetitionController(){
		
	}
	
	@FXML
	private void initialize(){
		messageErreur.setVisible(false);
		comboEquipe.getItems().addAll("oui", "non");
	}
	
	public void setClass(ModificationCompetition stageModif, GestionCompetitionsController stageGestion){
		this.stageGestion = stageGestion;
		this.stageModif = stageModif;
		textNom.setText(stageGestion.getCompetitionActive().getNom());
		datePicker.setValue(stageGestion.getCompetitionActive().getDateCloture());
		comboEquipe.setValue((String) returnOuiNon(stageGestion.getCompetitionActive().estEnEquipe()));
	}
	
	public Object returnOuiNon(Object b){
		if(b instanceof Boolean){
			if((boolean) b)
				return "oui";
			else
				return "non";
		}
		else if(b instanceof String){
			if(b.equals("oui")){
				return true;
			}
			else{
				return false;
			}
		}
		return null;
	}
	
	public void actionValider(){
		boolean okModif = true;
		if(!textNom.getText().isEmpty())
		{
			if(!textNom.getText().equals(stageGestion.getCompetitionActive().getNom()))
			{
				
				try 
				{
					stageGestion.getCompetitionActive().setNom(textNom.getText());
				} 
				catch (ExceptionCompetition e)
				{
					GestionDesErreurs.afficherMessage(messageErreur, e.toString(), "erreur");
					okModif = false;
				}
			
			}
		}
		else
		{
			okModif = false;
			GestionDesErreurs.afficherMessage(messageErreur,"vous devez saisir un nom, ancien nom : "+stageGestion.getCompetitionActive().getNom(), "erreur");
		}
			
		
		if(!datePicker.getValue().equals(stageGestion.getCompetitionActive().getDateCloture())){
			
				try {
					stageGestion.getCompetitionActive().setDateCloture(datePicker.getValue());
				} catch (ExceptionDateCompetition e) {
					okModif = false;
					GestionDesErreurs.afficherMessage(messageErreur, e.toString(), "erreur");
				}
			
		}
		if(!comboEquipe.getValue().equals((String)(returnOuiNon(stageGestion.getCompetitionActive().estEnEquipe())))){
			try {
				stageGestion.getCompetitionActive().setEstEnEquipe((boolean) returnOuiNon(comboEquipe.getValue()));
			} catch (Exception e) {
				okModif = false;
				GestionDesErreurs.afficherMessage(messageErreur,e.toString(),"erreur");
			}
		}
		if(okModif){
			try {
				MonAppli.getInscriptions().sauvegarder();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stageGestion.actualiserList();
			stageModif.close();
		}
	}
	
	public void actionAnnuler(){
		stageModif.close();
	}
}
