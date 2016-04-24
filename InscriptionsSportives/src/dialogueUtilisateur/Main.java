package dialogueUtilisateur;

import donnees.persistance;
import interfaceGraphique.controls.MonAppli;

/**
 * Point d'entrée de l'application, décide du type de vue ainsi que de la manière dont
 * les données vont persister.
 * @author Jean
 */
public class Main {
	public static final int SERIALIZATION = 0,
							BDD = 1,
							CONSOLE = 2,
							GRAPHIQUE = 3;

	public static int choixDialogue = GRAPHIQUE;
	public static int choixPersistance = BDD;
	
	public static void main(String[] args) {
		persistance.URLFINALE = persistance.URLLOCALE;
		
		persistance.USER = "root";
		persistance.PASS = "";

		if(choixDialogue == CONSOLE){
			new MenuDialogueConsole().start();
		}
		else if(choixDialogue == GRAPHIQUE){
			MonAppli.lancer(args);
		}
	}

}
