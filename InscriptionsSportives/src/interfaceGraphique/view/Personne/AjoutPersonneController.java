package interfaceGraphique.view.Personne;

import java.io.IOException;

import dialogueUtilisateur.GestionDesErreurs;
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

/**
* Controller de la vue AjoutPersonne.fxml
* IL est appellé par la classe AjoutPersonne.
* @author thomas
*
*/
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
	
	/**
	 * Mise en place des différents champs
	 * @param stageAjout
	 * @param stageGestion
	 */
	public void setStage(AjoutPersonne stageAjout, GestionPersonne stageGestion){
		this.stageAjout = stageAjout;
		this.stageGestion = stageGestion;
		this.information.setVisible(false);
	}
	
	/**
	 * Fermeture de la fenêtre
	 */
	public void actionClose(){
		stageAjout.close();
	}
	
	/**
	 * Boutton "ajout", permet d'ajourer une personne ou d'afficher un message d'erreur
	 */
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
				GestionDesErreurs.afficherMessage(information,e.toString(),"erreur");
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
			GestionDesErreurs.afficherMessage(information,"Veuillez renseigner tous les champs","erreur");
		}	
	}
}
