package interfaceGraphique.mainWindow;


import javafx.application.Application;
import javafx.stage.Stage;

public class MonAppli extends Application{

	public static void lancer(String[] args){
		launch(args);		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainWindow mafenetre = new MainWindow();
		mafenetre.show();
	}
}
