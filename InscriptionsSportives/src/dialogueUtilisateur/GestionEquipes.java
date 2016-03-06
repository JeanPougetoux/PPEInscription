package dialogueUtilisateur;

import java.io.IOException;
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
				for(Candidat c : inscriptions.getCandidats()){
					if(c instanceof Equipe){
						String membre = "";
						for(Personne p : ((Equipe) c).getMembres()){
							membre += p.getPrenom() + " " + p.getNom() + " | ";
						}
						System.out.println("\nNom : " + c.getNom() + "\n" 
										+ "Membres : " + membre + "\n"
										+ "Inscrite à : " + c.getCompetitions() + "\n");
					}
				}
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
				}
				try{
					inscriptions.sauvegarder();
				}
				catch(Exception e){
					System.out.println("Erreur lors de la sauvegarde.");
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
						getMenuSelectionEquipe(element).start();
					}
				});
				
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une équipe.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu équiê de type Menu.
	 */
	public Menu getMenuSelectionEquipe(Equipe element){
		Menu selectionEquipe = new Menu("\nGestion de l'équipe '" + element.getNom() + "'\nQue voulez-vous faire ?",
				"Gérer les équipes", "e");
		selectionEquipe.ajoute(new Option("Voir les membres de l'équipe", "v", getActionVoirMembres(element)));
		selectionEquipe.ajoute(new Option("Ajouter une personne à l'équipe", "a", getActionAjoutPersonne(element)));
		selectionEquipe.ajoute(new Option("Supprimer une personne de l'équipe", "s", getActionSuppressionPersonne(element)));
		selectionEquipe.ajoute(new Option("Ajouter à une compétition", "c", getActionAjoutCompetition(element)));
		selectionEquipe.ajoute(new Option("Modifier le nom de l'équipe", "m", getActionModificationEquipe(element, selectionEquipe)));
		selectionEquipe.ajoute(new Option("Supprimer l'équipe", "d", getActionSuppressionEquipe(element, selectionEquipe)));
		selectionEquipe.ajouteRevenir("r");
		return selectionEquipe;
	}	
	
	/**
	 * Permet de voir tous les membres de l'équipe
	 * @param l'équipe sélectionnée
	 * @return l'action de voir les membres
	 */
	public Action getActionVoirMembres(final Equipe equipe){
		return new Action(){
			public void optionSelectionnee() {
				if(equipe.getMembres().isEmpty()){
					System.out.println("Il n'y a aucun membre.");
				}
				else{
					int i=1;
					System.out.println("Les membres sont : ");
					for(Personne p : equipe.getMembres()){
						System.out.println(i + ": " + p.getPrenom() + " " + p.getNom());
						i++;
					}
				}
			}
		};
	}
	
	/**
	 * Retourne l'action qui permet d'ajouter une personne à une équipe
	 * @param l'équipe sélectionnée
	 * @return l'action d'ajout
	 */
	public Action getActionAjoutPersonne(final Equipe equipe){
		return new Action(){
			public void optionSelectionnee() {
				final ArrayList<Personne> personnes = new ArrayList<Personne>();
				for(Candidat c : inscriptions.getCandidats()){
					if(c instanceof Personne && !equipe.getMembres().contains(c)){
						personnes.add((Personne) c);
					}
				}
				Liste<Personne> menu = new Liste<Personne>("\nListe des personnes", 
						new ActionListe<Personne>(){
					public List<Personne> getListe()
					{
						return personnes;
					}
					public void elementSelectionne(int indice, Personne element)
					{
						equipe.add(element);
						System.out.println(element.getPrenom() + " " + element.getNom() + " bien ajouté à l'équipe.");
						try{
							inscriptions.sauvegarder();
						}
						catch(Exception e){
							System.out.println("Erreur lors de la sauvegarde");
						}
					}
				});
				
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	
	/**
	 * Permet la suppression d'une personne de l'équipe
	 * @param equipe
	 * @return l'action de suppression
	 */
	public Action getActionSuppressionPersonne(final Equipe equipe){
		return new Action(){
			public void optionSelectionnee() {
				final ArrayList<Personne> personnes = new ArrayList<Personne>();
				for(Personne e : equipe.getMembres()){
					personnes.add(e);
				}
				Liste<Personne> menu = new Liste<Personne>("\nListe des membres", 
						new ActionListe<Personne>(){
					public List<Personne> getListe()
					{
						return personnes;
					}
					public void elementSelectionne(int indice, Personne element)
					{
						equipe.remove(element);
						System.out.println(element.getPrenom() + " " + element.getNom() + " bien supprimé de l'équipe.");
						try{
							inscriptions.sauvegarder();
						}
						catch(Exception e){
							System.out.println("Erreur lors de la sauvegarde");
						}
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	
	/**
	 * Permet d'ajouter l'équipe à une compétition
	 * @param l'équipe sélectionnée
	 * @return l'action d'ajout de l'équipe
	 */
	public Action getActionAjoutCompetition(final Equipe equipe){
		return new Action(){
			public void optionSelectionnee(){
				final ArrayList<Competition> competitions = new ArrayList<Competition>();
				for(Competition c : inscriptions.getCompetitions()){
					if(!equipe.getCompetitions().contains(c) && c.estEnEquipe()){
						competitions.add(c);
					}
				}
				Liste<Competition> menu = new Liste<Competition>("\nListe des compétitions", 
						new ActionListe<Competition>(){
					public List<Competition> getListe()
					{
						return competitions;
					}
					public void elementSelectionne(int indice, Competition competition)
					{
						competition.add(equipe);
						System.out.println("Equipe bien ajoutée à la compétition.");
						try{
							inscriptions.sauvegarder();
						}
						catch(Exception e){
							System.out.println("Erreur lors de la sauvegarde");
						}
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	/**
	 * Permet de modifier le nom de l'équipe
	 * @param l'équipe sélectionnée
	 * @param le menu
	 * @return l'action de modifier le nom
	 */
	public Action getActionModificationEquipe(final Equipe equipe, final Menu selection){
		return new Action(){
			public void optionSelectionnee(){
				selection.setRetourAuto(true);
				String nouveauNom = EntreesSorties.getString("Veuillez saisir le nouveau nom de l'équipe ou 'r' pour revenir"
													+ ", ancien nom : " + equipe.getNom() + " nouveau nom : ");
				if(nouveauNom != "r" && !nouveauNom.isEmpty()){
					equipe.setNom(nouveauNom);
					System.out.println("Le nom de l'équipe a bien été changé en : " + equipe.getNom());
				}
				else if(nouveauNom.isEmpty()){
					System.out.println("Chaine vide.");
				}
				
				try{
					inscriptions.sauvegarder();
				}
				catch(Exception e){
					System.out.println("Erreur lors de la sauvegarde");
				}
			}
		};
	}
	
	/**
	 * Permet de supprimer l'équipe
	 * @param l'équipe sélectionnée
	 * @param le menu
	 * @return l'action de suppression
	 */
	public Action getActionSuppressionEquipe(final Equipe equipe, final Menu selection){
		return new Action(){
			public void optionSelectionnee(){
				char reponse = SaisiesConsole.saisieSuppression("l'équipe");
				if(reponse == 'o'){
					selection.setRetourAuto(true);
					equipe.delete();
					System.out.println("Equipe bien effacée.");
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
