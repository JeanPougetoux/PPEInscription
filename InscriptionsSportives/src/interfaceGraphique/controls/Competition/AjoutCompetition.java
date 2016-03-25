package interfaceGraphique.controls.Competition;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.AjoutCompetitionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjoutCompetition extends Stage{
	private BorderPane rootLayout;
	private GestionCompetitions stageGestion;
	
	public AjoutCompetition(GestionCompetitions stageGestion) {
		this.setTitle("Ajout compétition");
		this.initModality(Modality.APPLICATION_MODAL);
		this.stageGestion = stageGestion;
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutCompetition.class.getResource("../../view/Competition/AjoutCompetition.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            
	            AjoutCompetitionController controller = loader.getController();
	            controller.setStagesCompetition(this, stageGestion);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
