package interfaceGraphique.view.Personne;

import java.io.IOException;

import exceptions.ExceptionMailPersonne;
import exceptions.ExceptionNomEquipe;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Equipe.AjoutEquipe;
import interfaceGraphique.controls.Equipe.GestionEquipe;
import interfaceGraphique.controls.Personne.AjoutPersonne;
import interfaceGraphique.controls.Personne.GestionPersonne;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AjoutPersonneController {

	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
	@FXML
	private TextField mail;
	
	@FXML
	private Label information;
	
	private AjoutPersonne stageAjout;
	private GestionPersonne stageGestion;
	private boolean erreurActuelle;
	
	public AjoutPersonneController() {
		// TODO Auto-generated constructor stub
	}
	
	public void setStage(AjoutPersonne stageAjout, GestionPersonne stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
		this.information.setVisible(false);
	}
	
	public void actionClose(){
		stageAjout.close();
	}
	
	
	public void actionAjout()
	{
		if(!nom.getText().isEmpty() && !prenom.getText().isEmpty() && !mail.getText().isEmpty())
		{
			this.erreurActuelle = false;
			try 
			{
				stageGestion.getList().add(MonAppli.getInscriptions().
						createPersonne(nom.getText(), prenom.getText(), mail.getText()));
				
				
			} 
			catch (ExceptionMailPersonne e) 
			{
				// TODO Auto-generated catch block
				generationInfos(e.toString(),"erreur");
				this.erreurActuelle = true;
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
			if(!erreurActuelle)
				stageAjout.close();
		}
		else
		{
			generationInfos("Veuillez renseigner tous les champs","erreur");
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
}
