package interfaceGraphique.view.Personne;

import java.io.IOException;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.ModaleSuppression;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Personne.AjoutPersonne;
import interfaceGraphique.controls.Personne.GestionEquipes;
import interfaceGraphique.controls.Personne.GestionPersonne;
import interfaceGraphique.controls.Personne.ModificationPersonne;
import interfaceGraphique.controls.Personne.VueCompetitions;
import interfaceGraphique.view.Equipe.GestionEquipeController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
* Controller de la vue GestionPersonne.fxml
* IL est appellé par la classe GestionPersonne.
* @author thomas
*/
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
    private Button gererEquipes;
    @FXML
    private Button voirCompetitions;
    @FXML 
    private Button ajoutPersonne;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;

	 public GestionPersonneController(){
	    	
	    }
	    
	 /**
	  * Initialise la fenêtre
	  */
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
	    
	    /**
	     * Gère la visibilité des boutons
	     * @param visible
	     */
	    public void setChoixVisibility(boolean visible)
	    {
	    	labelPersonne.setVisible(visible);
	    	supprimer.setVisible(visible);
	    	modifier.setVisible(visible);
	    	gererEquipes.setVisible(visible);
	    	voirCompetitions.setVisible(visible);
	    	
	    }   
	    
	    /**
	     * récupère l'ensemble des personnes
	     * @return
	     */
	    public TableView<Personne> getTable(){
	    	return personneTable;
	    }
	    
	    /**
	     * Modifie le nom dans le label
	     * @param texte
	     */
	    public void setNomPersonne(String texte){
	    	labelPersonne.setText(texte);
	    }
	    
	    /**
	     * permet de désigner une nouvelle personne active
	     * @param personne
	     */
	    public void setPersonneActive(Personne personne){
	    	personneActive = personne;   
	    }
	    
	    /**
	     * Réxcupère la personne active
	     * @return
	     */
	    public Personne getPersonneActive(){
	    	return personneActive;
	    }
	    
	    /**
	     * Ouvre une fenêtre de gestion d'équipes lorsqu'on clique sur le bouton
	     */
	    public void actionBoutonGererEquipes(){
	    	GestionEquipes fenetreGestion = new GestionEquipes(this);
	    	fenetreGestion.show();
	    }
	    
	    /**
	     * Ouvre une fenêtre d'ajout de perosnne lorsqu'on clique sur le bouton
	     */
	    public void actionBoutonAjout()
	    {
	    	AjoutPersonne fenetre = new AjoutPersonne(stageGestion);
	    	fenetre.show();
	    }
	    
	    /**
	     * Ouvre une fenêtre de validation de suppression puis supprime ou non la personne lorsqu'on clique sur le bouton
	     */
	    public void actionBoutonSupprimer()
	    {
	    	ModaleSuppression modaleSupp = new ModaleSuppression(this);
	    	modaleSupp.show();
	    }
	    
	    /**
	     * Ouvre une fenêtre de modification de personne lorsqu'on clique sur le bouton
	     */
	    public void actionBoutonModifier()
	    {
	    	ModificationPersonne fenetre = new ModificationPersonne(stageGestion,personneActive.getMail(),this);
	    	fenetre.show();
	    }
	    
	    /**
	     * Ouvre une fenêtre de vue des compétitions lorsqu'on clique sur le bouton
	     */
	    public void actionBoutonVoirCompetitions()
	    {
	    	if(!personneActive.getCompetitions().isEmpty())
	    	{
	    		VueCompetitions fenetre = new VueCompetitions(this);
		    	fenetre.show();
	    	}
	    	else
	    	{
	    		GestionDesErreurs.afficherMessage(information,"Cette personne ne participe à aucune compétitions","erreur");
	    	}
	    	
	    	
	    }
	    /**
	     * Supprime la personne active
	     */
		public void deleteElement(){   
			
		stageGestion.getList().remove(personneActive);
		
		
		personneActive.delete();
			
			
		
		
		
    	try 
    	{
			MonAppli.getInscriptions().sauvegarder();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	setPersonneActive(null);
    	setChoixVisibility(false);
    	
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
