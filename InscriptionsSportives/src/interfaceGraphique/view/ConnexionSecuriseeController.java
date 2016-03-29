package interfaceGraphique.view;

import java.awt.TextField;

import javax.swing.JTextField;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import donnees.persistance;
import interfaceGraphique.controls.Accueil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Window;

public class ConnexionSecuriseeController
{

	
	
	@FXML
	private PasswordField password;
	
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
	
	
	public void handleConnexion()
	{
		if(persistance.estConnecte(password.getText()))
		{
			Accueil mafenetre = new Accueil();
			mafenetre.show();
		}
		else
		{
			generationInfos("Mot de passe incorrect","erreur");
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
