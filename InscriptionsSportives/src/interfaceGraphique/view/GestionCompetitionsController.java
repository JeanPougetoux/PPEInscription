package interfaceGraphique.view;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionNomCompetition;
import inscriptions.Competition;
import interfaceGraphique.controls.MonAppli;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionCompetitionsController {
	
	ObservableList<Competition> listCompetitions = FXCollections.observableArrayList();
	
	@FXML
    private TableView<Competition> competitionTable = new TableView<Competition>();
    @FXML
    private TableColumn<Competition, String> nameColumn;
    @FXML
    private TableColumn<Competition, String> dateColumn;
    @FXML
    private TableColumn<Competition, String> teamColumn;
    
    
    public GestionCompetitionsController(){
    }
    
    @FXML
    private void initialize() throws ExceptionNomCompetition{
		setClass();
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
    
    public void setClass(){
		 competitionTable.setItems(bindList());
	 }
    
    private ObservableList<Competition> bindList(){
		for(Competition c : MonAppli.getInscriptions().getCompetitions()){
			listCompetitions.add(c);
		}
		return listCompetitions;
	}
}
