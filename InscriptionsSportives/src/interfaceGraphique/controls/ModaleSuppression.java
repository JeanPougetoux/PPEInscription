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

public class ModaleSuppression extends Stage{

	private BorderPane rootLayout;
	GestionCompetitionsController stageGestion;
	
	public ModaleSuppression(GestionCompetitionsController stageGestion){
		this.stageGestion = stageGestion;
		this.setTitle("Supprimer");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
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
