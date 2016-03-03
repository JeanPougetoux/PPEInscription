package dialogueUtilisateur;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
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
		Menu Equipe = new Menu("Gestion des équipes\nQue-voulez-vous faire ?",
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
						System.out.println("Nom : " + c.getNom() + "\n" + "Inscrite à : " + c.getCompetitions() + "\n");
					}
				}
			}
		};
	}

	/**
	 * Construit l'action liée au choix de 'ajouter un Equipe' dans le menu Equipe.
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
	 * Construit l'action liée au choix de 'sélectionner un Equipe' dans le menu Equipe.
	 * @return une nouvelle action avec la méthode optionSelectionnee.
	 */
	public Action getActionSelectionEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				
			}
		};
	}
}
