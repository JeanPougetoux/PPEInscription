package interfaceGraphique.view;

import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Personne.GestionPersonneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller de la vue ModaleSuppression.fxml
 * et appellé par la classe ModaleSuppression.
 * @author Jean
 *
 */
public class ModaleSuppressionController {

	@FXML
	private Button oui;
	@FXML
	private Button non;
	private Object stageGestion;
	private ModaleSuppression stageDelete;
	
	/**
	 * Initialise les variables stageGestion et stageDelete.
	 * Donne aux boutons oui et non leurs actions.
	 * @param stageGestion
	 * @param stageDelete
	 */
	public void setStage(Object stageGestion, ModaleSuppression stageDelete){
		this.stageGestion = stageGestion;
		this.stageDelete = stageDelete;
		oui.setOnAction(getActionOui());
		non.setOnAction(getActionNon());
	}
	
	/**
	 * @return l'eventHandler du bouton oui qui appelle la méthode delete 
	 * de la fenêtre initialisée.
	 */
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
	
	/**
	 * @return l'eventHandler du bouton non qui ferme la fenêtre.
	 */
	public EventHandler<ActionEvent> getActionNon(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				stageDelete.close();	
			}
		};
	}
}