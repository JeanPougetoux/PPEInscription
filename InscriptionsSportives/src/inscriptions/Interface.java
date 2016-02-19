package inscriptions;

import utilitaires.EntreesSorties;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class Interface {

	public static void main(String[] args) {
		Menu menuPrincipal = new Menu("Bienvenue dans le gestionnaire de Compétitions M2L\n"
										+ "Que voulez-vous faire ?");
		Menu competitions = new Menu("Gestion des compétitions\nQue voulez-vous faire ?",
										"Gérer les compétitions", "co");
		Menu candidats = new Menu("Gestion des candidats\nQue-voulez-vous faire ?",
										"Gérer les candidats", "ca");
		menuPrincipal.ajoute(competitions);
		menuPrincipal.ajoute(candidats);
		menuPrincipal.ajouteQuitter("q");
		competitions.ajoute(new Option("Voir les compétitions", "v", new Action()
		{
			public void optionSelectionnee(){
			}
		}));
		competitions.ajoute(new Option("Ajouter une compétition", "a", new Action()
		{
			public void optionSelectionnee(){}
		}));
		competitions.ajoute(new Option("Sélectionner une compétition", "s", new Action()
		{
			public void optionSelectionnee(){
				Menu caca = new Menu("J'aime vraiment le caca",
					"Gérer les candidats", "ca");
				caca.ajouteRevenir("r");
				caca.start();}
		}));
		competitions.ajouteRevenir("r");
		candidats.ajoute(new Option("Voir les candidats", "v", new Action()
		{
			public void optionSelectionnee(){}
		}));
		candidats.ajoute(new Option("Ajouter un candidat", "a", new Action()
		{
			public void optionSelectionnee(){}
		}));
		candidats.ajoute(new Option("Sélectionner un candidat", "s", new Action()
		{
			public void optionSelectionnee(){
				Menu caca = new Menu("J'aime vraiment le caca",
					"Gérer les candidats", "ca");
				caca.ajouteRevenir("r");
				caca.start();}
		}));
		candidats.ajouteRevenir("r");
		menuPrincipal.start();
	}

}
