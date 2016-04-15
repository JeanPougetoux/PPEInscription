package interfaceGraphique.view;





import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import dialogueUtilisateur.GestionDesErreurs;
import donnees.persistance;
import interfaceGraphique.controls.Accueil;
import interfaceGraphique.controls.ConnexionSecurisee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 * Controller de la vue ConnexionSecurisee.fxml
 * et appellé par la classe ConnexionSecurisee.
 * @author Jean
 *
 */
public class ConnexionSecuriseeController
{

	ConnexionSecurisee stageGestion;
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField utilisateur;
	
	@FXML
	private Label information;
	
	public ConnexionSecuriseeController() 
	{
	}
	
	@FXML
	private void initialize()
	{
		this.information.setVisible(false);
	}
	
	/**
	 * Ouvre la fenêtre d'accueil si le mot de passe correspond 
	 */
	public void handleConnexion()
	{
		if(!password.getText().isEmpty() && !utilisateur.getText().isEmpty())
		{
			if(persistance.estConnecte(utilisateur.getText(),password.getText()))
			{
				Accueil mafenetre = new Accueil();
				mafenetre.show();
				stageGestion.close();
			}
			else
			{
				GestionDesErreurs.afficherMessage(information, "Nom de compte ou mot de passe incorrect", "erreur");
				//generationInfos("Mot de passe incorrect","erreur");
			}
		}
		else
			GestionDesErreurs.afficherMessage(information, "Tous les champs doivent être renseignés", "erreur");
			
		
      
	}
	public void setStage(ConnexionSecurisee connexionSecurisee) 
	{
		this.stageGestion = connexionSecurisee;
		
	}
	
	
}
