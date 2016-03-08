package dialogueUtilisateur;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class GestionCompetitions {

	private Inscriptions inscriptions;
	public GestionCompetitions(Inscriptions inscriptions){
		this.inscriptions = inscriptions;
	}
	
	/**
	 * Permet de créer le menu compétitions et y ajoute les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu compétition de type menu.
	 */
	public Menu getMenu(){
		Menu competitions = new Menu("\nGestion des compétitions\nQue voulez-vous faire ?",
				"Gérer les compétitions", "c");
		competitions.ajoute(new Option("Détails des compétitions", "v", getActionVoirCompetitions()));
		competitions.ajoute(new Option("Ajouter une compétition", "a", getActionAjoutCompetition()));
		competitions.ajoute(new Option("Sélectionner une compétition", "s", getActionSelectionCompetition()));
		competitions.ajouteRevenir("r");
		return competitions;
	}
	
	/**
	 * Construit l'action liée au choix de 'voir les compétitions' dans le menu compétitions.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionVoirCompetitions(){
		return new Action(){
			public void optionSelectionnee(){
				if(inscriptions.getCompetitions().isEmpty()){
					System.out.println("Il n'y a pas de compétition enregistrée.");
				}
				else{
					for(Competition c : inscriptions.getCompetitions()){
						String membres = "";
						for(Candidat ca : c.getCandidats()){
							if(ca instanceof Equipe)
								membres += ca.getNom() + " | ";
							else if(ca instanceof Personne)
								membres += ((Personne)ca).getPrenom() + " " + ca.getNom() + " | ";
						}
						System.out.println("\nNom : " + c.toString() + "\n" +
										   "Date de cloture : " + c.getDateCloture() + "\n" +
										   "En équipe : " + c.estEnEquipe() + "\n" +
										   "Candidats : " + membres);
					}
				}
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
				String nomCompetition = EntreesSorties.getString("\nSaisir le nom de la compétition.");
				LocalDate dateCloture = (LocalDate)SaisiesConsole.saisieDateCompetition("\nSaisir la date de clôture de la "
						+ "compétition (au format yyyy-MM-dd)", null);
				boolean estEnEquipe = (boolean)SaisiesConsole.saisieEquipeCompetition("\nLa compétition est-elle pour les équipes "
						+ "ou les personnes seules ?\n(tapez 'e' pour équipes ou 'p' pour personnes)", false);
				inscriptions.createCompetition(nomCompetition, dateCloture, estEnEquipe);
				try {
					inscriptions.sauvegarder();
					System.out.println("\nLa compétition est bien ajoutée.");
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			final ArrayList<Competition> competitions = new ArrayList<>();
			for(Competition c : inscriptions.getCompetitions()){
				competitions.add(c);
			}
			Liste<Competition> menu = new Liste<Competition>("\nListe des compétitions", 
					new ActionListe<Competition>()
			{
				public List<Competition> getListe()
				{
					return competitions;
				}
				public void elementSelectionne(int indice, Competition element)
				{
					getMenuSelectionCompetition(element).start();
				}
			});
			
			menu.ajouteRevenir("r");
			menu.start();
			}
		};
	}
	
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une compétition.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu compétition de type Menu.
	 */
	public Menu getMenuSelectionCompetition(Competition element){
		Menu selectionCompetitions = new Menu("\nGestion de la compétition '" + element.getNom() + "'\n"
				+ "Date de clôture : " + element.getDateCloture() + "\nEn équipe : " + element.estEnEquipe()
				+ "\nQue voulez-vous faire ?", "Gérer les compétitions", "c");
		selectionCompetitions.ajoute(new Option("Voir les candidats", "v", getActionVoirCandidats(element)));
		selectionCompetitions.ajoute(new Option("Ajouter un candidat", "a", getActionAjoutCandidat(element)));
		selectionCompetitions.ajoute(new Option("Supprimer un candidat", "s", getActionSuppressionCandidat(element)));
		selectionCompetitions.ajoute(new Option("Modifier la compétition", "m", getActionModificationCompetition(element, selectionCompetitions)));
		selectionCompetitions.ajoute(new Option("Supprimer la compétition", "d", getActionSuppressionCompetition(element, selectionCompetitions)));
		selectionCompetitions.ajouteRevenir("r");
		return selectionCompetitions;
	}	
	
	/**
	 * Permet de voir les candidats inscris à la compétition
	 * @param la compétition sélectionnée.
	 * @return l'action de voir les candidats.
	 */
	public Action getActionVoirCandidats(final Competition competition){
		return new Action(){
			public void optionSelectionnee(){
				if(competition.getCandidats().isEmpty()){
					System.out.println("Pas de candidats à cette compétition");
				}
				else{
					System.out.println("Candidats à la compétition " + competition.getNom());
					int i = 1;
					for(Candidat c : competition.getCandidats()){
						if(c instanceof Equipe)
							System.out.println("\n" + i + " : " + c.getNom());
						else
							System.out.println("\n" + i + " : " + c.getNom() + " " + ((Personne)c).getPrenom());
					}
				}
			}
		};
	}
	
	/**
	 * Permet l'ajout d'un candidat à la compétition après avoir vérifier qu'il correspond bien.
	 * @param la compétition sélectionnée.
	 * @return l'action d'ajout du candidat.
	 */
	public Action getActionAjoutCandidat(final Competition competition){
		return new Action(){
			public void optionSelectionnee() {
				final ArrayList<Candidat> candidats = new ArrayList<>();
				for(Candidat c : inscriptions.getCandidats()){
					if((c instanceof Equipe && competition.estEnEquipe()) ||
							(c instanceof Personne && !competition.estEnEquipe())){	
						candidats.add(c);
					}
				}
				Liste<Candidat> menu = new Liste<Candidat>("\nQuel candidat voulez-vous ajouter "
																	+ "à la compétition?", 
						new ActionListe<Candidat>()
				{
					public List<Candidat> getListe()
					{
						return candidats;
					}
					public void elementSelectionne(int indice, Candidat candidat)
					{
						if(!competition.inscriptionsOuvertes()){
							System.out.println("Les inscriptions ne sont pas ouvertes.");
						}
						else{
							if(competition.estEnEquipe())
								competition.add((Equipe)candidat);
							else
								competition.add((Personne)candidat);
							System.out.println("Le candidat est bien ajouté à la compétition.");
							try {
								inscriptions.sauvegarder();
							} catch (IOException e) {
								System.out.println("Sauvegarde impossible");
							}
						}
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	
	/**
	 * Permet la suppresion d'un candidat de la compétition. 
	 * @param la compétition sélectionnée.
	 * @return l'action de suppression du candidat.
	 */
	public Action getActionSuppressionCandidat(final Competition competition){
		return new Action(){
			public void optionSelectionnee() {
				final ArrayList<Candidat> candidats = new ArrayList<>();
				for(Candidat c : competition.getCandidats()){
					candidats.add(c);
				}
				if(competition.getCandidats().isEmpty()){
					System.out.println("Aucun candidats.");
				}
				else{
					Liste<Candidat> menu = new Liste<Candidat>("\nQuel candidat voulez-vous supprimer "
																		+ "de la compétition?", 
							new ActionListe<Candidat>()
					{
						public List<Candidat> getListe()
						{
							return candidats;
						}
						public void elementSelectionne(int indice, Candidat candidat)
						{
							competition.remove(candidat);
							System.out.println("Candidat bien supprimé de la compétition");
							try {
								inscriptions.sauvegarder();
							} catch (IOException e) {
								System.out.println("Sauvegarde impossible");
							}
						}
					});
					menu.ajouteRevenir("r");
					menu.start();
				}
			}
		};
	}
	
	/**
	 * Permet de supprimer la compétition.
	 * @param le menu
	 * @param competition la compétition sélectionnée.
	 * @return
	 */
	public Action getActionSuppressionCompetition(final Competition competition, final Menu selection){
		return new Action(){
			public void optionSelectionnee() {
				char reponse = SaisiesConsole.saisieSuppression("la compétition");
				if(reponse == 'o'){
					selection.setRetourAuto(true);
					competition.delete();
					System.out.println("Compétition bien effacée.");
				}
				try {
					inscriptions.sauvegarder();
				} catch (IOException e) {
					System.out.println("Sauvegarde impossible.");
				}
			}
		};
	}

	/**
	 * Permet de modifier chaque champ de la compétition.
	 * @param competition
	 * @param le menu
	 * @return
	 */
	public Action getActionModificationCompetition(final Competition competition, final Menu selection){
		return new Action(){
			public void optionSelectionnee() {
				selection.setRetourAuto(true);
				int mod = 0;
				do{
					mod++;
					switch(mod){
						case 1:
							String nom = EntreesSorties.getString("\nVoulez-vous changer le nom de la compétition ?\n" +
									"Le nom actuel est " + competition.getNom() + ".\n" +
									"Laissez l'espace vide pour ne rien changer, 'q' pour quitter.");
							if(!nom.isEmpty() && !nom.equals("q")){
								competition.setNom(nom);
								System.out.println("Le nom a bien été changé.");
							}
							if(nom.equals("q")){
								mod = 4;
							}
							break;
						case 2: 
							Object date = SaisiesConsole.saisieDateCompetition("\nSaisir la date de clôture de la "
									+ "compétition (au format yyyy-MM-dd).\nLa date actuelle est " +
									competition.getDateCloture() + ".\nLaissez l'espace" +
									" vide pour ne rien changer, 'q' pour quitter, 'r' pour revenir.", 
									competition.getDateCloture());
							if(date instanceof LocalDate){
								competition.setDateCloture((LocalDate)date);
								System.out.println("La date de cloture a bien été changée.");
							} 
							else if(date.equals("q")){
								mod = 4;
							}
							else if(date.equals("r")){
								mod = mod - 2;
							}
							break;
						case 3:
							Object estEnEquipe = SaisiesConsole.saisieEquipeCompetition("\nLa compétition est-elle pour "
									+ "les équipes ou les personnes seules ?\n(tapez 'e' pour équipes ou 'p' pour "
									+ "personnes)\nLa compétition autorise-elle actuellement les équipes : " 
									+ competition.estEnEquipe() + ".\n" + "Laissez l'espace vide pour ne rien changer, "
									+ "'q' pour quitter, 'r' pour revenir.", true);
							
							if(estEnEquipe instanceof Boolean){
								if(competition.getCandidats().isEmpty()){
									competition.setEstEnEquipe((boolean)estEnEquipe);
									System.out.println("Le mode de compétition est bien changé.");
								}
								else{
									System.out.println("Vous devez enlever tous les candidats de la compétition avant "
											+ "de changer son statut.");
								}
							}
							else if(estEnEquipe instanceof String){
								if(estEnEquipe.equals("q")){
									mod = 4;
								}
								else if(estEnEquipe.equals("r")){
									mod = mod - 2;
								}
							}
							break;
					}
				}while(mod < 4);
				try {
					inscriptions.sauvegarder();
				} catch (IOException e) {
					System.out.println("La sauvegarde n'a pas fonctionné.");
				}
			}
		};
	}
}
