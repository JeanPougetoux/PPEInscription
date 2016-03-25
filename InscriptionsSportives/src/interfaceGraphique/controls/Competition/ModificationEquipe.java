package interfaceGraphique.controls.Competition;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Competition.ModificationEquipeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModificationEquipe extends Stage{
	private BorderPane rootLayout;
	
	public ModificationEquipe(){
		this.setTitle("Modification équipe");
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ModificationEquipe.class.getResource("../../view/Competition/modificationEquipe.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            
	            ModificationEquipeController controller = loader.getController();	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
