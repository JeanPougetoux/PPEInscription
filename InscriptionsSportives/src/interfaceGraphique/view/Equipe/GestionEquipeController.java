package interfaceGraphique.view.Equipe;

import inscriptions.Equipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class GestionEquipeController {
	ObservableList<Equipe> listEquipes = FXCollections.observableArrayList();
	
	@FXML
    private TableView<Equipe> equipeTable = new TableView<Equipe>();
    @FXML
    private TableColumn<Equipe, String> nameColumn;
    @FXML 
    private Button ajoutEquipe;
    @FXML
    private Button gererMembres;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private Label labelEquipe;
    private GestionEquipe stageGestion;
    private Equipe equipeActive;
    
    public GestionEquipeController(){
    	
    }
    
    @FXML
    private void initialize(){
    	nameColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
				getValue().getNom()));
    	setChoixVisibility(false);
    }
    
    public void setClass(GestionEquipe stageGestion){
    	this.stageGestion = stageGestion;
    	equipeTable.setItems(stageGestion.getList());
    	equipeTable.setOnMouseClicked(new ActionClickTable(this));
    }
    
    public void setChoixVisibility(boolean visible){
    	labelEquipe.setVisible(visible);
    	gererMembres.setVisible(visible);
    	supprimer.setVisible(visible);
    	modifier.setVisible(visible);
    }   
    
    public void setNomEquipe(String texte){
    	labelEquipe.setText(texte);
    }
    
    public TableView<Equipe> getTable(){
    	return equipeTable;
    }
    
    public void setEquipeActive(Equipe equipe){
    	equipeActive = equipe;   
//    	gererCandidats.setOnAction(new ActionGererCandidats(competActive));
    }
}

class ActionClickTable implements EventHandler<MouseEvent>{

	private GestionEquipeController stageGestion;
	public ActionClickTable(GestionEquipeController stageGestion) {
		this.stageGestion = stageGestion;
	}

	@Override
	public void handle(MouseEvent event) {
		Equipe equipe = stageGestion.getTable().getSelectionModel().getSelectedItem();
		stageGestion.setChoixVisibility(true);
		stageGestion.setNomEquipe(equipe.getNom());
		stageGestion.setEquipeActive(equipe);
	}
	
}