package dialogueUtilisateur;


public class Main {
	
	public static final int SERIALIZATION = 0,
							BDD = 1,
							CONSOLE = 2,
							GRAPHIQUE = 3;
	
	public static int choixDialogue = CONSOLE;
	public static int choixPersistance = BDD;
	
	public static void main(String[] args) {
		if(choixDialogue == CONSOLE){
			new MenuDialogueConsole().start();
		}
		else if(choixDialogue == GRAPHIQUE){
			
		}
	}

}
