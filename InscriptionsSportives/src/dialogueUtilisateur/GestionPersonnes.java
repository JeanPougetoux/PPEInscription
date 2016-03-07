package dialogueUtilisateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class GestionPersonnes {

	private Inscriptions inscriptions;
	public GestionPersonnes(Inscriptions inscriptions){
		this.inscriptions = inscriptions;
	}
	
	/**
	 * Permet de créer le menu Personne et y ajout les différentes options
	 * ainsi que l'action 'revenir'.
	 * @return le menu Personne de type menu.
	 */
	public Menu getMenu(){
		Menu Equipe = new Menu("\nGestion des personnes\nQue-voulez-vous faire ?",
				"Gérer les personnes", "p");
		Equipe.ajoute(new Option("Détails des personnes", "v", getActionVoirPersonne()));
		Equipe.ajoute(new Option("Ajouter une personne", "a", getActionAjoutPersonne()));
		Equipe.ajoute(new Option("Sélectionner une personne", "s", getActionSelectionPersonne()));
		Equipe.ajouteRevenir("r");
		return Equipe;
	}
	
	/**
	 * Permet de voir toutes les personnes existantes.
	 * @return l'action de voir les personnes.
	 */
	public Action getActionVoirPersonne(){
		return new Action(){
			public void optionSelectionnee() {
				for(Candidat c : inscriptions.getCandidats()){
					if(c instanceof Personne){
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
						if(nom.equals("q"))
							mod = 5;
						break;
					case 2:
						prenom = EntreesSorties.getString("Veuillez saisir le prénom. 'q' pour quitter, 'r' pour revenir.");
						if(prenom.equals("r"))
							mod = mod - 2;
						else if(prenom.equals("q"))
							mod = 5;
						break;
					case 3:
						mail = EntreesSorties.getString("Veuillez saisir le mail. 'q' pour quitter, 'r' pour revenir.");
						if(mail.equals("r"))
							mod = mod - 2;
						else if(mail.equals("q"))
							mod = 5;
						break;
					}
				}while(mod < 4);
				if(mod < 5)
					inscriptions.createPersonne(nom, prenom, mail);
					System.out.println("La personne vient bien d'être créée.");
				try{
					inscriptions.sauvegarder();
				}
				catch(Exception e){
					System.out.println("Erreur lors de la sauvegarde.");
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
				Liste<Personne> menu = new Liste<Personne>("\nListe des personnes", 
						new ActionListe<Personne>()
				{
					public List<Personne> getListe()
					{
						return personnes;
					}
					public void elementSelectionne(int indice, Personne element)
					{
						getMenuSelectionPersonne(element).start();
					}
				});
				
				menu.ajouteRevenir("r");
				menu.start();
			}
		};
	}
	
	/**
	 * Fabrique et récupère le menu qui fait suite à la sélection d'une personne.
	 * Permet plusieurs options de modification de celle-ci.
	 * @return le menu personne de type Menu.
	 */
	public Menu getMenuSelectionPersonne(Personne element){
		Menu selectionPersonne = new Menu("\nGestion de la personne '" + element.getNom() + " " + element.getPrenom() +
									"'\nQue voulez-vous faire ?", "Gérer la personne", "p");
		selectionPersonne.ajoute(new Option("Voir ses équipes", "v", getActionVoirMembres(element)));
		selectionPersonne.ajoute(new Option("Ajouter à une équipe", "a", getActionAjoutEquipe(element)));
		selectionPersonne.ajoute(new Option("Supprimer d'une équipe", "s", getActionSuppressionEquipe(element)));
		selectionPersonne.ajoute(new Option("Modifier la personne", "m", getActionModificationPersonne(element, selectionPersonne)));
		selectionPersonne.ajoute(new Option("Supprimer la personne", "d", getActionSuppressionPersonne(element, selectionPersonne)));
		selectionPersonne.ajouteRevenir("r");
		return selectionPersonne;
	}	
	
	/**
	 * Permet de voir toutes les équipes dont la personne est membre.
	 * @param la personne sélectionnée.
	 * @return l'action de voir les équipes.
	 */
	public Action getActionVoirMembres(final Personne personne){
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
	public Action getActionAjoutEquipe(final Personne personne){
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
						try{
							inscriptions.sauvegarder();
						}
						catch(Exception e){
							System.out.println("Erreur lors de la sauvegarde");
						}
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
	public Action getActionSuppressionEquipe(final Personne personne){
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
							try{
								inscriptions.sauvegarder();
							}
							catch(Exception e){
								System.out.println("Erreur lors de la sauvegarde");
							}
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
	public Action getActionSuppressionPersonne(final Personne personne, final Menu selection){
		return new Action(){
			public void optionSelectionnee(){
				char reponse = SaisiesConsole.saisieSuppression("la personne");
				if(reponse == 'o'){
					selection.setRetourAuto(true);
					personne.delete();
					System.out.println("Personne bien effacée.");
				}
				try {
					inscriptions.sauvegarder();
				} catch (IOException e) {
					System.out.println("Sauvegarde impossible.");
				}
			}
		};
	}
	
	public Action getActionModificationPersonne(final Personne personne, final Menu selection){
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
				try{
					inscriptions.sauvegarder();
				}
				catch(Exception e){
					System.out.println("Erreur lors de la sauvegarde.");
				}
			}
		};
	}
}
