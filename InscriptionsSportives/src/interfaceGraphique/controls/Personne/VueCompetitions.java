package interfaceGraphique.controls.Personne;

import java.io.IOException;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.view.Personne.VueCompetitionsController;
import interfaceGraphique.view.Personne.GestionPersonneController;
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
	private GestionPersonneController stageGestion;
	
	private ObservableList<Competition> listeCompete = FXCollections.observableArrayList();
	
	public VueCompetitions(GestionPersonneController stageGestion){
		this.setResizable(false);
		this.setTitle("Les compétitions associées");
		this.stageGestion = stageGestion;

		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {

	            
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(VueCompetitions.class.getResource("../../view/Personne/VueCompetitions.fxml"));
	            
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
		for(Competition c :stageGestion.getPersonneActive().getCompetitions())
    		listeCompete.add(c);
		if(!stageGestion.getPersonneActive().getEquipes().isEmpty())
		{
			for(Equipe e :stageGestion.getPersonneActive().getEquipes())
				if(!e.getCompetitions().isEmpty())
					for(Competition comp : e.getCompetitions())
						listeCompete.add(comp);
		}
			
				
		
	}	 
	
	public ObservableList<Competition> getLiseCompetitions(){
		return listeCompete;
	} 
}
