package interfaceGraphique.controls.Competition;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GererCandidatsController;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GererCandidats extends Stage{
	private BorderPane rootLayout;
	private GestionCompetitionsController stageGestion;
	private ObservableList<Candidat> listCandidatsCompet = FXCollections.observableArrayList();
	private ObservableList<Candidat> listAutresCandidats = FXCollections.observableArrayList();
	
	public GererCandidats(GestionCompetitionsController stageGestion) {
		this.setResizable(false);
		this.setTitle("Gérer candidats");
		this.initModality(Modality.APPLICATION_MODAL);
		this.stageGestion = stageGestion;
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutCompetition.class.getResource("../../view/Competition/GererCandidats.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindLists();
	            GererCandidatsController controller = loader.getController();
	            controller.setClass(this, stageGestion);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	
	
    public void bindLists(){
    	if(stageGestion.getCompetitionActive().estEnEquipe()){
			for(Candidat c : MonAppli.getInscriptions().getCandidats()){
				if(stageGestion.getCompetitionActive().getCandidats().contains(c) && c instanceof Equipe)
					listCandidatsCompet.add(c);
				else if(!stageGestion.getCompetitionActive().getCandidats().contains(c) && c instanceof Equipe)
					listAutresCandidats.add(c);	}
    	}
    	else{
    		for(Candidat c : MonAppli.getInscriptions().getCandidats()){
    			if(stageGestion.getCompetitionActive().getCandidats().contains(c) && c instanceof Personne)
    				listCandidatsCompet.add(c);
    			else if(!stageGestion.getCompetitionActive().getCandidats().contains(c) && c instanceof Personne)
    				listAutresCandidats.add(c);
    		}
    	}
	}
    
    public ObservableList<Candidat> getListCandidats(){
    	return listCandidatsCompet;
    } 
    
    public ObservableList<Candidat> getListAutresCandidats(){
    	return listAutresCandidats;
    } 
}
