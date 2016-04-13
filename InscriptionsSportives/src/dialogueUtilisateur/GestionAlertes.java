package dialogueUtilisateur;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GestionAlertes
{


	public GestionAlertes()
	{
		
	}
	
	public static Alert afficherAlerte(String titre, String message)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titre);
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert;
	}
	
	
}
