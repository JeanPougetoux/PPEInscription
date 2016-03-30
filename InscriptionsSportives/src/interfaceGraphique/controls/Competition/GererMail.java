package interfaceGraphique.controls.Competition;

import java.io.IOException;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.view.Competition.GererCandidatsController;
import interfaceGraphique.view.Competition.GererMailController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GererMail extends Stage
{
	private BorderPane rootLayout;
	private Competition compet;
	private ObservableList<Candidat> listCandidatsCompet = FXCollections.observableArrayList();
	
	public GererMail(Competition compet) {
		this.setResizable(false);
		this.setTitle("Envoie de mail groupé");
		this.initModality(Modality.APPLICATION_MODAL);
		this.compet = compet;
		initLayouts();
	}
	
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
	            bindLists();
	            GererMailController controller = loader.getController();
	            controller.setClass(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }	
	 
	public Competition getCompet(){
		return compet;
	}
	
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
    
    public ObservableList<Candidat> getListCandidats(){
    	return listCandidatsCompet;
    } 
    
   
}
