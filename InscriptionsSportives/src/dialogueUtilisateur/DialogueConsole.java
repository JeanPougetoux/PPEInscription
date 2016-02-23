package dialogueUtilisateur;


import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class DialogueConsole {

	public final static int SERIALIZATION = 1, 
							BDD = 2;
	private Inscriptions inscriptions;
	private Menu menuPrincipal;
	private GestionCompetitions g;
	
	public DialogueConsole(int persistance){
		inscriptions = Inscriptions.getInscriptions();
		g = new GestionCompetitions(persistance, inscriptions);
		menuPrincipal = buildMenuPrincipal();
	}
	
	/**
	 * Retourne l'instance du menu principal
	 * @return instance du menu de type menu
	 */
	public Menu getMenuPrincipal(){
		return menuPrincipal;
	}
	
	/**
	 * Permet de créer le menu principal et y ajoute les prochains menu ainsi que
	 * l'action 'quitter'.
	 * @return le menu principal de type menu.
	 */
	public Menu buildMenuPrincipal(){
		Menu menuPrincipal = new Menu("Bienvenue dans le gestionnaire de compétitions M2L\n"
										+ "Que voulez-vous faire ?");
		menuPrincipal.ajoute(g.getMenu());
		menuPrincipal.ajoute(buildMenuEquipe());
		menuPrincipal.ajoute(buildMenuPersonne());
		menuPrincipal.ajouteQuitter("q");
		return menuPrincipal;
	}
	
	
	/**
	 * Permet de créer le menu candidats et y ajout les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu candidat de type menu.
	 */
	public Menu buildMenuEquipe(){
		Menu candidats = new Menu("Gestion des équipes\nQue-voulez-vous faire ?",
				"Gérer les équipes", "e");
		candidats.ajoute(new Option("Voir les équipes", "v", getActionVoirCandidats()));
		candidats.ajoute(new Option("Ajouter une équipe", "a", getActionAjoutCandidat()));
		candidats.ajoute(new Option("Sélectionner une équipe", "s", getActionSelectionCandidat()));
		candidats.ajouteRevenir("r");
		return candidats;
	}
	
	/**
	 * Permet de créer le menu candidats et y ajout les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu candidat de type menu.
	 */
	public Menu buildMenuPersonne(){
		Menu candidats = new Menu("Gestion des personnes\nQue-voulez-vous faire ?",
				"Gérer les personnes", "p");
		candidats.ajoute(new Option("Voir les personnes", "v", getActionVoirCandidats()));
		candidats.ajoute(new Option("Ajouter une personne", "a", getActionAjoutCandidat()));
		candidats.ajoute(new Option("Sélectionner une personne", "s", getActionSelectionCandidat()));
		candidats.ajouteRevenir("r");
		return candidats;
	}
	
	/**
	 * Construit l'action liée au choix de 'voir les candidats' dans le menu candidats.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionVoirCandidats(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
	}

	/**
	 * Construit l'action liée au choix de 'ajouter un candidat' dans le menu candidats.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionAjoutCandidat(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
	}
	
	/**
	 * Construit l'action liée au choix de 'sélectionner un candidat' dans le menu candidats.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionSelectionCandidat(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
	}


}
