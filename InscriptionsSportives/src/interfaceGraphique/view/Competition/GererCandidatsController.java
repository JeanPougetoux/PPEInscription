package interfaceGraphique.view.Competition;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.ExceptionAjoutPersonneCompetition;
import exceptions.ExceptionNomEquipe;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Competition.GererCandidats;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
    private TableColumn<Candidat, Boolean> checkBoxAutresCandidats;
    @FXML
    private Button candidatVersAutres;
    @FXML
    private Button autresVersCandidats;
    private GererCandidats stage;
    private GestionCompetitionsController stageGestion;
    private ArrayList<BooleanProperty> selectedRowList = new ArrayList<BooleanProperty>();
    
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
    	new Callback<CellDataFeatures<Candidat,Boolean>,ObservableValue<Boolean>>()
    	{
    	    @Override
    	    public ObservableValue<Boolean> call(CellDataFeatures<Candidat,Boolean> cdf)
    	    {
    	        TableView<Candidat> tblView = cdf.getTableView();

    	        Candidat rowData = cdf.getValue();

    	        int rowIndex = tblView.getItems().indexOf( rowData );

    	        return selectedRowList.get( rowIndex );
    	    }
    	});
    	checkBoxAutresCandidats.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxAutresCandidats));
    }
    
    public void setClass(GererCandidats stage, GestionCompetitionsController stageGestion){
    	this.stage = stage;
    	this.stageGestion = stageGestion;
    	for(Candidat p : stage.getListAutresCandidats()){
    		selectedRowList.add(new SimpleBooleanProperty());
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
    	autresVersCandidats.setOnAction(buttonAutreVersCandidat());
    }
    
    public void setVisibility(boolean candidatsVersAutres, boolean autreVersCandidats){
    	candidatVersAutres.setVisible(candidatsVersAutres);
    	autresVersCandidats.setVisible(autreVersCandidats);
    }
    
    public EventHandler<ActionEvent> buttonAutreVersCandidat(){
    	return new EventHandler<ActionEvent>() {
//    	for(int i = 0; i < stage.getListAutresCandidats().size(); i++){
//    		if(checkBoxAutresCandidats.getCellData(i).booleanValue()){
////    			if(stage.getListAutresCandidats().get(i) instanceof Personne){
////					try {
////						stage.getCompet().add((Personne)(stage.getListAutresCandidats().get(i)));
////					} catch (ExceptionAjoutPersonneCompetition e1) {
////						// TODO Auto-generated catch block
////						e1.printStackTrace();
////					}
////    			}
////    			if(stage.getListAutresCandidats().get(i) instanceof Equipe){
////    				for(Competition c : MonAppli.getInscriptions().getCompetitions()){
////    					
////    				}
////    			}
//    				
////    			stage.getListAutresCandidats().remove(i);
//    			try {
//					MonAppli.getInscriptions().sauvegarder();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//    		}
//    	}
		@Override
		public void handle(ActionEvent event) {
			stageGestion.addElementToCompet();
		}
    };
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
}