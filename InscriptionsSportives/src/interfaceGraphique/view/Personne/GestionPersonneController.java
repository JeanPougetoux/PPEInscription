package interfaceGraphique.view.Personne;

import java.io.IOException;

import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Personne.AjoutPersonne;
import interfaceGraphique.controls.Personne.GestionPersonne;
import interfaceGraphique.controls.Personne.ModificationPersonne;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GestionPersonneController {
	
	@FXML
    private TableColumn<Personne, String> nameColumn;
	@FXML
    private TableColumn<Personne, String> prenomColumn;
	@FXML
    private TableColumn<Personne, String> mailColumn;
	@FXML
	private Label information;
	
	@FXML
    private Label labelPersonne;
    private GestionPersonne stageGestion;
    private Personne personneActive;
    
    @FXML
    private TableView<Personne> personneTable = new TableView<Personne>();
    
    @FXML 
    private Button ajoutPersonne;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;

	 public GestionPersonneController(){
	    	
	    }
	    
	    @FXML
	    private void initialize()
	    {
	    	nameColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
					getValue().getNom()));
	    	prenomColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
					getValue().getPrenom()));
	    	mailColumn.setCellValueFactory(CellDataFeatures -> new ReadOnlyStringWrapper(CellDataFeatures.
	    			getValue().getMail()));
	    	setChoixVisibility(false);
	    	this.information.setVisible(false);
	    }
	    
	    public void setClass(GestionPersonne stageGestion){
	    	this.stageGestion = stageGestion;
	    	personneTable.setItems(stageGestion.getList());
	    	personneTable.setOnMouseClicked(new ActionClickTable(this));
	    }
	    
	    public void setChoixVisibility(boolean visible)
	    {
	    	labelPersonne.setVisible(visible);
	    	supprimer.setVisible(visible);
	    	modifier.setVisible(visible);
	    }   
	    
	    public TableView<Personne> getTable(){
	    	return personneTable;
	    }
	    
	    public void setNomPersonne(String texte){
	    	labelPersonne.setText(texte);
	    }
	    public void setPersonneActive(Personne personne){
	    	personneActive = personne;   
	    }
	    
	    
	    public void actionBoutonAjout()
	    {
	    	AjoutPersonne fenetre = new AjoutPersonne(stageGestion);
	    	fenetre.show();
	    }
	    
	    public void actionBoutonSupprimer()
	    {
	    	ModaleSuppression modaleSupp = new ModaleSuppression(this);
	    	modaleSupp.show();
	    }
	    
	    public void actionBoutonModifier()
	    {
	    	ModificationPersonne fenetre = new ModificationPersonne(stageGestion,personneActive.getMail(),this);
	    	fenetre.show();
	    }

		public void deleteElement(){    	
		stageGestion.getList().remove(personneActive);
		try 
		{
			personneActive.delete();
		}
		catch (ExceptionRetraitPersonneEquipe e1) 
		{
			// TODO Auto-generated catch block
			generationInfos(e1.toString(),"infos");
			System.out.println(e1.toString());
		}
		
    	try 
    	{
			MonAppli.getInscriptions().sauvegarder();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
		
		
		public void generationInfos(String message,String type)
		{
	    	if(type == "erreur")
	    		this.information.setTextFill(Color.web("#FF0000"));
	    	else
	    		this.information.setTextFill(Color.web("green"));
	    	this.information.setText(message);
	    	
			this.information.setVisible(true);
		}
		
		public void actualise()
		{
			this.personneTable.refresh();
			setPersonneActive(null);
			setChoixVisibility(false);
		}
	    

}

class ActionClickTable implements EventHandler<MouseEvent>{

	private GestionPersonneController stageGestion;
	public ActionClickTable(GestionPersonneController stageGestion) {
		this.stageGestion = stageGestion;
	}

	@Override
	public void handle(MouseEvent event) {
		Personne personne = stageGestion.getTable().getSelectionModel().getSelectedItem();
		stageGestion.setChoixVisibility(true);
		stageGestion.setNomPersonne(personne.getNom());
		stageGestion.setPersonneActive(personne);
	}
	
}
