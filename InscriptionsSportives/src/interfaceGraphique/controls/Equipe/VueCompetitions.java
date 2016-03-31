package interfaceGraphique.controls.Equipe;

import java.io.IOException;

import inscriptions.Competition;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Equipe.ModificationEquipeController;
import interfaceGraphique.view.Equipe.VueCompetitionsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VueCompetitions extends Stage{
	private BorderPane rootLayout;
	private GestionEquipeController stageGestion;
	
	private ObservableList<Competition> listeCompete = FXCollections.observableArrayList();
	
	public VueCompetitions(GestionEquipeController stageGestion){
		this.setResizable(false);
		this.setTitle("Les compétitions associées");
		this.stageGestion = stageGestion;

		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {

	            
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(VueCompetitions.class.getResource("../../view/Equipe/VueCompetitions.fxml"));
	            
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindList();
	            VueCompetitionsController controller = loader.getController();	 
	            controller.setStage(stageGestion, this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

	private void bindList() {
		for(Competition c :stageGestion.getEquipeActive().getCompetitions())
    		listeCompete.add(c);
		
	}	 
	
	public ObservableList<Competition> getLiseCompetitions(){
		return listeCompete;
	} 
}
