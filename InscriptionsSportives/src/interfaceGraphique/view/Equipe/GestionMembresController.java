package interfaceGraphique.view.Equipe;

import java.io.IOException;
import java.util.ArrayList;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.GestionMembres;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class GestionMembresController {

	@FXML
    private TableView<Personne> membresEquipe = new TableView<Personne>();
    @FXML
    private TableColumn<Personne, String> nameMembresEquipe;
    @FXML
    private TableColumn<Personne, String> prenomMembresEquipe;
    @FXML
    private TableColumn<Personne, Boolean> checkBoxMembresEquipe;
	@FXML
    private TableView<Personne> autrePersonnes = new TableView<Personne>();
    @FXML
    private TableColumn<Personne, String> nameAutresPersonnes;
    @FXML
    private TableColumn<Personne, String> prenomAutresPersonnes;
    @FXML
    private TableColumn<Personne, Boolean> checkBoxAutresPersonnes;
    @FXML
    private Button membresVersAutres;
    @FXML
    private Button autresVersMembres;
    @FXML
    private Label messageErreur;
    private GestionMembres stageMembres;
    private GestionEquipeController stageGestion;
    private ArrayList<BooleanProperty> selectedRowListAutres = new ArrayList<BooleanProperty>();
    private ArrayList<BooleanProperty> selectedRowListMembres = new ArrayList<BooleanProperty>();
    
    public GestionMembresController(){
    	
    }
    
    @FXML
    private void initialize(){
    	messageErreur(null);
    	nameMembresEquipe.setCellValueFactory(CellDataFeatures -> 
    	new ReadOnlyStringWrapper(CellDataFeatures.getValue().getNom()));
    	nameAutresPersonnes.setCellValueFactory(CellDataFeatures ->
    	new ReadOnlyStringWrapper(CellDataFeatures.getValue().getNom()));
    	prenomMembresEquipe.setCellValueFactory(CellDataFeatures -> 
		new ReadOnlyStringWrapper(((Personne)CellDataFeatures.getValue()).getPrenom()));
    	prenomAutresPersonnes.setCellValueFactory(CellDataFeatures -> 
    	new ReadOnlyStringWrapper(((Personne)CellDataFeatures.getValue()).getPrenom()));
    	checkBoxMembresEquipe.setCellValueFactory(
    	    	new Callback<CellDataFeatures<Personne,Boolean>,ObservableValue<Boolean>>()
    	    	{
    	    	    @Override
    	    	    public ObservableValue<Boolean> call(CellDataFeatures<Personne,Boolean> cdf)
    	    	    {
    	    	        TableView<Personne> tblView = cdf.getTableView();

    	    	        Personne rowData = cdf.getValue();

    	    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	    	        return selectedRowListMembres.get( rowIndex );
    	    	    }
    	    	});
    	checkBoxAutresPersonnes.setCellValueFactory(
    	new Callback<CellDataFeatures<Personne,Boolean>,ObservableValue<Boolean>>()
    	{
    	    @Override
    	    public ObservableValue<Boolean> call(CellDataFeatures<Personne,Boolean> cdf)
    	    {
    	        TableView<Personne> tblView = cdf.getTableView();

    	        Personne rowData = cdf.getValue();

    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	        return selectedRowListAutres.get( rowIndex );
    	    }
    	});
    	checkBoxAutresPersonnes.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxAutresPersonnes));
    	checkBoxMembresEquipe.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxMembresEquipe));
    }
    
    public void setStage(GestionMembres stageMembres, GestionEquipeController stageGestion){
    	this.stageGestion = stageGestion;
    	this.stageMembres = stageMembres;
    	for(Personne p : stageMembres.getListAutresMembres()){
    		selectedRowListAutres.add(new SimpleBooleanProperty());
    	}
    	for(Personne p : stageMembres.getListMembres()){
    		selectedRowListMembres.add(new SimpleBooleanProperty());
    	}
    	membresEquipe.setItems(stageMembres.getListMembres());
    	autrePersonnes.setItems(stageMembres.getListAutresMembres());
    }
    
    public void messageErreur(Object o){
    	if(o == null){
    		messageErreur.setVisible(false);
    	}
    	else{
    		messageErreur.setVisible(true);
    		messageErreur.setText(o.toString());
    	}
    }
    
    public void clean(){
    	for(int i = 0; i < selectedRowListAutres.size(); i++){
    		selectedRowListAutres.get(i).set(false);
    	}
    	for(int i = 0; i < selectedRowListMembres.size(); i++){
    		selectedRowListMembres.get(i).set(false);
    	}
    }
    
    public void actionAutreVersMembres(){
    	messageErreur(null);
    	ArrayList<Personne> aEnlever = new ArrayList<>();
    	for(int i = 0; i < stageMembres.getListAutresMembres().size(); i++){
    		if(checkBoxAutresPersonnes.getCellData(i).booleanValue()){
    			stageGestion.getEquipeActive().add(stageMembres.getListAutresMembres().get(i));
    			aEnlever.add(stageMembres.getListAutresMembres().get(i));
    			stageMembres.getListMembres().add(stageMembres.getListAutresMembres().get(i));
    			selectedRowListMembres.add(new SimpleBooleanProperty());
    		}
    	}
    	
    	for(Candidat i : aEnlever){
    		stageMembres.getListAutresMembres().remove(i);
    	}
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	clean();
    }
    
    public void actionMembresVersAutres(){
    	messageErreur(null);
    	ArrayList<Personne> aEnlever = new ArrayList<>();
    	for(int i = 0; i < stageMembres.getListMembres().size(); i++){
    		if(checkBoxMembresEquipe.getCellData(i).booleanValue()){
    			stageGestion.getEquipeActive().remove(stageMembres.getListMembres().get(i));
    			aEnlever.add(stageMembres.getListMembres().get(i));
    			stageMembres.getListAutresMembres().add(stageMembres.getListMembres().get(i));
    			selectedRowListAutres.add(new SimpleBooleanProperty());
    		}
    	}
    	
    	for(Candidat i : aEnlever){
    		stageMembres.getListMembres().remove(i);
    	}
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	clean();
    }
}
