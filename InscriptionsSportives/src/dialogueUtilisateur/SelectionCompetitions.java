package dialogueUtilisateur;

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

public class SelectionCompetitions{
	
	public Inscriptions inscriptions;
	private Competition competition;
	private Menu selectionCompetitions;
	
	public SelectionCompetitions(Competition competition, Inscriptions inscriptions){
		this.inscriptions = inscriptions;
		this.competition = competition;
		selectionCompetitions = new Menu("\nGestion de la compétition '" + competition.getNom() + "'\n"
				+ "Date de clôture : " + competition.getDateCloture() + "\nEn équipe : " 
				+ Utilitaires.getOuiNon(competition.estEnEquipe()) 
				+ "\nQue voulez-vous faire ?", "Gérer les compétitions", "c");
	}
	
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une compétition.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu compétition de type Menu.
	 */
	public Menu getMenuSelectionCompetition(){
		selectionCompetitions.ajoute(new Option("Voir les candidats", "v", getActionVoirCandidats()));
		selectionCompetitions.ajoute(new Option("Ajouter un candidat", "a", getActionAjoutCandidat()));
		selectionCompetitions.ajoute(new Option("Supprimer un candidat", "s", getActionSuppressionCandidat()));
		selectionCompetitions.ajoute(new Option("Modifier la compétition", "m", getActionModificationCompetition()));
		selectionCompetitions.ajoute(new Option("Supprimer la compétition", "d", getActionSuppressionCompetition()));
		selectionCompetitions.ajouteRevenir("r");
		return selectionCompetitions;
	}	
	
	/**
	 * Permet de voir les candidats inscris à la compétition
	 * @param la compétition sélectionnée.
	 * @return l'action de voir les candidats.
	 */
	public Action getActionVoirCandidats(){
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
	public Action getActionAjoutCandidat(){
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
							Utilitaires.sauvegarde(inscriptions);
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
	public Action getActionSuppressionCandidat(){
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
							Utilitaires.sauvegarde(inscriptions);
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
	public Action getActionSuppressionCompetition(){
		return new Action(){
			public void optionSelectionnee() {
				char reponse = SaisiesConsole.saisieSuppression("la compétition");
				if(reponse == 'o'){
					selectionCompetitions.setRetourAuto(true);
					competition.delete();
					System.out.println("Compétition bien effacée.");
				}
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}

	/**
	 * Permet de modifier chaque champ de la compétition.
	 * @param competition
	 * @param le menu
	 * @return
	 */
	public Action getActionModificationCompetition(){
		return new Action(){
			public void optionSelectionnee() {
				selectionCompetitions.setRetourAuto(true);
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
							else{
								mod = Utilitaires.getMod(mod, nom);
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
							else{
								mod = Utilitaires.getMod(mod, date);
							}
							break;
						case 3:
							Object estEnEquipe = SaisiesConsole.saisieEquipeCompetition("\nLa compétition est-elle pour "
									+ "les équipes ou les personnes seules ?\n(tapez 'e' pour équipes ou 'p' pour "
									+ "personnes)\nLa compétition autorise-elle actuellement les équipes : " 
									+ Utilitaires.getOuiNon(competition.estEnEquipe()) + ".\n" + "Laissez l'espace vide pour ne rien changer, "
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
								mod = Utilitaires.getMod(mod, estEnEquipe);
							}
							break;
					}
				}while(mod < 5);
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
}
