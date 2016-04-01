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

/**
 * Cette fenêtre permet de voir les compétitions auxquelles est
 * inscrite l'équipe sélectionnée.
 * @author Jean
 *
 */
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
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue GestionEquipe.fxml
	 * et appelle le controller qui lui est lié.
	 * Appelle le bindList ainsi que le setStage qui initialise les variables du
	 * controller correspondant à cette fenêtre et à la GestionEquipeController.
	 */
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

	 /**
	  * Fabrique la fenêtre des compétitions correspondant à l'équipe sélectionnée.
	  */
	private void bindList() {
		for(Competition c :stageGestion.getEquipeActive().getCompetitions())
    		listeCompete.add(c);
		
	}	 
	
	/**
	 * Retourne la fenêtre des compétitions.
	 * @return
	 */
	public ObservableList<Competition> getLiseCompetitions(){
		return listeCompete;
	} 
}
