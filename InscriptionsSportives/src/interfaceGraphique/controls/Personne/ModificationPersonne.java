package interfaceGraphique.controls.Personne;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.view.Personne.GestionPersonneController;
import interfaceGraphique.view.Personne.ModificationPersonneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre permet la modification des champs d'une personne (nom,
 * prénom, mail).
 * @author Jean
 *
 */
public class ModificationPersonne extends Stage{

	private BorderPane rootLayout;
	private GestionPersonne stageGestion;
	private GestionPersonneController stageGestionController;
	String mailActuel;
	
	public ModificationPersonne(GestionPersonne stageGestion,String mail,GestionPersonneController stageGestionController){
		this.setResizable(false);
		this.stageGestion = stageGestion;
		this.setTitle("Modification d'une personne");
		this.initModality(Modality.APPLICATION_MODAL);
		this.mailActuel = mail;
		this.stageGestionController = stageGestionController;
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue ModificationPersonne.fxml
	 * et appelle le controller qui lui est lié.
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AjoutEquipe.class.getResource("../../view/Personne/ModificationPersonne.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            ModificationPersonneController controller = loader.getController();	 
	            controller.setStage(this, stageGestion,mailActuel,stageGestionController);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	 
}
