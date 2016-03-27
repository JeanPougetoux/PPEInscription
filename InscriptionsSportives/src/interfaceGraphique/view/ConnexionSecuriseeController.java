package interfaceGraphique.view;

import java.awt.TextField;

import javax.swing.JTextField;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import donnees.persistance;
import interfaceGraphique.controls.Accueil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Window;

public class ConnexionSecuriseeController
{

	
	
	@FXML
	private PasswordField password;
	
	
	
	
	
	public ConnexionSecuriseeController() 
	{
	}
	
	@FXML
	private void initialize()
	{

	}
	
	
	public void handleConnexion()
	{
		/*if(this.utilisateur.toString() != "" && this.password.toString() != "")
			if(persistance.estConnecte(utilisateur.toString(), password.toString()))
				generationErreur("BRAVO");
			else
				generationErreur("Nom de compte ou mot de passe incorrect");		
		else
			generationErreur("Veuillez renseigner tous les champs");*/
		if(persistance.estConnecte(password.getText()))
		{
			Accueil mafenetre = new Accueil();
			mafenetre.show();
		}
		else
		{
			generationErreur("Mot de passe incorrect");
		}
      
	}
	
	public void generationErreur(String message)
	{
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenur lors de la connexion");
        alert.setContentText(message);
        
        alert.showAndWait();
	}
	
	
}
