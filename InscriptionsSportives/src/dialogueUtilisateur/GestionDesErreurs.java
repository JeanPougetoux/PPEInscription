package dialogueUtilisateur;



import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GestionDesErreurs 
{
	
	
	public GestionDesErreurs() 
	{
		
	}
	
	/**
	 * Cette méthode permet de gérer l'affichage des erreurs et des informations absolument partout dans le code.
	 * Elle affiche le label et lui donne une couleur appropriee.
	 * @param label
	 * @param message
	 * @param erreur
	 * @return
	 */
	public static Label afficherMessage(Label label,String message,String erreur)
	{
		if(erreur == "erreur")
    		label.setTextFill(Color.web("#FF0000"));
    	else
    		label.setTextFill(Color.web("green"));
    	label.setText(message);
		label.setVisible(true);
		return label;

	}
}
