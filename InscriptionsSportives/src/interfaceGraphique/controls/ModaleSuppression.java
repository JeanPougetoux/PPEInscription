package interfaceGraphique.controls;

import java.io.IOException;

import interfaceGraphique.view.ModaleSuppressionController;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Fenêtre de validation de la suppression d'un élément.
 * Elle hérite de Stage et comporte deux boutons.
 * @author Jean
 *
 */
public class ModaleSuppression extends Stage{

	private BorderPane rootLayout;
	Object stageGestion;
	
	public ModaleSuppression(Object stageGestion){
		this.stageGestion = stageGestion;
		this.setTitle("Supprimer");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue ModaleSuppression.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ModaleSuppression.class.getResource("../view/ModaleSuppression.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            ModaleSuppressionController controller = loader.getController();	 
	            controller.setStage(stageGestion, this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
