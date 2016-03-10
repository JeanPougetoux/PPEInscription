package dialogueUtilisateur.Competitions.Gestion;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Competitions.Selection.MenuSelectionCompetitions;
import inscriptions.Competition;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

public class ActionSelectionCompetition implements Action{

	private Inscriptions inscriptions;
	
	/**
	 * Construit l'action liée au choix de 'sélectionner une compétition' dans le menu compétitions.
	 */
	public ActionSelectionCompetition(MenuGestionCompetitions menu){
		this.inscriptions = menu.getInscriptions();
	}
	
	public void optionSelectionnee(){
		final ArrayList<Competition> competitions = new ArrayList<>();
		for(Competition c : inscriptions.getCompetitions()){
			competitions.add(c);
		}
		Liste<Competition> menu = new Liste<Competition>("\nListe des compétitions :\n", 
				new ActionListe<Competition>()
		{
			public List<Competition> getListe()
			{
				return competitions;
			}
			public void elementSelectionne(int indice, Competition element)
			{
				new MenuSelectionCompetitions(element, inscriptions).start();
			}
		});
		menu.ajouteRevenir("r");
		menu.start();
	}
}
