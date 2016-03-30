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
	
	ObservableList<Competition> listCompetitions = FXCollections.observableArrayList();
	
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
    	competitionTable.setOnMouseClicked(new ActionClickTable(this));
		ajoutCompetition.setOnAction(new ActionAjoutCompetition(stageGestion));
		supprimer.setOnAction(new ActionDeleteCompetition(this));
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
    	gererCandidats.setOnAction(new ActionGererCandidats(this));
    }
    
    public Competition getCompetitionActive(){
    	return competActive;
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
    
    public void addElementToCompet(Candidat c) throws ExceptionAjoutPersonneCompetition{
		if(competActive.estEnEquipe()){
			
				try 
				{
					competActive.add((Equipe)c);
				} 
				catch (ExceptionAjoutEquipeCompetition e) 
				{
					System.out.println(e.toString());
				}
			
		}
		else if(!competActive.estEnEquipe()){
			try {
				competActive.add((Personne)c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void removeElementOfCompet(Candidat c){
    	competActive.remove(c);
    	Inscriptions i = Inscriptions.getInscriptions();
    	for(Competition a : i.getCompetitions()){
    		if(a.equals(competActive)){
		    	if(competActive.getCandidats().contains(c)){
		    		System.out.println("ca marche pas du tout gros");
		    	}
    		}
    	}
    }
}

class ActionClickTable implements EventHandler<MouseEvent>{

	private GestionCompetitionsController stageGestion;
	public ActionClickTable(GestionCompetitionsController stageGestion) {
		this.stageGestion = stageGestion;
	}

	@Override
	public void handle(MouseEvent event) {
		if(!stageGestion.getTable().getSelectionModel().getSelectedCells().isEmpty()){
			Competition compet = stageGestion.getTable().getSelectionModel().getSelectedItem();
			stageGestion.setChoixVisibility(true);
			stageGestion.setNomCompetition(compet.getNom());
			stageGestion.setCompetitionActive(compet);
		}
	}
	
}

class ActionAjoutCompetition implements EventHandler<ActionEvent>{

	private GestionCompetitions stageGestion;
	public ActionAjoutCompetition(GestionCompetitions stageGestion) {
		this.stageGestion = stageGestion;
	}
	@Override
	public void handle(ActionEvent event) {
		AjoutCompetition fenetreAjout = new AjoutCompetition(stageGestion);
		fenetreAjout.show();
	}
	
}

class ActionDeleteCompetition implements EventHandler<ActionEvent>{

	private GestionCompetitionsController stageGestion;
	public ActionDeleteCompetition(GestionCompetitionsController stageGestion) {
		this.stageGestion = stageGestion;
	}
	@Override
	public void handle(ActionEvent event) {
		ModaleSuppression fenetreSuppression = new ModaleSuppression(stageGestion);
		fenetreSuppression.show();
	}
	
}

class ActionGererCandidats implements EventHandler<ActionEvent>{
		
	private GestionCompetitionsController stageGestion;
	public ActionGererCandidats(GestionCompetitionsController stageGestion) {
		this.stageGestion = stageGestion;
	}
	@Override
	public void handle(ActionEvent event) {
		GererCandidats fenetreCandidats = new GererCandidats(stageGestion);
		fenetreCandidats.show();
	}
	
}
