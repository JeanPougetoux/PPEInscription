package dialogueUtilisateur.Equipes.Gestion;

import java.util.ArrayList;
import java.util.Set;

import inscriptions.*;
import utilitaires.ligneDeCommande.Action;

/**
 * Construit l'action liée au choix de 'voir les Equipe' dans le menu Equipe.
 * @author Jean
 *
 */
public class ActionVoirEquipes implements Action{
	
	private MenuGestionEquipes menu;
	
	public ActionVoirEquipes(MenuGestionEquipes menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		boolean nul = true;
		Inscriptions inscriptions = menu.getInscriptions();
		Set<Candidat> candidats = inscriptions.getCandidats();
		for(Candidat c : candidats){
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
