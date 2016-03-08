package dialogueUtilisateur;

import java.util.ArrayList;
import java.util.List;

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

public class SelectionEquipes {
	
	public Inscriptions inscriptions;
	private Equipe equipe;
	private Menu selectionEquipe;
	
	
	public SelectionEquipes(Inscriptions inscriptions, Equipe equipe){
		this.inscriptions = inscriptions;
		this.equipe = equipe;
	}
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une équipe.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu équiê de type Menu.
	 */
	public Menu getMenuSelectionEquipe(){
		selectionEquipe = new Menu("\nGestion de l'équipe '" + equipe.getNom() + "'\nQue voulez-vous faire ?",
				"Gérer les équipes", "e");
		selectionEquipe.ajoute(new Option("Voir les membres de l'équipe", "v", getActionVoirMembres()));
		selectionEquipe.ajoute(new Option("Ajouter une personne à l'équipe", "a", getActionAjoutPersonne()));
		selectionEquipe.ajoute(new Option("Supprimer une personne de l'équipe", "s", getActionSuppressionPersonne()));
		selectionEquipe.ajoute(new Option("Modifier le nom de l'équipe", "m", getActionModificationEquipe()));
		selectionEquipe.ajoute(new Option("Supprimer l'équipe", "d", getActionSuppressionEquipe()));
		selectionEquipe.ajouteRevenir("r");
		return selectionEquipe;
	}	
	
	/**
	 * Permet de voir tous les membres de l'équipe
	 * @param l'équipe sélectionnée
	 * @return l'action de voir les membres
	 */
	public Action getActionVoirMembres(){
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
	public Action getActionAjoutPersonne(){
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
						Utilitaires.sauvegarde(inscriptions);
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
	public Action getActionSuppressionPersonne(){
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
						Utilitaires.sauvegarde(inscriptions);
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
	public Action getActionModificationEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				selectionEquipe.setRetourAuto(true);
				String nouveauNom = EntreesSorties.getString("Veuillez saisir le nouveau nom de l'équipe ou 'r' pour revenir"
													+ ", ancien nom : " + equipe.getNom() + " nouveau nom : ");
				if(nouveauNom != "r" && !nouveauNom.isEmpty()){
					equipe.setNom(nouveauNom);
					System.out.println("Le nom de l'équipe a bien été changé en : " + equipe.getNom());
				}
				else if(nouveauNom.isEmpty()){
					System.out.println("Chaine vide.");
				}
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
	
	/**
	 * Permet de supprimer l'équipe
	 * @param l'équipe sélectionnée
	 * @param le menu
	 * @return l'action de suppression
	 */
	public Action getActionSuppressionEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				char reponse = SaisiesConsole.saisieSuppression("l'équipe");
				if(reponse == 'o'){
					selectionEquipe.setRetourAuto(true);
					equipe.delete();
					System.out.println("Equipe bien effacée.");
				}
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
}
