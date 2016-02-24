package dialogueUtilisateur;

public class Main {
	
	public static final int CONSOLE = 1,
							GRAPHIQUE = 2;
	
	public static int choixDialogue = CONSOLE;
	
	public static void main(String[] args) {
		if(choixDialogue == CONSOLE){
			DialogueConsole start = new DialogueConsole();
			start.getMenuPrincipal().start();
		}
		else if(choixDialogue == GRAPHIQUE){
			
		}
	}

}
