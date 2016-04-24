package interfaceGraphique.view.Equipe;

import java.io.IOException;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionNomEquipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AjoutEquipeController {
	
	@FXML
	private TextField newName;
	@FXML
	private Label messageErreur;
	private AjoutEquipe stageAjout;
	private GestionEquipe stageGestion;
	
	public AjoutEquipeController(){
		
	}
	
	@FXML
	private void initialize(){
		messageErreur.setVisible(false);
	}
	
	public void setStage(AjoutEquipe stageAjout, GestionEquipe stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
	}
	
	public void actionClose(){
		stageAjout.close();
	}
	
	public void actionAjout(){
		if(!newName.getText().isEmpty())
		{
			try 
			{
				stageGestion.getList().add(MonAppli.getInscriptions().createEquipe(newName.getText().toLowerCase(),
						MonAppli.getInscriptions().pers.getLastInsertCandidat()));
				stageAjout.close();
			} 
			catch (ExceptionNomEquipe e1) 
			{
				GestionDesErreurs.afficherMessage(messageErreur,e1.toString(),"erreur");
			}
		
		try
		{
			MonAppli.getInscriptions().sauvegarder();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		else
		{
			GestionDesErreurs.afficherMessage(messageErreur,"Impossible de rentrer un champs vide","erreur");
		}
			
	}
}
