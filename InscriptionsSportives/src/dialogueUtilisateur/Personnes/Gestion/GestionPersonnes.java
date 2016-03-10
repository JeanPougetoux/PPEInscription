package dialogueUtilisateur.Personnes.Gestion;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.Utilitaires;
import dialogueUtilisateur.Personnes.Selection.SelectionPersonnes;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class GestionPersonnes extends Menu{

	private Inscriptions inscriptions;
	public GestionPersonnes(Inscriptions inscriptions){
		super("\nGestion des personnes\nQue-voulez-vous faire ?",
				"Gérer les personnes", "p");
		this.inscriptions = inscriptions;
		this.ajoute(new Option("Détails des personnes", "v", getActionVoirPersonne()));
		this.ajoute(new Option("Ajouter une personne", "a", getActionAjoutPersonne()));
		this.ajoute(new Option("Sélectionner une personne", "s", getActionSelectionPersonne()));
		this.ajouteRevenir("r");
	}
	
	/**
	 * Permet de voir toutes les personnes existantes.
	 * @return l'action de voir les personnes.
	 */
	public Action getActionVoirPersonne(){
		return new Action(){
			public void optionSelectionnee() {
				boolean nul = true;
				for(Candidat c : inscriptions.getCandidats()){
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
			
		};
	}
	
	/**
	 * Permet l'ajout d'une nouvelle personne.
	 * @return l'action d'ajout.
	 */
	public Action getActionAjoutPersonne(){
		return new Action(){
			public void optionSelectionnee(){
				int mod = 0;
				String nom = "", prenom = "", mail = "";
				do{
					mod++;
					switch(mod){
					case 1:
						nom = EntreesSorties.getString("Veuillez saisir le nom. 'q' pour quitter.");
						mod = Utilitaires.getMod(mod, nom, false);
						break;
					case 2:
						prenom = EntreesSorties.getString("Veuillez saisir le prénom. 'q' pour quitter, 'r' pour revenir.");
						mod = Utilitaires.getMod(mod, prenom, false);
						break;
					case 3:
						mail = EntreesSorties.getString("Veuillez saisir le mail. 'q' pour quitter, 'r' pour revenir.");
						mod = Utilitaires.getMod(mod, mail, false);
						break;
					}
				}while(mod < 4);
				if(mod < 5){
					inscriptions.createPersonne(nom, prenom, mail);
					System.out.println("\nLa personne vient bien d'être créée.");
					Utilitaires.sauvegarde(inscriptions);
				}
			}
		};
	}

	/**
	 * Permet de sélectionner une personne pour ensuite appeller le menu
	 * permettant d'effectuer une action sur cette personne.
	 * @return l'action appelant le menu gestion d'une personne.
	 */
	public Action getActionSelectionPersonne(){
		return new Action(){
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
						new SelectionPersonnes(inscriptions, element).start();
					}
				});
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
}
