package dialogueUtilisateur.Competitions.Gestion;

import dialogueUtilisateur.Utilitaires;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action liée au choix de 'voir les compétitions' dans le menu compétitions.
 * @author Jean
 *
 */
public class ActionVoirCompetitions implements Action{

	private Inscriptions inscriptions;

	public ActionVoirCompetitions(MenuGestionCompetitions menu){
		this.inscriptions = menu.getInscriptions();
	}
	
	public void optionSelectionnee(){
		if(inscriptions.getCompetitions().isEmpty()){
			System.out.println("\nIl n'y a pas de compétition enregistrée.");
		}
		else{
			for(Competition c : inscriptions.getCompetitions()){
				String membres = "";
				for(Candidat ca : c.getCandidats()){
					if(ca instanceof Equipe)
						membres += ca.getNom() + " | ";
					else if(ca instanceof Personne)
						membres += ((Personne)ca).toString() + " | ";
				}
				System.out.println("\nNom : " + c.toString() + "\n" +
								   "Date de cloture : " + c.getDateCloture() + "\n" +
								   "En équipe : " + Utilitaires.getOuiNon(c.estEnEquipe()) + "\n" +
								   "Candidats : " + membres);
			}
		}
	}

}
