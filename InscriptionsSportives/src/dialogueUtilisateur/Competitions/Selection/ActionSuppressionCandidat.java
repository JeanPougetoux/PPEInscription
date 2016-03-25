package dialogueUtilisateur.Competitions.Selection;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Utilitaires;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

/**
 * Permet la suppresion d'un candidat de la compétition.
 * @author Jean
 *
 */
public class ActionSuppressionCandidat implements Action{
	
	private Inscriptions inscriptions;
	private Competition competition;
	
	public ActionSuppressionCandidat(MenuSelectionCompetitions menu){
		this.inscriptions = menu.getInscriptions();
		this.competition = menu.getCompetition();
	}
	
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
																+ "de la compétition?\n", 
					new ActionListe<Candidat>()
			{
				public List<Candidat> getListe()
				{
					return candidats;
				}
				public void elementSelectionne(int indice, Candidat candidat)
				{
					competition.remove(candidat);
					if(candidat instanceof Equipe)
						System.out.println("\n'" + candidat.toString() + " bien supprimé de la compétition.");
					else
						System.out.println("\n'" + ((Personne)candidat).toString() + "' bien supprimé de la compétition.");
					Utilitaires.sauvegarde(inscriptions);
				}
			});
			menu.ajouteRevenir("r");
			menu.start();
		}
	}

}
