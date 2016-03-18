package dialogueUtilisateur.Personnes.Selection;

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
 * Construit l'action permettant de supprimer la personne d'une équipe.
 * @author Jean
 *
 */
public class ActionSuppressionEquipe implements Action{

	private Inscriptions inscriptions;
	private Personne personne;

	public ActionSuppressionEquipe(MenuSelectionPersonnes menu){
		inscriptions = menu.getInscriptions();
		personne = menu.getPersonne();
	}
	public void optionSelectionnee(){
		if(personne.getEquipes().isEmpty())
			System.out.println("\nLa personne n'est membre d'aucune équipe.");
		else{
			final ArrayList<Equipe> equipes = new ArrayList<Equipe>();
			for(Equipe e : personne.getEquipes()){
				equipes.add(e);
			}
			Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes :\n", 
					new ActionListe<Equipe>(){
				public List<Equipe> getListe()
				{
					return equipes;
				}
				public void elementSelectionne(int indice, Equipe element)
				{
					
						element.remove(personne);
						System.out.println(personne.toString() + " bien supprimé de l'équipe '"
								+ element.getNom() + "'");
					
					
					Utilitaires.sauvegarde(inscriptions);
				}
			});
			menu.ajouteRevenir("r");
			menu.start();
		}
	}
}
