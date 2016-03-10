package dialogueUtilisateur.Personnes.Gestion;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Personnes.Selection.MenuSelectionPersonnes;
import inscriptions.Candidat;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

/**
 * Construit l'action permettant de sélectionner une personne pour ensuite appeller le menu
 * permettant d'effectuer une action sur cette personne.
 * @author Jean
 *
 */
public class ActionSelectionPersonne implements Action{
	
	private Inscriptions inscriptions;

	public ActionSelectionPersonne(MenuGestionPersonnes menu){
		inscriptions = menu.getInscriptions();
	}

	public void optionSelectionnee(){
		final ArrayList<Personne> personnes = new ArrayList<>();
		for(Candidat e : inscriptions.getCandidats()){
			if(e instanceof Personne)
				personnes.add((Personne)e);
		}
		Liste<Personne> menu = new Liste<Personne>("\nListe des personnes :\n", 
				new ActionListe<Personne>()
		{
			public List<Personne> getListe()
			{
				return personnes;
			}
			public void elementSelectionne(int indice, Personne element)
			{
				new MenuSelectionPersonnes(inscriptions, element).start();
			}
		});
		menu.ajouteRevenir("r");
		menu.start();
	}
}
