package interfaceGraphique.view.Personne;

import java.io.IOException;

import exceptions.ExceptionMailPersonne;
import inscriptions.Candidat;
import inscriptions.Personne;
import interfaceGraphique.controls.MonAppli;
import interfaceGraphique.controls.Personne.AjoutPersonne;
import interfaceGraphique.controls.Personne.GestionPersonne;
import interfaceGraphique.controls.Personne.ModificationPersonne;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ModificationPersonneController {

	private String mailActuel;
	private boolean erreurActuelle;
	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
	@FXML
	private TextField mail;
	
	@FXML
	private Label information;
	
	private ModificationPersonne stageAjout;
	private GestionPersonne stageGestion;
	
	public ModificationPersonneController() {
		// TODO Auto-generated constructor stub
	}
	
	public void setStage(ModificationPersonne stageAjout, GestionPersonne stageGestion,String mail){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
		this.information.setVisible(false);
		this.mailActuel = mail;
	}
	
	public void actionClose(){
		stageAjout.close();
	}
	
	
	public void actionModifier()
	{	
		this.erreurActuelle = false;
		if(!nom.getText().isEmpty())
		{
			for (Candidat c : MonAppli.getInscriptions().getCandidats())
			{
				if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
					c.setNom(nom.getText());	
			}
		}
		if(!prenom.getText().isEmpty())
		{
			for (Candidat c : MonAppli.getInscriptions().getCandidats())
			{
				if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
					((Personne)c).setPrenom(prenom.getText());
			}
		}
		if(!mail.getText().isEmpty())
		{
			for (Candidat c : MonAppli.getInscriptions().getCandidats())
			{
				if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
					try 
				{
					((Personne)c).setMail(mail.getText());
				} 
				catch (ExceptionMailPersonne e) 
				{
						this.erreurActuelle = true;
						generationInfos(e.toString(),"erreur");
				}
			}
		}
		if(!this.erreurActuelle)
			stageAjout.close();
		
			
		
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
