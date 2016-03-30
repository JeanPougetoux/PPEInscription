package interfaceGraphique.controls.Equipe;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Equipe.ModificationEquipeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModificationEquipe extends Stage{
	private BorderPane rootLayout;
	private GestionEquipeController stageGestion;
	
	public ModificationEquipe(GestionEquipeController stageGestion){
		this.setResizable(false);
		this.setTitle("Modification équipe");
		this.stageGestion = stageGestion;
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ModificationEquipe.class.getResource("../../view/Equipe/modificationEquipe.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            
	            ModificationEquipeController controller = loader.getController();	 
	            controller.setStage(stageGestion, this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
