package interfaceGraphique.view.Equipe;

import java.io.IOException;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.VueCompetitions;
import interfaceGraphique.controls.Personne.AjoutPersonne;
import interfaceGraphique.controls.Personne.GestionPersonne;
import interfaceGraphique.controls.Personne.ModificationPersonne;
import interfaceGraphique.view.Personne.GestionPersonneController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class VueCompetitionsController {

	 @FXML
	    private TableColumn<Competition, String> nameColumn;
	    @FXML
	    private TableColumn<Competition, String> dateColumn;
	    @FXML
	    private TableColumn<Competition, String> teamColumn;
	
	private Equipe equipeActive;

	private VueCompetitions stageGestion;
	GestionEquipeController stageEquipeController;
    @FXML
    private TableView<Competition> competitionsTable = new TableView<Competition>();
  
	 public VueCompetitionsController(){
	    	
	    }
	    
	    @FXML
	    private void initialize()
	    {
	    	nameColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
					getValue().getNom()));

	    	dateColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
					getValue().
					getDateCloture().
					toString()));

	    	teamColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(Utilitaires.getOuiNon(
					CellDataFeatures.
					getValue().estEnEquipe())));
	    	
	    }
	    
	   
	    
	    public void handleOk()
	    {
	    	stageGestion.close();
	    }
	    
	   
	    
	    public TableView<Competition> getTable(){
	    	return competitionsTable;
	    }

		public void setStage(GestionEquipeController stageGestion2, VueCompetitions vueCompetitions) 
		{
		
			this.equipeActive = stageGestion2.getEquipeActive();
			this.stageGestion = vueCompetitions;
			competitionsTable.setItems(stageGestion.getLiseCompetitions());
			
		}
	    
	   
	    

}

