package interfaceGraphique.controls.Personne;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.view.Equipe.AjoutEquipeController;
import interfaceGraphique.view.Personne.AjoutPersonneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre permet l'ajout d'une personne aux données.
 * @author Jean
 *
 */
public class AjoutPersonne extends Stage
{
	private BorderPane rootLayout;
	private GestionPersonne stageGestion;
	
	public AjoutPersonne(GestionPersonne stageGestion){
		this.setResizable(false);
		this.stageGestion = stageGestion;
		this.setTitle("Ajout d'un personne");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue AjoutPersonne.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutEquipe.class.getResource("../../view/Personne/AjoutPersonne.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            AjoutPersonneController controller = loader.getController();	 
	            controller.setStage(this, stageGestion);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
