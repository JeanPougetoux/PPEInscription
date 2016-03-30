package interfaceGraphique.controls.Equipe;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionEquipe extends Stage{
	private BorderPane rootLayout;
	private ObservableList<Equipe> listEquipes = FXCollections.observableArrayList();
	
	public GestionEquipe(){
		this.setResizable(false);
		this.setTitle("Equipes");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GestionEquipe.class.getResource("../../view/Equipe/GestionEquipe.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindList();
	            GestionEquipeController controller = loader.getController();	 
	            controller.setClass(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
	 
    private void bindList(){
		for(Candidat c : MonAppli.getInscriptions().getCandidats()){
			if(c instanceof Equipe)
				listEquipes.add((Equipe)c);
		}
	}
    
    public ObservableList<Equipe> getList(){
    	return listEquipes;
    } 
}
