package interfaceGraphique.controls;

import java.io.IOException;

import interfaceGraphique.view.AccueilController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Fenêtre instanciée si la ConnexionSecurisée est validée.
 * Elle hérite de Stage.
 * @author Jean
 *
 */
public class Accueil extends Stage{
	
private BorderPane rootLayout;
	
	public Accueil(){
		this.setResizable(false);
		this.setTitle("Bienvenue");
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue Accueil.fxml
	 * et appelle le controller qui lui est lié.
	 */
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
