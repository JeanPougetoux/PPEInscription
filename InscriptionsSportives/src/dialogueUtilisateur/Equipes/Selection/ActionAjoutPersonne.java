package dialogueUtilisateur.Equipes.Selection;

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
 * Construit l'action qui permet d'ajouter une personne à une équipe.
 * @author Jean
 *
 */
public class ActionAjoutPersonne implements Action{
	
	private Inscriptions inscriptions;
	private Equipe equipe;

	public ActionAjoutPersonne(MenuSelectionEquipes menu){
		inscriptions = menu.getInscriptions();
		equipe = menu.getEquipe();
	}
	
	public void optionSelectionnee() {
		final ArrayList<Personne> personnes = new ArrayList<Personne>();
		for(Candidat c : inscriptions.getCandidats()){
			if(c instanceof Personne && !equipe.getMembres().contains(c)){
				personnes.add((Personne) c);
			}
		}
		Liste<Personne> menu = new Liste<Personne>("\nListe des personnes :\n", 
				new ActionListe<Personne>(){
			public List<Personne> getListe()
			{
				return personnes;
			}
			public void elementSelectionne(int indice, Personne element)
			{
				equipe.add(element);
				System.out.println(element.getPrenom() + " " + element.getNom() + " bien ajouté à l'équipe.");
				Utilitaires.sauvegarde(inscriptions);
			}
		});
		
		menu.ajouteRevenir("r");
		menu.start();
	}

}
