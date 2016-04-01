package interfaceGraphique.controls;

import java.io.IOException;

import interfaceGraphique.view.ConnexionSecuriseeController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Première fenêtre instanciée depuis la classe MonAppli.
 * Elle hérite de Stage et permet la vérification du mot de passe.
 * @author Jean
 *
 */
public class ConnexionSecurisee extends Stage{
	
private BorderPane rootLayout;
	
	public ConnexionSecurisee(){
		this.setTitle("Connexion");
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue ConnexionSecurisee.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ConnexionSecurisee.class.getResource("../view/ConnexionSecurisee.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            ConnexionSecuriseeController controller = loader.getController();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }
}
