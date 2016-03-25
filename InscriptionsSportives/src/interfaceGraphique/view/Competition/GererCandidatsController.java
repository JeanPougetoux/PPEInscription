package interfaceGraphique.view.Competition;

import inscriptions.Candidat;
import inscriptions.Personne;
import interfaceGraphique.controls.Competition.GererCandidats;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
    private TableColumn<Candidat, CheckBox> checkBoxCandidatsCompet;
	@FXML
    private TableView<Candidat> autreCandidats = new TableView<Candidat>();
    @FXML
    private TableColumn<Candidat, String> nameAutresCandidats;
    @FXML
    private TableColumn<Candidat, String> prenomAutresCandidats;
    @FXML
    private TableColumn<Candidat, CheckBox> checkBoxAutresCandidats;
    @FXML
    private Button candidatVersAutres;
    @FXML
    private Button autresVersCandidats;
    private GererCandidats stage;
    
    public GererCandidatsController(){
    	
    }
    
    @FXML
    private void initialize(){
    	nameCandidatsCompet.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(
    			CellDataFeatures.getValue().getNom()));

    	nameAutresCandidats.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(
    			CellDataFeatures.getValue().getNom()));
    	checkBoxCandidatsCompet.setCellFactory(tc -> new CheckBoxTableCell<>());
    	checkBoxAutresCandidats.setCellValueFactory(
    			new Callback<CellDataFeatures<Candidat, CheckBox>, ObservableValue<CheckBox>>() {

             @Override
             public ObservableValue<CheckBox> call(
                     CellDataFeatures<Candidat, CheckBox> arg0) {
                 Candidat candidat = arg0.getValue();
                 CheckBox checkBox = new CheckBox();
                 
                 return new SimpleObjectProperty<CheckBox>(checkBox);
             }
         
         
         });
//    	checkBoxAutresCandidats.setCellFactory(tc -> new CheckBoxTableCell<>());
    }
    
    public void setClass(GererCandidats stage){
    	this.stage = stage;
    	if(stage.getCompet().estEnEquipe()){
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
    	System.out.println("okok");
    	for(int i = 0; i < autreCandidats.getItems().size(); i++){
    	}
		System.out.println(autreCandidats.getSelectionModel().getSelectedItem());

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
