package interfaceGraphique.view.Personne;

import java.io.IOException;
import java.util.ArrayList;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Personne.GestionEquipes;
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

/**
* Controller de la vue GestionEquipe.fxml
* IL est appellé par la classe GestionEquipe.
* @author thomas
*
*/
public class GestionEquipesController {
	
	@FXML
    private TableView<Equipe> equipesMembre = new TableView<Equipe>();
    @FXML
    private TableColumn<Equipe, String> nameEquipeMembre;
    @FXML
    private TableColumn<Equipe, Boolean> checkBoxEquipeMembre;
	@FXML
    private TableView<Equipe> autresEquipes = new TableView<Equipe>();
    @FXML
    private TableColumn<Equipe, String> nameAutresEquipes;
    @FXML
    private TableColumn<Equipe, Boolean> checkBoxAutresEquipes;
    @FXML
    private Button equipeVersAutres;
    @FXML
    private Button autresVersEquipes;
    @FXML
    private Label messageErreur;
    private GestionEquipes stageEquipes;
    private GestionPersonneController stageGestion;
    private ArrayList<BooleanProperty> selectedRowListAutres = new ArrayList<BooleanProperty>();
    private ArrayList<BooleanProperty> selectedRowListEquipes = new ArrayList<BooleanProperty>();
    
    public GestionEquipesController(){
    	
    }
    
    /**
     * Inititalise la fenêtre
     */
    @FXML
    private void initialize(){
    	messageErreur.setVisible(false);
    	nameEquipeMembre.setCellValueFactory(CellDataFeatures -> 
    	new ReadOnlyStringWrapper(CellDataFeatures.getValue().getNom()));
    	nameAutresEquipes.setCellValueFactory(CellDataFeatures ->
    	new ReadOnlyStringWrapper(CellDataFeatures.getValue().getNom()));
    	checkBoxEquipeMembre.setCellValueFactory(
    	    	new Callback<CellDataFeatures<Equipe,Boolean>,ObservableValue<Boolean>>()
    	    	{
    	    	    @Override
    	    	    public ObservableValue<Boolean> call(CellDataFeatures<Equipe,Boolean> cdf)
    	    	    {
    	    	        TableView<Equipe> tblView = cdf.getTableView();

    	    	        Equipe rowData = cdf.getValue();

    	    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	    	        return selectedRowListEquipes.get( rowIndex );
    	    	    }
    	    	});
    	checkBoxAutresEquipes.setCellValueFactory(
		    	new Callback<CellDataFeatures<Equipe,Boolean>,ObservableValue<Boolean>>()
		    	{
		    	    @Override
		    	    public ObservableValue<Boolean> call(CellDataFeatures<Equipe,Boolean> cdf)
		    	    {
		    	        TableView<Equipe> tblView = cdf.getTableView();
		
		    	        Equipe rowData = cdf.getValue();
		
		    	        int rowIndex = tblView.getItems().indexOf( rowData );
		
		    	        return selectedRowListAutres.get( rowIndex );
		    	    }
		    	});
    	checkBoxAutresEquipes.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxAutresEquipes));
    	checkBoxEquipeMembre.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxEquipeMembre));
    
    }
    /**
     * Initialise les listes
     * @param stageEquipes
     * @param stageGestion
     */
    public void setStage(GestionEquipes stageEquipes, GestionPersonneController stageGestion){
    	this.stageEquipes = stageEquipes;
    	this.stageGestion = stageGestion;
    	for(Equipe e : stageEquipes.getListAutres()){
    		selectedRowListAutres.add(new SimpleBooleanProperty());
    	}
    	for(Equipe e : stageEquipes.getListEquipes()){
    		selectedRowListEquipes.add(new SimpleBooleanProperty());
    	}
    	equipesMembre.setItems(stageEquipes.getListEquipes());
    	autresEquipes.setItems(stageEquipes.getListAutres());
    }
    /**
     * Permet de rafraîchir les tableaux
     */
    public void clean(){
    	for(int i = 0; i < selectedRowListAutres.size(); i++){
    		selectedRowListAutres.get(i).set(false);
    	}
    	for(int i = 0; i < selectedRowListEquipes.size(); i++){
    		selectedRowListEquipes.get(i).set(false);
    	}
    }
    
    /**
     * Permet de faire passer une équipe vers les équipes du candidat
     */
    public void boutonAutresVersEquipes(){
    	ArrayList<Equipe> aEnlever = new ArrayList<>();
    	for(int i = 0; i < stageEquipes.getListAutres().size(); i++){
    		if(checkBoxAutresEquipes.getCellData(i).booleanValue()){
    			stageEquipes.getListAutres().get(i).add(stageGestion.getPersonneActive());
    			aEnlever.add(stageEquipes.getListAutres().get(i));
    			stageEquipes.getListEquipes().add(stageEquipes.getListAutres().get(i));
    			selectedRowListEquipes.add(new SimpleBooleanProperty());
    		}
    	}
    	
    	for(Candidat i : aEnlever){
    		stageEquipes.getListAutres().remove(i);
    	}
    	try 
    	{
			MonAppli.getInscriptions().sauvegarder();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	clean();
    }
    
    /**
     * Permet de faire passer une équipe du candidat dans les autres équipes 
     */
    public void boutonEquipesVersAutres(){

    	ArrayList<Equipe> aEnlever = new ArrayList<>();
    	for(int i = 0; i < stageEquipes.getListEquipes().size(); i++){
    		if(checkBoxEquipeMembre.getCellData(i).booleanValue()){
    			stageEquipes.getListEquipes().get(i).remove(stageGestion.getPersonneActive());
    			aEnlever.add(stageEquipes.getListEquipes().get(i));
    			stageEquipes.getListAutres().add(stageEquipes.getListEquipes().get(i));
    			selectedRowListAutres.add(new SimpleBooleanProperty());
    		}
    	}
    	
    	for(Candidat i : aEnlever){
    		stageEquipes.getListEquipes().remove(i);
    	}
    	try 
    	{
			MonAppli.getInscriptions().sauvegarder();
		}
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	clean();
    }
}
