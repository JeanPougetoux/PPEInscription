package dialogueUtilisateur.Personnes.Gestion;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action permettant de voir toutes les personnes existantes.
 * @author Jean
 *
 */
public class ActionVoirPersonnes implements Action{
	
	private MenuGestionPersonnes menu;

	public ActionVoirPersonnes(MenuGestionPersonnes menu){
		this.menu = menu;
	}

	public void optionSelectionnee() {
		boolean nul = true;
		for(Candidat c : menu.getInscriptions().getCandidats()){
			if(c instanceof Personne){
				nul = false;
				String membre = "", competitions = "";
				for(Equipe e : ((Personne)c).getEquipes()){
					membre += e.getNom() + " | ";
				}
				for(Competition o : c.getCompetitions()){
					competitions += o.getNom() + " | ";
				}
				System.out.println("\nNom : " + c.getNom() + "\n"
								+ "Prénom : " + ((Personne)c).getPrenom() + "\n"
								+ "Email : " + ((Personne)c).getMail() + "\n"
								+ "Membre de : " + membre + "\n"
								+ "Inscrit à : " + competitions);
			}
		}
		if(nul)
			System.out.println("\nIl n'y a pas de personne inscrite.");
	}
}
