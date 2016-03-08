package dialogueUtilisateur;

import java.util.ArrayList;
import java.util.List;

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

public class SelectionPersonnes {
	
	public Inscriptions inscriptions;
	private Personne personne;
	private Menu selectionPersonne;
	
	
	public SelectionPersonnes(Inscriptions inscriptions, Personne personne){
		this.inscriptions = inscriptions;
		this.personne = personne;
	}
	
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une personne.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu personne de type Menu.
	 */
	public Menu getMenuSelectionPersonne(){
		Menu selectionPersonne = new Menu("\nGestion de la personne '" + personne.getNom() + " " + personne.getPrenom() +
									"'\nQue voulez-vous faire ?", "Gérer la personne", "p");
		selectionPersonne.ajoute(new Option("Voir ses équipes", "v", getActionVoirMembres()));
		selectionPersonne.ajoute(new Option("Ajouter à une équipe", "a", getActionAjoutEquipe()));
		selectionPersonne.ajoute(new Option("Supprimer d'une équipe", "s", getActionSuppressionEquipe()));
		selectionPersonne.ajoute(new Option("Modifier la personne", "m", getActionModificationPersonne()));
		selectionPersonne.ajoute(new Option("Supprimer la personne", "d", getActionSuppressionPersonne()));
		selectionPersonne.ajouteRevenir("r");
		return selectionPersonne;
	}	
	
	/**
	 * Permet de voir toutes les équipes dont la personne est membre.
	 * @param la personne sélectionnée.
	 * @return l'action de voir les équipes.
	 */
	public Action getActionVoirMembres(){
		return new Action(){
			public void optionSelectionnee(){
				if(personne.getEquipes().isEmpty()){
					System.out.println("La personne n'est membre d'aucune équipe.");
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
	 * @param la personne sélectionnée.
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
				Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes", 
						new ActionListe<Equipe>(){
					public List<Equipe> getListe()
					{
						return equipes;
					}
					public void elementSelectionne(int indice, Equipe element)
					{
						element.add(personne);
						System.out.println(personne.getNom() + " " + personne.getPrenom() + " bien ajouté à l'équipe '"
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
	 * @param la personne sélectionnée.
	 * @return l'action de supprimer la personne.
	 */
	public Action getActionSuppressionEquipe(){
		return new Action(){
			public void optionSelectionnee(){
				if(personne.getEquipes().isEmpty())
					System.out.println("La personne n'est membre d'aucune équipe.");
				else{
					final ArrayList<Equipe> equipes = new ArrayList<Equipe>();
					for(Equipe e : personne.getEquipes()){
						equipes.add(e);
					}
					Liste<Equipe> menu = new Liste<Equipe>("\nListe des équipes", 
							new ActionListe<Equipe>(){
						public List<Equipe> getListe()
						{
							return equipes;
						}
						public void elementSelectionne(int indice, Equipe element)
						{
							element.remove(personne);
							System.out.println(personne.getNom() + " " + personne.getPrenom() + " bien supprimé de l'équipe '"
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
	 * @param la personne sélectionnée.
	 * @param le menu.
	 * @return l'action de supprimer la personne.
	 */
	public Action getActionSuppressionPersonne(){
		return new Action(){
			public void optionSelectionnee(){
				char reponse = SaisiesConsole.saisieSuppression("la personne");
				if(reponse == 'o'){
					selectionPersonne.setRetourAuto(true);
					personne.delete();
					System.out.println("Personne bien effacée.");
				}
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
	
	public Action getActionModificationPersonne(){
		return new Action(){
			public void optionSelectionnee(){
				selectionPersonne.setRetourAuto(true);
				int mod = 0;
				String nom = "", prenom = "", mail = "";
				do{
					mod++;
					switch(mod){
					case 1:
						nom = EntreesSorties.getString("\nVeuillez saisir le nouveau nom. Ancien nom : " + 
											personne.getNom() + "\n'q' pour quitter, laissez vide pour ne rien changer.");
						if(nom.equals("q"))
							mod = 4;
						else if(!nom.isEmpty()){
							personne.setNom(nom);
							System.out.println("Le nom est bien changé en : " + personne.getNom());
						}
						break;
					case 2:
						prenom = EntreesSorties.getString("\nVeuillez saisir le prénom. Ancien prénom : " +
											personne.getPrenom() + "\n'q' pour quitter, 'r' pour revenir,"
													+ " laissez vide pour ne rien changer.");
						if(prenom.equals("r"))
							mod = mod - 2;
						else if(prenom.equals("q"))
							mod = 4;
						else if(!nom.isEmpty()){
							personne.setPrenom(prenom);
							System.out.println("Le prénom a bien été changé en : " + personne.getPrenom());
						}
						break;
					case 3:
						mail = EntreesSorties.getString("\nVeuillez saisir le mail. Ancien mail : " +
											personne.getMail() + "\n'q' pour quitter, 'r' pour revenir,"
													+ " laissez vide pour ne rien changer.");
						if(mail.equals("r"))
							mod = mod - 2;
						else if(mail.equals("q"))
							mod = 4;
						else if(!mail.isEmpty()){
							personne.setMail(mail);
							System.out.println("Le mail a bien été changé en : " + personne.getMail());
						}
						break;
					}
				}while(mod < 4);
				Utilitaires.sauvegarde(inscriptions);
			}
		};
	}
}
