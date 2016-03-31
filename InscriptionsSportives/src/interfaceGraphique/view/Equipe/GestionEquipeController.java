package interfaceGraphique.view.Equipe;

import java.io.IOException;

import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Equipe.GestionMembres;
import interfaceGraphique.controls.Equipe.ModificationEquipe;
import interfaceGraphique.controls.Equipe.VueCompetitions;
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
    private Button voirCompetitions;
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
    }
    
    public void setChoixVisibility(boolean visible){
    	labelEquipe.setVisible(visible);
    	gererMembres.setVisible(visible);
    	supprimer.setVisible(visible);
    	modifier.setVisible(visible);
    	voirCompetitions.setVisible(visible);
    }   
    
    public void setNomEquipe(String texte){
    	labelEquipe.setText(texte);
    }
    
    public TableView<Equipe> getTable(){
    	return equipeTable;
    }
    
    public void setEquipeActive(Equipe equipe){
    	equipeActive = equipe;   
    }
    
    public Equipe getEquipeActive(){
    	return equipeActive;
    }
    
    public void actualise(){
    	equipeTable.refresh();
    	setEquipeActive(null);
    	setChoixVisibility(false);
    }
    
    public void actionBoutonAjout(){
    	AjoutEquipe fenetreAjout = new AjoutEquipe(stageGestion);
    	fenetreAjout.show();
    }
    
    public void actionBoutonSupprimer(){
    	ModaleSuppression modaleSupp = new ModaleSuppression(this);
    	modaleSupp.show();
    }
    
    public void actionBoutonModifier(){
    	ModificationEquipe fenetreModif = new ModificationEquipe(this);
    	fenetreModif.show();
    }
    
    public void actionBoutonGererMembres(){
    	GestionMembres fenetreMembres = new GestionMembres(this);
    	fenetreMembres.show();
    }
    
    public void actionBoutonVoirCompetitions(){
    	VueCompetitions fenetreCompetitions = new VueCompetitions(this);
    	fenetreCompetitions.show();
    }
    
    public void deleteElement(){    	
		stageGestion.getList().remove(equipeActive);
		try {
			equipeActive.delete();
		} catch (ExceptionRetraitPersonneEquipe e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	setChoixVisibility(false);
    	setEquipeActive(null);
    }
    
    public void actionClickTable(){
    	if(!this.getTable().getSelectionModel().getSelectedCells().isEmpty()){
	    	Equipe equipe = this.getTable().getSelectionModel().getSelectedItem();
			this.setChoixVisibility(true);
			this.setNomEquipe(equipe.getNom());
			this.setEquipeActive(equipe);
    	}
    }
}