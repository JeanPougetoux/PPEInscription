package dialogueUtilisateur.Equipes.Gestion;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

public class ActionVoirEquipes implements Action{
	
	private MenuGestionEquipes menu;
	
	/**
	 * Construit l'action liée au choix de 'voir les Equipe' dans le menu Equipe.
	 */
	public ActionVoirEquipes(MenuGestionEquipes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		boolean nul = true;
		for(Candidat c : menu.getInscriptions().getCandidats()){
			if(c instanceof Equipe){
				nul = false;
				String membre = "";
				for(Personne p : ((Equipe) c).getMembres()){
					membre += p.toString() + " | ";
				}
				System.out.println("\nNom : " + c.getNom() + "\n" 
								+ "Membres : " + membre + "\n"
								+ "Inscrite à : " + c.getCompetitions() + "\n");
			}
		}
		if(nul)
			System.out.println("\nIl n'y a pas d'équipes inscrite.");
	}

}
