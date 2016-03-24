package interfaceGraphique.view.Equipe;

import java.io.IOException;


import exceptions.ExceptionNomEquipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AjoutEquipeController {
	
	@FXML
	private TextField newName;
	private AjoutEquipe stageAjout;
	private GestionEquipe stageGestion;
	
	public AjoutEquipeController(){
		
	}
	
	@FXML
	private void initialize(){
		
	}
	
	public void setStage(AjoutEquipe stageAjout, GestionEquipe stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
	}
	
	public void actionClose(){
		stageAjout.close();
	}
	
	public void actionAjout(){
		try {
			stageGestion.getList().add(MonAppli.getInscriptions().createEquipe(newName.getText()));
		} catch (ExceptionNomEquipe e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stageAjout.close();
	}
}
