package interfaceGraphique.controls;

import java.io.IOException;

import interfaceGraphique.view.GestionCompetitionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GestionCompetitions extends Stage{
	private BorderPane rootLayout;
	
	public GestionCompetitions(){
		this.setTitle("Compétitions");
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GestionCompetitions.class.getResource("../view/GestionCompetitions.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            
	            GestionCompetitionsController controller = loader.getController();	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
