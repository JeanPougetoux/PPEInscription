package interfaceGraphique.controls.Personne;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import interfaceGraphique.view.Personne.GestionPersonneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionPersonne extends Stage

{
	private BorderPane rootLayout;
	private ObservableList<Personne> listPersonnes = FXCollections.observableArrayList();
	
	public GestionPersonne(){
		this.setResizable(false);
		this.setTitle("Personnes");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GestionPersonne.class.getResource("../../view/Personne/GestionPersonne.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindList();
	            GestionPersonneController controller = loader.getController();	 
	            controller.setClass(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
	 
    private void bindList(){
		for(Candidat c : MonAppli.getInscriptions().getCandidats()){
			if(c instanceof Personne)
				listPersonnes.add((Personne)c);
		}
	}
    
    public ObservableList<Personne> getList(){
    	return listPersonnes;
    } 
}
