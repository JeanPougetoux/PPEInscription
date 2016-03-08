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
				System.out.println("\nLa compétition est bien ajoutée.");
				Utilitaires.sauvegarde(inscriptions);
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
					SelectionCompetitions selection = new SelectionCompetitions(element, inscriptions);
					selection.getMenuSelectionCompetition().start();
				}
			});
			
			menu.ajouteRevenir("r");
			menu.start();
			}
		};
	}
}
