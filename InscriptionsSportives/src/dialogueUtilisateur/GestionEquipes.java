package dialogueUtilisateur;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Util;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class GestionEquipes {

	private Inscriptions inscriptions;
	public GestionEquipes(Inscriptions inscriptions){
		this.inscriptions = inscriptions;
	}
	
	/**
	 * Permet de créer le menu Equipe et y ajout les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu Equipe de type menu.
	 */
	public Menu getMenu(){
		Menu Equipe = new Menu("\nGestion des équipes\nQue-voulez-vous faire ?",
				"Gérer les équipes", "e");
		Equipe.ajoute(new Option("Détails des équipes", "v", getActionVoirEquipe()));
		Equipe.ajoute(new Option("Ajouter une équipe", "a", getActionAjoutEquipe()));
		Equipe.ajoute(new Option("Sélectionner une équipe", "s", getActionSelectionEquipe()));
		Equipe.ajouteRevenir("r");
		return Equipe;
	}
	
	/**
	 * Construit l'action liée au choix de 'voir les Equipe' dans le menu Equipe.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionVoirEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				boolean nul = true;
				for(Candidat c : inscriptions.getCandidats()){
					if(c instanceof Equipe){
						nul = false;
						String membre = "";
						for(Personne p : ((Equipe) c).getMembres()){
							membre += p.getPrenom() + " " + p.getNom() + " | ";
						}
						System.out.println("\nNom : " + c.getNom() + "\n" 
										+ "Membres : " + membre + "\n"
										+ "Inscrite à : " + c.getCompetitions() + "\n");
					}
				}
				if(nul)
					System.out.println("Il n'y a pas d'équipes inscrite.");
			}
		};
	}

	/**
	 * Construit l'action liée au choix de 'ajouter une équipe' dans le menu Equipe.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionAjoutEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				String nomEquipe = EntreesSorties.getString("\nSaisir le nom de l'équipe.\n'a' pour annuler.");
				if(!nomEquipe.isEmpty() && !nomEquipe.equals("a")){
					inscriptions.createEquipe(nomEquipe);
					System.out.println("Equipe bien ajoutée.");
					Utilitaires.sauvegarde(inscriptions);
				}
			}
		};
	}
	
	/**
	 * Construit l'action liée au choix de 'sélectionner une Equipe' dans le menu Equipe.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionSelectionEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				final ArrayList<Equipe> equipes = new ArrayList<>();
				for(Candidat e : inscriptions.getCandidats()){
					if(e instanceof Equipe)
						equipes.add((Equipe)e);
				}
				Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes", 
						new ActionListe<Equipe>()
				{
					public List<Equipe> getListe()
					{
						return equipes;
					}
					public void elementSelectionne(int indice, Equipe element)
					{
						SelectionEquipes selection = new SelectionEquipes(inscriptions, element);
						selection.getMenuSelectionEquipe().start();
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
}
