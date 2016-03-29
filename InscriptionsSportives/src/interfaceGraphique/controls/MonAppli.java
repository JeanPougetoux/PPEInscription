package interfaceGraphique.controls;


import inscriptions.Inscriptions;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MonAppli extends Application{

	public static final String COLORFENETRE = "#DCDCDC";
	private static Inscriptions inscriptions = Inscriptions.getInscriptions();

	public static void lancer(String[] args){
		launch(args);	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Accueil mafenetre = new Accueil();
		mafenetre.show();
//		ConnexionSecurisee fenetre = new ConnexionSecurisee();
//		fenetre.show();
	}
	
	public static Inscriptions getInscriptions(){
		return inscriptions;
	}
	
	public static void generationErreur(String message)
	{
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Attention");
        alert.setContentText(message);
        
        alert.showAndWait();
	}
}
