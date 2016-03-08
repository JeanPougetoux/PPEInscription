package dialogueUtilisateur;


public class Main {
	
	public static final int SERIALIZATION = 0,
							BDD = 1,
							CONSOLE = 2,
							GRAPHIQUE = 3;
	
	public static int choixDialogue = CONSOLE;
	public static int choixPersistance = SERIALIZATION;
	
	public static void main(String[] args) {
		if(choixDialogue == CONSOLE){
			DialogueConsole start = new DialogueConsole();
			start.getMenuPrincipal().start();
		}
		else if(choixDialogue == GRAPHIQUE){
			
		}
	}

}
