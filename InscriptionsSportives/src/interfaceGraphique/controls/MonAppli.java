package interfaceGraphique.controls;


import inscriptions.Inscriptions;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Point d'entrée de notre application.
 * Hérite de Application.
 * C'est la méthode lancer qui est appellée à l'extérieur
 * et qui appelle la méthode start() qui lance notre première fenêtre.
 * @author Jean
 *
 */
public class MonAppli extends Application{

	public static final String COLORFENETRE = "#DCDCDC";
	private static Inscriptions inscriptions = Inscriptions.getInscriptions();

	/**
	 * Appelle start();
	 * @param args
	 */
	public static void lancer(String[] args){
		launch(args);	
	}
	
	/**
	 * Méthode start prend en paramètre un stage mais ne l'utilise pas.
	 * Elle instance une nouvelle fenêtre ConnexionSecurisee et la lance.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Accueil mafenetre = new Accueil();
		mafenetre.show();
//		ConnexionSecurisee fenetre = new ConnexionSecurisee();
//		fenetre.show();
	}
	
	/**
	 * @return la variable de type Inscriptions qui contient nos données.
	 */
	public static Inscriptions getInscriptions(){
		return inscriptions;
	}
}
