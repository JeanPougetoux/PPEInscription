package dialogueUtilisateur.Equipes.Gestion;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Utilitaires;
import dialogueUtilisateur.Equipes.Selection.MenuSelectionEquipes;
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

public class MenuGestionEquipes extends Menu{

	private Inscriptions inscriptions;
	public MenuGestionEquipes(Inscriptions inscriptions){
		super("\nGestion des équipes\nQue-voulez-vous faire ?",
				"Gérer les équipes", "e");
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Détails des équipes", "v", new ActionVoirEquipes(this)));
		this.ajoute(new Option("Ajouter une équipe", "a", new ActionAjoutEquipe(this)));
		this.ajoute(new Option("Sélectionner une équipe", "s", getActionSelectionEquipe()));
		this.ajouteRevenir("r");
	}
	
	/**
	 * Retourne l'objet inscriptions
	 * @return objet Inscription
	 */
	public Inscriptions getInscriptions(){
		return inscriptions;
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
				Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes :\n", 
						new ActionListe<Equipe>()
				{
					public List<Equipe> getListe()
					{
						return equipes;
					}
					public void elementSelectionne(int indice, Equipe element)
					{
						new MenuSelectionEquipes(inscriptions, element).start();
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
}
