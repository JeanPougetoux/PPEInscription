package interfaceGraphique.controls.Competition;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GererMailController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cette fenêtre est celle qui gère l'envoie des mails aux candidats.
 * @author Jean
 *
 */
public class GererMail extends Stage
{
	private BorderPane rootLayout;
	private Competition compet;
	private Personne personne;
	private ObservableList<Candidat> listCandidatsCompet = FXCollections.observableArrayList();
	
	public GererMail(Competition compet) 
	{
		this.setResizable(false);
		this.setTitle("Envoie de mail groupé");
		this.initModality(Modality.APPLICATION_MODAL);
		this.compet = compet;
		initLayouts();
	}
	
	public GererMail(Personne personne) 
	{
		this.setResizable(false);
		this.setTitle("Envoie de mail personnel");
		this.initModality(Modality.APPLICATION_MODAL);
		this.personne = personne;
		initLayouts();
	}
	
	/**
	 * La méthode initLayouts permet de récupérer notre vue GererMail.fxml
	 * et appelle le controller qui lui est lié.
	 * Appelle bindLists();
	 */
	 public void initLayouts() {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(GererMail.class.getResource("../../view/Competition/GererMail.fxml"));
	            GridPane panelPrincipal = (GridPane) loader.load();
	            panelPrincipal.setStyle("-fx-background-color : " + MonAppli.COLORFENETRE + ";");
	            rootLayout = new BorderPane();
	            rootLayout.setPrefWidth(panelPrincipal.getWidth());
	            rootLayout.setPrefHeight(panelPrincipal.getHeight());
	            Scene scene = new Scene(rootLayout);
	            this.setScene(scene);
	            rootLayout.setCenter(panelPrincipal);
	            if(estCompetition())
	            	bindLists();
	            GererMailController controller = loader.getController();
	            controller.setClass(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	
	 
	/**
	 * @return la competition
	 */
	public Competition getCompet(){
		return compet;
	}
	
	/**
	 * Fabrique la liste des candidats auxquels il faut envoyer un mail.
	 */
    private void bindLists()
    {
    	if(compet.estEnEquipe())
    	{
			for(Candidat c : MonAppli.getInscriptions().getCandidats())
			{
				if(compet.getCandidats().contains(c) && c instanceof Equipe)
					listCandidatsCompet.add(c);
			}
    	}
    	else
    	{
    		for(Candidat c : MonAppli.getInscriptions().getCandidats())
    		{
    			if(compet.getCandidats().contains(c) && c instanceof Personne)
    				listCandidatsCompet.add(c);
    		}
    	}
	}
    
    /**
     * @return la liste des candidats.
     */
    public ObservableList<Candidat> getListCandidats(){
    	return listCandidatsCompet;
    } 
    
    public boolean estPersonnel()
    {
    	return personne != null;
    }
    public Personne getPersonne()
    {
    	return this.personne;
    }
    public boolean estCompetition()
    {
    	return compet != null;
    }
    
   
}
