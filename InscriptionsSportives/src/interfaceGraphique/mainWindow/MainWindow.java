package interfaceGraphique.mainWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainWindow extends Stage{
	public MainWindow(){
		this.setTitle("Bienvenue");
		this.setResizable(false);
        Scene scene = new Scene(returnGrid(), 700, 400);
        this.setScene(scene);
	}
	
	public GridPane returnGrid(){
		GridPane grid = new GridPane();
		grid.setHgap(10);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);
        grid.add(returnBienvenue(), 0, 0);
        grid.add(returnGridButtons(), 0, 1);
        return grid;
	}
	
	public GridPane returnGridButtons(){
		GridPane grid = new GridPane();
		grid.setHgap(10);
        grid.setVgap(17);
		grid.setAlignment(Pos.CENTER);
		grid.add(returnCompetition(), 0, 1);
        grid.add(returnEquipe(), 0, 2);
        grid.add(returnPersonne(), 0, 3);
		return grid;
	}
	
	public Label returnBienvenue(){
		Label texteBienvenue = new Label("Bienvenue dans l'application\nde gestion"
					+ " de compétitions M2L");
		texteBienvenue.setFont(new Font("Arial", 35));
		texteBienvenue.setTextAlignment(TextAlignment.CENTER);
		texteBienvenue.setPadding(new Insets(0, 0, 15, 0));
		return texteBienvenue;
	}
	
	public Button returnCompetition(){
		Button btn = new Button("COMPETITIONS");
		btn.setMinWidth(160);
		btn.setMinHeight(50);
		return btn;
	}
	
	public Button returnEquipe(){
		Button btn = new Button("EQUIPES");
		btn.setMinWidth(160);
		btn.setMinHeight(50);
		return btn;
	}

	public Button returnPersonne(){
		Button btn = new Button("PERSONNES");
		btn.setMinWidth(160);
		btn.setMinHeight(50);
		return btn;
	}
}
