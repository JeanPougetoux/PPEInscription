package dialogueUtilisateur.Equipes.Selection;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

/**
 * Construit l'action permettant la suppression d'une personne de l'équipe.
 * @author Jean
 *
 */
public class ActionSuppressionPersonne implements Action{
	
	private Inscriptions inscriptions;
	private Equipe equipe;
	
	public ActionSuppressionPersonne(MenuSelectionEquipes menu){
		inscriptions = menu.getInscriptions();
		equipe = menu.getEquipe();
	}

	public void optionSelectionnee() {
		final ArrayList<Personne> personnes = new ArrayList<Personne>();
		for(Personne e : equipe.getMembres()){
			personnes.add(e);
		}
		Liste<Personne> menu = new Liste<Personne>("\nListe des membres :\n", 
				new ActionListe<Personne>(){
			public List<Personne> getListe()
			{
				return personnes;
			}
			public void elementSelectionne(int indice, Personne element)
			{
				 equipe.remove(element);
				System.out.println("élément correctement supprimé");
				//System.out.println(element.getPrenom() + " " + element.getNom() + " bien supprimé de l'équipe.");
					
				
				Utilitaires.sauvegarde(inscriptions);
			}
		});
		menu.ajouteRevenir("r");
		menu.start();
	}
}
