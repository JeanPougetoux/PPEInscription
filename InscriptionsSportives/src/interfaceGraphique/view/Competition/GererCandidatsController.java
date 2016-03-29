package interfaceGraphique.view.Competition;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import inscriptions.Candidat;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.GererCandidats;
import interfaceGraphique.controls.Competition.GererMail;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class GererCandidatsController {


	@FXML
    private TableView<Candidat> candidatsCompetition = new TableView<Candidat>();
    @FXML
    private TableColumn<Candidat, String> nameCandidatsCompet;
    @FXML
    private TableColumn<Candidat, String> prenomCandidatsCompet;
    @FXML
    private TableColumn<Candidat, Boolean> checkBoxCandidatsCompet;
	@FXML
    private TableView<Candidat> autreCandidats = new TableView<Candidat>();
    @FXML
    private TableColumn<Candidat, String> nameAutresCandidats;
    @FXML
    private TableColumn<Candidat, String> prenomAutresCandidats;
    @FXML
    private TableColumn<Candidat, Boolean> checkBoxAutresCandidats;
    @FXML
    private Button candidatVersAutres;
    @FXML
    private Button autresVersCandidats;
    private GererCandidats stage;
    private GestionCompetitionsController stageGestion;
    private ArrayList<BooleanProperty> selectedRowListAutres = new ArrayList<BooleanProperty>();
    private ArrayList<BooleanProperty> selectedRowListCandidats = new ArrayList<BooleanProperty>();

    
    public GererCandidatsController(){
    	
    }
    
    @FXML
    private void initialize(){
    	nameCandidatsCompet.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(
    			CellDataFeatures.getValue().getNom()));

    	nameAutresCandidats.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(
    			CellDataFeatures.getValue().getNom()));
    	checkBoxCandidatsCompet.setCellValueFactory(
    	new Callback<CellDataFeatures<Candidat,Boolean>,ObservableValue<Boolean>>()
    	{
    	    @Override
    	    public ObservableValue<Boolean> call(CellDataFeatures<Candidat,Boolean> cdf)
    	    {
    	        TableView<Candidat> tblView = cdf.getTableView();

    	        Candidat rowData = cdf.getValue();

    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	        return selectedRowListCandidats.get( rowIndex );
    	    }
    	});
    	checkBoxAutresCandidats.setCellValueFactory(
    	new Callback<CellDataFeatures<Candidat,Boolean>,ObservableValue<Boolean>>()
    	{
    	    @Override
    	    public ObservableValue<Boolean> call(CellDataFeatures<Candidat,Boolean> cdf)
    	    {
    	        TableView<Candidat> tblView = cdf.getTableView();

    	        Candidat rowData = cdf.getValue();

    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	        return selectedRowListAutres.get( rowIndex );
    	    }
    	});
    	checkBoxAutresCandidats.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxAutresCandidats));
    	checkBoxCandidatsCompet.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxCandidatsCompet));
    }
    
    public void setClass(GererCandidats stage, GestionCompetitionsController stageGestion){
    	this.stageGestion = stageGestion;
    	this.stage = stage;
    	for(Candidat p : stage.getListAutresCandidats()){
    		selectedRowListAutres.add(new SimpleBooleanProperty());
    	}
    	for(Candidat p : stage.getListCandidats()){
    		selectedRowListCandidats.add(new SimpleBooleanProperty());
    	}
    	if(stageGestion.getCompetitionActive().estEnEquipe()){
    		prenomCandidatsCompet.setVisible(false);
    		prenomAutresCandidats.setVisible(false);
    		nameCandidatsCompet.setPrefWidth(220);
    		nameCandidatsCompet.setMinWidth(220);
    		nameCandidatsCompet.setMaxWidth(220);
    		nameAutresCandidats.setPrefWidth(220);
    		nameAutresCandidats.setMinWidth(220);
    		nameAutresCandidats.setMaxWidth(220);
    	}
    	else{
    		prenomCandidatsCompet.setCellValueFactory(CellDataFeatures -> 
    		new ReadOnlyStringWrapper(((Personne)CellDataFeatures.getValue()).getPrenom()));

        	prenomAutresCandidats.setCellValueFactory(CellDataFeatures -> 
        	new ReadOnlyStringWrapper(((Personne)CellDataFeatures.getValue()).getPrenom()));
    	}
    	candidatsCompetition.setItems(stage.getListCandidats());
    	autreCandidats.setItems(stage.getListAutresCandidats());
    }
    
    public void setVisibility(boolean candidatsVersAutres, boolean autreVersCandidats){
    	candidatVersAutres.setVisible(candidatsVersAutres);
    	autresVersCandidats.setVisible(autreVersCandidats);
    }
    
    public void buttonAutreVersCandidat(){
    	ArrayList<Integer> aEnlever = new ArrayList<Integer>();
    	for(int i = 0; i < stage.getListAutresCandidats().size(); i++){
    		if(checkBoxAutresCandidats.getCellData(i).booleanValue()){
    			try {
					stageGestion.addElementToCompet(stage.getListAutresCandidats().get(i));
					aEnlever.add(i);
					stage.getListCandidats().add(stage.getListAutresCandidats().get(i));
					selectedRowListCandidats.add(new SimpleBooleanProperty());
				} catch (Exception e) {
					System.out.println("Erreur pas de membres dans l'équipe " + stage.getListAutresCandidats().get(i).toString());
				}
    		}
    	}
    	for(int i : aEnlever){
    		stage.getListAutresCandidats().remove(i);
    		selectedRowListAutres.get(i).set(false);
    	}
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void buttonCandidatVersAutre(){
    	for(int i = 0; i < stage.getListCandidats().size(); i++){
			if(checkBoxCandidatsCompet.getCellData(i).booleanValue()){
					stageGestion.removeElementOfCompet(stage.getListCandidats().get(i));
					stage.getListAutresCandidats().add(stage.getListCandidats().get(i));
					stage.getListCandidats().remove(i);
					selectedRowListCandidats.remove(i);
					selectedRowListAutres.add(new SimpleBooleanProperty());
			}
    	}
    	try {
			MonAppli.getInscriptions().sauvegarder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void handleMail()
    {
    	if(!stageGestion.getCompetitionActive().getCandidats().isEmpty())
    	{
    		GererMail fenetre = new GererMail(stageGestion.getCompetitionActive());
        	fenetre.show();
    	}
    	else
    	{
    		generationErreur("Cette compétition est vide, action impossible");
    	}
    	
    }
    public void generationErreur(String message)
	{
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Attention");
        alert.setContentText(message);
        
        alert.showAndWait();
	}
}


class ActionClickTableCandidats implements EventHandler<ActionEvent>{

	private GestionCompetitionsController stageGestion;
	public ActionClickTableCandidats() {
	}
	
	@Override
	public void handle(ActionEvent event) {
		System.out.println("le clic marche bien");		
	}
}