package interfaceGraphique.controls.Competition;

import java.io.IOException;

import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GestionCompetitionsController;
import interfaceGraphique.view.Competition.ModificationCompetitionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre permet la modification d'une compétition.
 * @author Jean
 *
 */
public class ModificationCompetition extends Stage{

	private BorderPane rootLayout;
	private GestionCompetitionsController stageGestion;
	
	public ModificationCompetition(GestionCompetitionsController stageGestion){
		this.setResizable(false);
		this.stageGestion = stageGestion;
		this.setTitle("Modification d'une compétition");
		this.initModality(Modality.APPLICATION_MODAL);
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue ModificationCompetition.fxml
	 * et appelle le controller qui lui est lié.
	 */
	public void initLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GestionCompetitions.class.getResource("../../view/Competition/ModificationCompetition.fxml"));
            GridPane panelPrincipal = (GridPane) loader.load();
            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
            rootLayout = new BorderPane();
            rootLayout.setPrefWidth(panelPrincipal.getWidth());
            rootLayout.setPrefHeight(panelPrincipal.getHeight());
            Scene scene = new Scene(rootLayout);
            this.setScene(scene);
            rootLayout.setCenter(panelPrincipal);
            ModificationCompetitionController controller = loader.getController();	 
            controller.setClass(this, stageGestion);
        } catch (IOException e) {
            e.printStackTrace();
        }
 }	 
}
