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

	private int persistance;
	private Inscriptions inscriptions;
	public GestionCompetitions(int persistance){
		this.persistance = persistance;
	}
	
	public GestionCompetitions(int persistance, Inscriptions inscriptions){
		this.persistance = persistance;
		this.inscriptions = inscriptions;
	}
	
	/**
	 * Permet de créer le menu compétitions et y ajoute les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu compétition de type menu.
	 */
	public Menu getMenu(){
		Menu competitions = new Menu("Gestion des compétitions\nQue voulez-vous faire ?",
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
				if(persistance == DialogueConsole.SERIALIZATION){
					for(Competition c : inscriptions.getCompetitions()){
						System.out.println("Nom : " + c.toString() + "\n" +
										   "Date de cloture : " + c.getDateCloture() + "\n" +
										   "En équipe : " + c.estEnEquipe() + "\n");
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
				String nomCompetition = EntreesSorties.getString("Saisir le nom de la compétition.");
				LocalDate dateCloture = SaisiesConsole.saisieDateCompetition();
				boolean estEnEquipe = SaisiesConsole.saisieEquipeCompetition();
				if(persistance == DialogueConsole.SERIALIZATION){
					inscriptions.createCompetition(nomCompetition, dateCloture, estEnEquipe);
					try {
						inscriptions.sauvegarder();
						System.out.println("La compétition est bien ajoutée.");
					} catch (IOException e) {
						e.printStackTrace();
					}
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
				if(persistance == DialogueConsole.SERIALIZATION){
					for(Competition c : inscriptions.getCompetitions()){
						competitions.add(c);
					}
				}
				Liste<Competition> menu = new Liste<Competition>("Liste des compétitions", 
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
		Menu selectionCompetitions = new Menu("Gestion des compétitions\nQue voulez-vous faire ?",
				"Gérer les compétitions", "c");
		selectionCompetitions.setRetourAuto(true);
		selectionCompetitions.ajoute(new Option("Ajouter un candidat", "a", getActionAjoutCandidat(element)));
		selectionCompetitions.ajoute(new Option("Supprimer un candidat", "s", getActionSuppressionCandidat(element)));
		selectionCompetitions.ajoute(new Option("Modifier la compétition", "m", null));
		selectionCompetitions.ajoute(new Option("Supprimer la compétition", "d", getActionSuppressionCompetition(element)));
		selectionCompetitions.ajouteRevenir("r");
		return selectionCompetitions;
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
				if(persistance == DialogueConsole.SERIALIZATION){
					for(Candidat c : inscriptions.getCandidats()){
						candidats.add(c);
					}
					Liste<Candidat> menu = new Liste<Candidat>("Quel candidat voulez-vous ajouter "
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
								if(candidat instanceof Personne){
									try{
										competition.add((Personne)candidat);
										System.out.println("La personne est bien ajoutée à la compétition");
									}
									catch(Exception e){
										System.out.println("La compétition n'est pas autorisée aux personnes seules");
									}
								}
								else if(candidat instanceof Equipe){
									try{
										competition.add((Equipe)candidat);
										System.out.println("L'équipe est bien ajoutée à la compétition");
									}
									catch(Exception e){
										System.out.println("La compétition n'est pas autorisée aux équipes");
									}
								}
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
			}
		};
	}
	
	public Action getActionSuppressionCandidat(final Competition competition){
		return new Action(){
			public void optionSelectionnee() {
				final ArrayList<Candidat> candidats = new ArrayList<>();
				if(persistance == DialogueConsole.SERIALIZATION){
					for(Candidat c : competition.getCandidats()){
						candidats.add(c);
					}
					if(competition.getCandidats().isEmpty()){
						System.out.println("Aucun candidats.");
					}
					else{
						Liste<Candidat> menu = new Liste<Candidat>("Quel candidat voulez-vous supprimer "
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
			}
		};
	}
	
	public Action getActionSuppressionCompetition(final Competition competition){
		return new Action(){
			public void optionSelectionnee() {
				char reponse = SaisiesConsole.saisieSuppressionCompetition();
				if(reponse == 'o'){
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
}
