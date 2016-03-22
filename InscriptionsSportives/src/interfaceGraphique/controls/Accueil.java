package interfaceGraphique.controls;

import java.io.IOException;

import interfaceGraphique.view.AccueilController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Accueil extends Stage{
	
private BorderPane rootLayout;
	
	public Accueil(){
		this.setTitle("Bienvenue");
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Accueil.class.getResource("../view/Accueil.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            
	            AccueilController controller = loader.getController();	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }
}
