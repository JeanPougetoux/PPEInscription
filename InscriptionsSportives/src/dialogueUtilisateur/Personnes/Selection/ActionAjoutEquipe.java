package dialogueUtilisateur.Personnes.Selection;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Utilitaires;
import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;

/**
 * Construit l'action permettant d'ajouter la personne dans une équipe.
 * @author Jean
 *
 */
public class ActionAjoutEquipe implements Action{

	private Inscriptions inscriptions;
	private Personne personne;

	public ActionAjoutEquipe(MenuSelectionPersonnes menu){
		inscriptions = menu.getInscriptions();
		personne = menu.getPersonne();
	}
	public void optionSelectionnee(){
		final ArrayList<Equipe> equipes = new ArrayList<Equipe>();
		for(Candidat c : inscriptions.getCandidats()){
			if(c instanceof Equipe && !personne.getEquipes().contains(c)){
				equipes.add((Equipe) c);
			}
		}
		Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes :\n", 
				new ActionListe<Equipe>(){
			public List<Equipe> getListe()
			{
				return equipes;
			}
			public void elementSelectionne(int indice, Equipe element)
			{
				element.add(personne);
				System.out.println(personne.toString() + " bien ajouté à l'équipe '"
						+ element.getNom() + "'");
				Utilitaires.sauvegarde(inscriptions);
			}
		});
		menu.ajouteRevenir("r");
		menu.start();
	}
}
