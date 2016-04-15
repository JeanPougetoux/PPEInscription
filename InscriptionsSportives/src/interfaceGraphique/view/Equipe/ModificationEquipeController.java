package interfaceGraphique.view.Equipe;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionNomEquipe;
import inscriptions.Competition;
import inscriptions.Equipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.ModificationEquipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificationEquipeController {


	@FXML
    private TextField nomEquipe;
	@FXML
	private Button valider;
	@FXML
	private Button annuler;
	@FXML
	private Label messageErreur;
	private GestionEquipeController stageGestion;
	private ModificationEquipe stageModif;
	
	public ModificationEquipeController(){
		
	}
	
	@FXML
	private void initialize(){
		messageErreur.setVisible(false);
	}
	
	public void setStage(GestionEquipeController stageGestion, ModificationEquipe stageModif){
		this.stageGestion = stageGestion;
		this.stageModif = stageModif;
		nomEquipe.setText(stageGestion.getEquipeActive().getNom());
	}
	
	public void actionValider(){
		if(!nomEquipe.getText().isEmpty())
		{
			if(!nomEquipe.equals(stageGestion.getEquipeActive().getNom()))
			{
				
				try 
				{
					stageGestion.getEquipeActive().setNom(nomEquipe.getText().toLowerCase());
					try
					{
						MonAppli.getInscriptions().sauvegarder();
						stageGestion.actualise();
						stageModif.close();
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				} 
				catch (ExceptionNomEquipe e1) 
				{
					GestionDesErreurs.afficherMessage(messageErreur,e1.toString(),"erreur");
				}
				
			}
		}
		else
			GestionDesErreurs.afficherMessage(messageErreur,"Champs vide interdis, ancien nom : " + stageGestion.getEquipeActive().getNom(),"erreur");
		
	}
	
	public void actionAnnuler(){
		stageModif.close();
	}
}
