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

public class ActionAjoutCandidat implements Action{
	
	private Inscriptions inscriptions;
	private Competition competition;
	
	/**
	 * Permet l'ajout d'un candidat à la compétition après avoir vérifier qu'il correspond bien.
	 */
	public ActionAjoutCandidat(MenuSelectionCompetitions menu){
		this.inscriptions = menu.getInscriptions();
		this.competition = menu.getCompetition();
	}
	
	public void optionSelectionnee() {
		final ArrayList<Candidat> candidats = new ArrayList<>();
		for(Candidat c : inscriptions.getCandidats()){
			if((c instanceof Equipe && competition.estEnEquipe()) && !competition.getCandidats().contains(c)||
					(c instanceof Personne && !competition.estEnEquipe() && !competition.getCandidats().contains(c))){	
				candidats.add(c);
			}
		}
		if(candidats.isEmpty()){
			System.out.println("Aucun candidat disponible pour cette compétition.");
		}
		else{
			Liste<Candidat> menu = new Liste<Candidat>("\nQuel candidat voulez-vous ajouter "
																+ "à la compétition?\n", 
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
						if(competition.estEnEquipe()){
							if(((Equipe)candidat).getMembres().isEmpty()){
								System.out.println("Une équipe sans membres ne peut pas s'inscrire.");
							}
							else{
								competition.add((Equipe)candidat);
								System.out.println("\n'" + candidat.toString() + "' est bien ajoutée à la compétition.");
							}
						}
						else{
							competition.add((Personne)candidat);
							System.out.println("\n'" + ((Personne)candidat).toString() + "' est bien ajouté à la compétition.");
						}
						Utilitaires.sauvegarde(inscriptions);
					}
				}
			});
			menu.ajouteRevenir("r");
			menu.start();
		}
	}

}
