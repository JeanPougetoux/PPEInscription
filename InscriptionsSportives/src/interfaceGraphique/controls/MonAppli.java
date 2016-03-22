package interfaceGraphique.controls;


import inscriptions.Inscriptions;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonAppli extends Application{

	public static final String COLORFENETRE = "#8A2BE2";
	private static Inscriptions inscriptions = Inscriptions.getInscriptions();

	public static void lancer(String[] args){
		launch(args);	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Accueil mafenetre = new Accueil();
		mafenetre.show();
	}
	
	public static Inscriptions getInscriptions(){
		return inscriptions;
	}
}
