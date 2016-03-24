package interfaceGraphique.controls.Equipe;

import java.io.IOException;

import inscriptions.Equipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Equipe.AjoutEquipeController;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjoutEquipe extends Stage{
	private BorderPane rootLayout;
	private GestionEquipe stageGestion;
	
	public AjoutEquipe(GestionEquipe stageGestion){
		this.stageGestion = stageGestion;
		this.setTitle("Ajout équipe");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutEquipe.class.getResource("../../view/Equipe/AjoutEquipe.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            AjoutEquipeController controller = loader.getController();	 
	            controller.setStage(this, stageGestion);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
