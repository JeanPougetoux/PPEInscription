package interfaceGraphique.controls.Personne;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.view.Personne.AjoutPersonneController;
import interfaceGraphique.view.Personne.ModificationPersonneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificationPersonne extends Stage{

	private BorderPane rootLayout;
	private GestionPersonne stageGestion;
	String mailActuel;
	
	public ModificationPersonne(GestionPersonne stageGestion,String mail){
		this.stageGestion = stageGestion;
		this.setTitle("Modification d'une personne");
		this.initModality(Modality.APPLICATION_MODAL);
		this.mailActuel = mail;
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutEquipe.class.getResource("../../view/Personne/ModificationPersonne.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            ModificationPersonneController controller = loader.getController();	 
	            controller.setStage(this, stageGestion,mailActuel);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
