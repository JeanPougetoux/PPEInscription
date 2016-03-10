package dialogueUtilisateur.Personnes.Selection;

import java.util.ArrayList;
import java.util.List;

import dialogueUtilisateur.SaisiesConsole;
import dialogueUtilisateur.Utilitaires;
import inscriptions.Candidat;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class SelectionPersonnes extends Menu{
	
	public Inscriptions inscriptions;
	private Personne personne;
	
	
	public SelectionPersonnes(Inscriptions inscriptions, Personne personne){
		super("\nGestion de la personne '" + personne.toString() +
				"'\nQue voulez-vous faire ?", "Gérer la personne", "p");
		this.inscriptions = inscriptions;
		this.personne = personne;
		this.ajoute(new Option("Voir ses équipes", "v", getActionVoirMembres()));
		this.ajoute(new Option("Ajouter à une équipe", "a", getActionAjoutEquipe()));
		this.ajoute(new Option("Supprimer d'une équipe", "s", getActionSuppressionEquipe()));
		this.ajoute(new Option("Modifier la personne", "m", getActionModificationPersonne(this)));
		this.ajoute(new Option("Supprimer la personne", "d", getActionSuppressionPersonne(this)));
		this.ajouteRevenir("r");
	}
	
	/**
	 * Permet de voir toutes les équipes dont la personne est membre.
	 * @return l'action de voir les équipes.
	 */
	public Action getActionVoirMembres(){
		return new Action(){
			public void optionSelectionnee(){
				if(personne.getEquipes().isEmpty()){
					System.out.println("\nLa personne n'est membre d'aucune équipe.");
				}
				else{
					int i = 1;
					for(Equipe e : personne.getEquipes()){
						System.out.println("\n" + i + " : " + e.getNom() + "\n");
						i++;
					}
				}
			}
		};
	}
	
	/**
	 * Permet d'ajout la personne dans une équipe.
	 * @return l'action d'ajouter la personne.
	 */
	public Action getActionAjoutEquipe(){
		return new Action(){
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
		};
	}
	
	/**
	 * Permet de supprimer la personne d'une équipe
	 * @return l'action de supprimer la personne.
	 */
	public Action getActionSuppressionEquipe(){
		return new Action(){
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
		};
	}
	
	/**
	 * Permet de supprimer la personne.
	 * @param selection
	 * @return l'action de supprimer la personne.
	 */
	public Action getActionSuppressionPersonne(final SelectionPersonnes selection){
		return new Action(){
			public void optionSelectionnee(){
				char reponse = SaisiesConsole.saisieSuppression("la personne");
				if(reponse == 'o'){
					selection.setRetourAuto(true);
					personne.delete();
					System.out.println("\nPersonne bien effacée.");
				}
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
	
	/**
	 * Permet de modifier chacun des champs pour une personne
	 * @param selection
	 * @return l'action de modification
	 */
	public Action getActionModificationPersonne(final SelectionPersonnes selection){
		return new Action(){
			public void optionSelectionnee(){
				selection.setRetourAuto(true);
				int mod = 0;
				String nom = "", prenom = "", mail = "";
				do{
					mod++;
					switch(mod){
					case 1:
						nom = EntreesSorties.getString("\nVeuillez saisir le nouveau nom. Ancien nom : " + 
											personne.getNom() + "\n'q' pour quitter, laissez vide pour ne rien changer.");
						mod = Utilitaires.getMod(mod, nom, true);
						if(!nom.isEmpty() && mod == 1){
							personne.setNom(nom);
							System.out.println("Le nom est bien changé en : " + personne.getNom());
						}
						break;
					case 2:
						prenom = EntreesSorties.getString("\nVeuillez saisir le prénom. Ancien prénom : " +
											personne.getPrenom() + "\n'q' pour quitter, 'r' pour revenir,"
													+ " laissez vide pour ne rien changer.");
						mod = Utilitaires.getMod(mod, prenom, true);
						if(!prenom.isEmpty() && mod == 2){
							personne.setPrenom(prenom);
							System.out.println("Le prénom a bien été changé en : " + personne.getPrenom());
						}
						break;
					case 3:
						mail = EntreesSorties.getString("\nVeuillez saisir le mail. Ancien mail : " +
											personne.getMail() + "\n'q' pour quitter, 'r' pour revenir,"
													+ " laissez vide pour ne rien changer.");
						mod = Utilitaires.getMod(mod, mail, true);
						if(!mail.isEmpty() && mod == 3){
							personne.setMail(mail);
							System.out.println("Le mail a bien été changé en : " + personne.getMail());
						}
						break;
					}
				}while(mod < 5);
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
}
