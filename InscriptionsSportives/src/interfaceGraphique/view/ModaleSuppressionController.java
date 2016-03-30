package interfaceGraphique.view;

import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Personne.GestionPersonneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ModaleSuppressionController {

	@FXML
	private Button oui;
	@FXML
	private Button non;
	private Object stageGestion;
	private ModaleSuppression stageDelete;
	
	public void setStage(Object stageGestion, ModaleSuppression stageDelete){
		this.stageGestion = stageGestion;
		this.stageDelete = stageDelete;
		oui.setOnAction(getActionOui());
		non.setOnAction(getActionNon());
	}
	
	public EventHandler<ActionEvent> getActionOui(){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(stageGestion instanceof GestionCompetitionsController)
					((GestionCompetitionsController)stageGestion).deleteElement();
				else if(stageGestion instanceof GestionEquipeController)
					((GestionEquipeController)stageGestion).deleteElement();
				else if(stageGestion instanceof GestionPersonneController)
					((GestionPersonneController)stageGestion).deleteElement();
				stageDelete.close();
			}
		};
	}
	
	public EventHandler<ActionEvent> getActionNon(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				stageDelete.close();	
			}
		};
	}
}