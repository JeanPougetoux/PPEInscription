package interfaceGraphique.controls.Competition;

import java.io.IOException;

import inscriptions.Competition;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre offre différentes possibilitées quand à la 
 * gestion des compétitions (modifier, supprimer, etc).
 * @author Jean
 *
 */
public class GestionCompetitions extends Stage{
	private BorderPane rootLayout;
	private ObservableList<Competition> listCompetitions = FXCollections.observableArrayList();
	
	public GestionCompetitions(){
		this.setResizable(false);
		this.setTitle("Compétitions");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue GestionCompetition.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GestionCompetitions.class.getResource("../../view/Competition/GestionCompetitions.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindList();
	            GestionCompetitionsController controller = loader.getController();	 
	            controller.setStage(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
	 
	/**
	 * Fabrique la liste à partir de toutes les compétitions enregistrés.
	 */
    public void bindList(){
    	listCompetitions = FXCollections.observableArrayList();
		for(Competition c : MonAppli.getInscriptions().getCompetitions())
		{
			listCompetitions.add(c);
		}
	}
    
    /**
     * @return la liste des compétitions.
     */
    public ObservableList<Competition> getList(){
    	return listCompetitions;
    } 
}
