package interfaceGraphique.view.Personne;

import java.io.IOException;

import dialogueUtilisateur.GestionDesErreurs;
import exceptions.ExceptionMailPersonne;
import exceptions.ExceptionNomEquipe;
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
	private GestionPersonneController stageGestionController;
	
	public ModificationPersonneController() {
		// TODO Auto-generated constructor stub
	}
	
	public void setStage(ModificationPersonne stageAjout, GestionPersonne stageGestion,String mail,GestionPersonneController stageGestionController){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
		this.information.setVisible(false);
		this.mailActuel = mail;
		this.stageGestionController = stageGestionController;
		nom.setText(stageGestionController.getPersonneActive().getNom());
		prenom.setText(stageGestionController.getPersonneActive().getPrenom());
		this.mail.setText(stageGestionController.getPersonneActive().getMail());
	}
	
	public void actionClose(){
		stageAjout.close();
	}
	
	
	public void actionModifier()
	{	
		this.erreurActuelle = false;
		if(!nom.getText().isEmpty())
		{
			if(!nom.getText().equals(stageGestionController.getPersonneActive().getNom()))
			{
				for (Candidat c : MonAppli.getInscriptions().getCandidats())
				{
					if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
					{
						try 
						{
							c.setNom(nom.getText());
						} 
						catch (ExceptionNomEquipe e) 
						{
							GestionDesErreurs.afficherMessage(information,e.toString(),"erreur");
						}
					}
							
				}
			}
			
		}
		else
		{
			this.erreurActuelle = true;
			GestionDesErreurs.afficherMessage(information, "Veuillez remplit tous les champs, ancien nom : "+
					stageGestionController.getPersonneActive().getNom(), "erreur");
		}
			
		if(!prenom.getText().isEmpty())
		{
			if(!prenom.getText().equals(stageGestionController.getPersonneActive().getPrenom()))
			{
				for (Candidat c : MonAppli.getInscriptions().getCandidats())
				{
					if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
						((Personne)c).setPrenom(prenom.getText());
				}
			}
			
		}
		else
		{
			this.erreurActuelle = true;
			GestionDesErreurs.afficherMessage(information,"Veuillez remplit tous les champs, ancien prénom : "+
					stageGestionController.getPersonneActive().getPrenom(),"erreur");
		}
		if(!mail.getText().isEmpty())
		{
			if(!mail.getText().equals(stageGestionController.getPersonneActive().getMail().toLowerCase()))
			{
				for (Candidat c : MonAppli.getInscriptions().getCandidats())
				{
					if (c instanceof Personne && ((Personne) c).getMail()== this.mailActuel)
						try 
					{
						((Personne)c).setMail(mail.getText().toLowerCase());
					} 
					catch (ExceptionMailPersonne e) 
					{
							this.erreurActuelle = true;
							GestionDesErreurs.afficherMessage(information,e.toString(),"erreur");
					}
				}
			}
			
		}
		else
		{
			this.erreurActuelle = true;
			GestionDesErreurs.afficherMessage(information,"Veuillez remplit tous les champs, ancien mail : "+
					stageGestionController.getPersonneActive().getMail(),"erreur");
		}
		if(!this.erreurActuelle)
		{
			stageAjout.close();
			stageGestionController.actualise();
		}
			
		
			
		
	}
}
