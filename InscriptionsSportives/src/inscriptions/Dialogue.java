package inscriptions;

import java.time.LocalDate;

import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class Dialogue {

	private Inscriptions inscriptions;
	private Menu menuPrincipal;
	
	public Dialogue(){
		inscriptions = Inscriptions.getInscriptions();
		menuPrincipal = buildMenuPrincipal();
	}
	
	/**
	 * Retourne l'instance du menu principal
	 * @return instance du menu de type menu
	 */
	public Menu getMenuPrincipal(){
		return menuPrincipal;
	}
	
	public static void main(String[] args) {
		Dialogue start = new Dialogue();
		start.getMenuPrincipal().start();
	}
	
	/**
	 * Permet de créer le menu principal et y ajoute les prochains menu ainsi que
	 * l'action 'quitter'.
	 * @return le menu principal de type menu.
	 */
	public Menu buildMenuPrincipal(){
		Menu menuPrincipal = new Menu("Bienvenue dans le gestionnaire de compétitions M2L\n"
										+ "Que voulez-vous faire ?");
		menuPrincipal.ajoute(buildMenuCompetitions());
		menuPrincipal.ajoute(buildMenuEquipe());
		menuPrincipal.ajoute(buildMenuPersonne());
		menuPrincipal.ajouteQuitter("q");
		return menuPrincipal;
	}
	
	/**
	 * Permet de créer le menu compétitions et y ajoute les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu compétition de type menu.
	 */
	public Menu buildMenuCompetitions(){
		Menu competitions = new Menu("Gestion des compétitions\nQue voulez-vous faire ?",
				"Gérer les compétitions", "c");
		competitions.ajoute(new Option("Voir les compétitions", "v", getActionVoirCompetitions()));
		competitions.ajoute(new Option("Ajouter une compétition", "a", getActionAjoutCompetition()));
		competitions.ajoute(new Option("Sélectionner une compétition", "s", getActionSelectionCompetition()));
		competitions.ajouteRevenir("r");
		return competitions;
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
	 * Construit l'action liée au choix de 'voir les compétitions' dans le menu compétitions.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionVoirCompetitions(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
	}
	
	/**
	 * Construit l'action liée au choix de 'ajouter une compétition' dans le menu compétitions.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionAjoutCompetition(){
		return new Action(){
			public void optionSelectionnee(){
				String nomCompetition = EntreesSorties.getString("Saisir le nom de la compétition.");
				LocalDate dateCloture = Saisies.saisieDateCompetition();
				boolean estEnEquipe = Saisies.saisieEquipeCompetition();
				inscriptions.createCompetition(nomCompetition, dateCloture, estEnEquipe);
				System.out.println(inscriptions);
			}
		};
	}

	/**
	 * Construit l'action liée au choix de 'sélectionner une compétition' dans le menu compétitions.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionSelectionCompetition(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
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
