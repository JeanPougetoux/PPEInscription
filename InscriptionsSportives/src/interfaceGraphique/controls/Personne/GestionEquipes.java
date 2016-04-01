package interfaceGraphique.controls.Personne;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Equipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Personne.GestionEquipesController;
import interfaceGraphique.view.Personne.GestionPersonneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre permet de gérer les équipes auxquelles appartient
 * une personne.
 * @author Jean
 *
 */
public class GestionEquipes extends Stage{

	private BorderPane rootLayout;
	private GestionPersonneController stageGestion;
	private ObservableList<Equipe> listEquipes = FXCollections.observableArrayList();
	private ObservableList<Equipe> listAutres = FXCollections.observableArrayList();
	
	public GestionEquipes(GestionPersonneController stageGestion) {
		this.setResizable(false);
		this.setTitle("Gestion équipes");
		this.initModality(Modality.APPLICATION_MODAL);
		this.stageGestion = stageGestion;
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue GestionEquipes.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GestionPersonne.class.getResource("../../view/Personne/GestionEquipes.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            bindList();
	            GestionEquipesController controller = loader.getController();	 
	            controller.setStage(this, stageGestion);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
	 
	/**
	 * Permet de fabriquer les deux listes des équipes d'une personne
	 * et des autres équipes.
	 */
    private void bindList(){
		for(Candidat e : MonAppli.getInscriptions().getCandidats()){
			if(e instanceof Equipe){
				if(stageGestion.getPersonneActive().getEquipes().contains(e)){
					listEquipes.add((Equipe)e);
				}
				else{
					listAutres.add((Equipe)e);
				}
			}
		}
	}
    
    /**
     * @return la liste des équipes de la personne sélectionnée.
     */
    public ObservableList<Equipe> getListEquipes(){
    	return listEquipes;
    } 
    
    /**
     * @return la liste des autres équipes.
     */
    public ObservableList<Equipe> getListAutres(){
    	return listAutres;
    }
}
