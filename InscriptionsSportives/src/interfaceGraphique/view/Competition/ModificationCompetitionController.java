package interfaceGraphique.view.Competition;

import java.io.IOException;
import java.util.Optional;

import dialogueUtilisateur.GestionAlertes;
import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionCompetition;
import exceptions.ExceptionDateCompetition;
import inscriptions.Candidat;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.ModificationCompetition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private ComboBox<String> etat;
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
		
		etat.getItems().addAll("oui", "non");
	}
	
	public void setClass(ModificationCompetition stageModif, GestionCompetitionsController stageGestion)
	{
		this.stageGestion = stageGestion;
		this.stageModif = stageModif;
		
		textNom.setText(stageGestion.getCompetitionActive().getNom());
		datePicker.setValue(stageGestion.getCompetitionActive().getDateCloture());
		etat.setValue((String)returnOuiNon(stageGestion.getCompetitionActive().estEnEquipe()));
	
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
	
	public void actionValider()
	{
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
		if(!etat.getValue().equals((String)(returnOuiNon(stageGestion.getCompetitionActive().estEnEquipe()))))
		{
			if(!stageGestion.getCompetitionActive().getCandidats().isEmpty())
			{
				Alert alert = GestionAlertes.afficherAlerte("confirmation", "Modifier le champs 'en équipe' "
						+ "d'une compétition comportant déjà des candidats va entraîner la "
						+ "désinscription de ceux-ci. Continuer quand même ?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK)
				{
					
					try 
					{
					stageGestion.getCompetitionActive().removeAllCandidats();
					GestionDesErreurs.afficherMessage(stageGestion.getErreur(), "les participants ont "
									+ "bien été désinscris", "infos");
					stageGestion.getCompetitionActive().setEstEnEquipe((boolean)returnOuiNon(etat.getValue()));
					} 
					catch (Exception e) 
					{
						okModif = false;
						GestionDesErreurs.afficherMessage(messageErreur,e.toString(),"erreur");
					}
				} 
				else 
				{
					okModif = false;
				    etat.setValue((String)(returnOuiNon(stageGestion.getCompetitionActive().estEnEquipe())));
				}
			}
			else
			{
				try 
				{
					stageGestion.getCompetitionActive().setEstEnEquipe((boolean) returnOuiNon(etat.getValue()));
					
				} 
				catch (Exception e) 
				{
					okModif = false;
					GestionDesErreurs.afficherMessage(messageErreur,e.toString(),"erreur");
				}
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
