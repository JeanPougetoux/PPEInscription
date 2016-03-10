package dialogueUtilisateur.Competitions.Selection;

import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;

/**
 * Permet de voir les candidats inscrits à la compétition.
 * @author Jean
 *
 */
public class ActionVoirCandidats implements Action{

	private MenuSelectionCompetitions menu;
	
	public ActionVoirCandidats(MenuSelectionCompetitions menu){
		this.menu = menu;
	}
	
	public void optionSelectionnee(){
		if(menu.getCompetition().getCandidats().isEmpty()){
			System.out.println("Pas de candidats à cette compétition");
		}
		else{
			System.out.println("\nCandidats à la compétition " + menu.getCompetition().getNom() + ":\n");
			int i = 1;
			for(Candidat c : menu.getCompetition().getCandidats()){
				if(c instanceof Equipe)
					System.out.println(i + " : " + c.toString());
				else
					System.out.println(i + " : " + ((Personne)c).toString());
				i++;
			}
		}
	}
}
