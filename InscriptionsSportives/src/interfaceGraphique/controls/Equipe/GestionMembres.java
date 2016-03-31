package interfaceGraphique.controls.Equipe;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.AjoutCompetition;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Equipe.GestionMembresController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionMembres extends Stage{

	private GestionEquipeController stageGestion;
	private BorderPane rootLayout;
	private ObservableList<Personne> listMembresEquipe = FXCollections.observableArrayList();
	private ObservableList<Personne> listAutresPersonnes = FXCollections.observableArrayList();
	
	public GestionMembres(GestionEquipeController stageGestion){
		this.setResizable(false);
		this.setTitle("Gérer membres");
		this.initModality(Modality.APPLICATION_MODAL);
		this.stageGestion = stageGestion;
		initLayouts();
	}
	
	public void initLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AjoutCompetition.class.getResource("../../view/Equipe/GestionMembres.fxml"));
            GridPane panelPrincipal = (GridPane) loader.load();
            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
            rootLayout = new BorderPane();
            rootLayout.setPrefWidth(panelPrincipal.getWidth());
            rootLayout.setPrefHeight(panelPrincipal.getHeight());
            Scene scene = new Scene(rootLayout);
            this.setScene(scene);
            rootLayout.setCenter(panelPrincipal);
            bindLists();
            GestionMembresController controller = loader.getController();
            controller.setStage(this, stageGestion);;
        } catch (IOException e) {
            e.printStackTrace();
        }
 }	

public void bindLists(){
	for(Candidat p : MonAppli.getInscriptions().getCandidats()){
		if(p instanceof Personne){
			if(stageGestion.getEquipeActive().getMembres().contains(p))
				listMembresEquipe.add((Personne)p);
			else
				listAutresPersonnes.add((Personne)p);	
		}
	}
}

public ObservableList<Personne> getListMembres(){
	return listMembresEquipe;
} 

public ObservableList<Personne> getListAutresMembres(){
	return listAutresPersonnes;
} 
}
