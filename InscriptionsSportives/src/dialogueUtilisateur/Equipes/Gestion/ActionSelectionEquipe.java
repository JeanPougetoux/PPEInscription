package dialogueUtilisateur.Equipes.Gestion;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Equipes.Selection.MenuSelectionEquipes;
import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

public class ActionSelectionEquipe implements Action{
	
	private Inscriptions inscriptions;
	
	public ActionSelectionEquipe(MenuGestionEquipes menu){
		inscriptions = menu.getInscriptions();
	}
	
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

}
