package interfaceGraphique.view.Competition;


import java.io.IOException;
import java.util.Iterator;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionAjoutEquipeCompetition;
import exceptions.ExceptionAjoutPersonneCompetition;
import exceptions.ExceptionCompetition;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.AjoutCompetition;
import interfaceGraphique.controls.Competition.GererCandidats;
import interfaceGraphique.controls.Competition.GestionCompetitions;
import interfaceGraphique.controls.Competition.ModificationCompetition;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class GestionCompetitionsController {
	
	
	@FXML
    private TableView<Competition> competitionTable = new TableView<Competition>();
    @FXML
    private TableColumn<Competition, String> nameColumn;
    @FXML
    private TableColumn<Competition, String> dateColumn;
    @FXML
    private TableColumn<Competition, String> teamColumn;
    @FXML 
    private Button ajoutCompetition;
    @FXML
    private Button gererCandidats;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private Label labelCompetition;
    private GestionCompetitions stageGestion;
    private Competition competActive;
    
    
    public GestionCompetitionsController(){
    }
    
    @FXML
    private void initialize() throws ExceptionCompetition{
    	nameColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
																				getValue().getNom()));

		dateColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
																				getValue().
																				getDateCloture().
																				toString()));
		
		teamColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(Utilitaires.getOuiNon(
																				CellDataFeatures.
																				getValue().estEnEquipe())));
		setChoixVisibility(false);
    }
    
    public void setChoixVisibility(boolean visible){
    	labelCompetition.setVisible(visible);
    	gererCandidats.setVisible(visible);
    	supprimer.setVisible(visible);
    	modifier.setVisible(visible);
    }
    
    public void setStage(GestionCompetitions stageGestion){
    	this.stageGestion = stageGestion;
    	competitionTable.setItems(stageGestion.getList());
    }
    
    public TableView<Competition> getTable(){
    	return competitionTable;
    }
    
    public void setNomCompetition(String texte){
    	labelCompetition.setVisible(true);
    	labelCompetition.setText(texte);
    }
    
    public void setCompetitionActive(Competition compet){
    	competActive = compet;   
    }
    
    public Competition getCompetitionActive(){
    	return competActive;
    }
    
    public void actualiserList(){
    	this.getTable().refresh();
    	setCompetitionActive(null);
    	setChoixVisibility(false);
    }
    
    public void deleteElement(){    	
		stageGestion.getList().remove(competActive);
		competActive.delete();
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	setCompetitionActive(null);
    	setChoixVisibility(false);
    }
    
    public void actionClickTable(){
    	if(!this.getTable().getSelectionModel().getSelectedCells().isEmpty()){
			Competition compet = this.getTable().getSelectionModel().getSelectedItem();
			this.setChoixVisibility(true);
			this.setNomCompetition(compet.getNom());
			this.setCompetitionActive(compet);
		}
    }
    
    public void actionAjoutCompetition(){
    	AjoutCompetition fenetreAjout = new AjoutCompetition(stageGestion);
		fenetreAjout.show();
    }
    
    public void actionDeleteCompetition(){
    	ModaleSuppression fenetreSuppression = new ModaleSuppression(this);
		fenetreSuppression.show();
    }
    
    public void actionGererCandidats(){
    	GererCandidats fenetreCandidats = new GererCandidats(this);
		fenetreCandidats.show();
    }
    
    public void actionModifierCompetition(){
    	ModificationCompetition fenetreModification = new ModificationCompetition(this);
    	fenetreModification.show();
    }
}
