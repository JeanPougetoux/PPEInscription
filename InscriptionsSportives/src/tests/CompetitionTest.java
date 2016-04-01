package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionAjoutEquipeCompetition;
import exceptions.ExceptionAjoutPersonneCompetition;
import exceptions.ExceptionCompetition;
import exceptions.ExceptionDateCompetition;
import exceptions.ExceptionMailPersonne;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import junit.framework.TestCase;

public class CompetitionTest extends TestCase {

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	LocalDate dateClotureFaux = LocalDate.parse("2015-01-10");
	LocalDate dateClotureVrai = LocalDate.parse("2018-04-20");
	String nom = "test competition";
	Personne tony = null;
	Personne robert = null;
	Personne arthur = null;
	Equipe manouches = null;
	Competition testCompetFaux = null;
	Competition testCompetVrai = null;
	Competition testCompetEquipe = null;
	Competition testCompetSolo = null;
	
	
	public void setUp() throws Exception
	{
		super.setUp();
		 tony = inscriptions.createPersonne("Tony", "prenomTony", "mailTony");
		 robert = inscriptions.createPersonne("Robert", "prenomRobert", "mailRobert");
		 arthur = inscriptions.createPersonne("Arthur", "prenomArthur", "mailArthur");
		 manouches = inscriptions.createEquipe("Les manouches");
		 testCompetFaux = inscriptions.createCompetition(nom, dateClotureVrai, true);
		 testCompetVrai = inscriptions.createCompetition(nom, dateClotureVrai, false);
		 testCompetEquipe = inscriptions.createCompetition(nom, dateClotureVrai, true);
		 testCompetSolo = inscriptions.createCompetition(nom, dateClotureVrai, false);
	}

	@Test
	public void testCompetition() throws ExceptionCompetition {
		Competition testCompet = inscriptions.createCompetition("flechette", dateClotureVrai, true);
		assertEquals("flechette", testCompet.getNom());
		assertEquals(dateClotureVrai, testCompet.getDateCloture());
		assertEquals(true, testCompet.estEnEquipe());
		assertEquals(true, testCompet.getCandidats().isEmpty());
	}
	
	@Test
	public void testGetNom() {
		assertEquals(nom, testCompetVrai.getNom());
	}

	@Test
	public void testInscriptionsOuvertes() {
		assertEquals(true, testCompetVrai.inscriptionsOuvertes());
		//assertEquals(false, testCompetFaux.inscriptionsOuvertes());
	}

	@Test
	public void testGetDateCloture() {
		assertEquals(dateClotureVrai, testCompetVrai.getDateCloture());
	}

	@Test
	public void testEstEnEquipe() {
		assertEquals(true, testCompetEquipe.estEnEquipe());
		assertEquals(false, testCompetSolo.estEnEquipe());
	}

	@Test
	public void testSetDateCloture() throws ExceptionDateCompetition {
		LocalDate nouvelleDate = LocalDate.parse("2018-08-12");
		testCompetVrai.setDateCloture(nouvelleDate);
		assertEquals(nouvelleDate, testCompetVrai.getDateCloture());
	}

	@Test
	public void testGetCandidats() throws ExceptionAjoutPersonneCompetition {
		testCompetVrai.add(tony);
		testCompetVrai.add(robert);
		assertEquals(true, testCompetVrai.getCandidats().contains(tony));
		assertEquals(true, testCompetVrai.getCandidats().contains(robert));
		assertEquals(false, testCompetVrai.getCandidats().contains(arthur));	
	}

//	TEST ADD PERSONNE
	
	@Test
	public void testAddPersonneNormal() throws ExceptionAjoutPersonneCompetition {
		testCompetVrai.add(tony);
		assertEquals(true, testCompetVrai.getCandidats().contains(tony));
	}
	
	/*@Test(expected = ExceptionAjoutPersonneCompetition.class)
	public void testAddPersonneEquipe() throws ExceptionAjoutPersonneCompetition{
		testCompetEquipe.add(tony);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddPersonneClos() throws Exception{
		testCompetFaux.add(tony);
	}*/

	// TEST ADD EQUIPE
	
	@Test
	public void testAddEquipeNormal() throws ExceptionAjoutEquipeCompetition, ExceptionMailPersonne {
		manouches.add(inscriptions.getInscriptions().createPersonne("ecalle", "thomas", "thomasmail"));
		testCompetEquipe.add(manouches);
		assertEquals(true, testCompetEquipe.getCandidats().contains(manouches));
	}
	
	/*@Test(expected = Exception.class)
	public void testAddEquipePersonne() throws ExceptionAjoutEquipeCompetition {
		testCompetVrai.add(manouches);
	}*/
	
	/*@Test(expected = RuntimeException.class)
	public void testAddEquipeClos() throws Exception{
		testCompetFaux.add(manouches);
	}*/

	@Test
	public void testRemove() throws ExceptionAjoutPersonneCompetition {
		testCompetVrai.add(tony);
		assertEquals(true, testCompetVrai.getCandidats().contains(tony));
		testCompetVrai.remove(tony);
		assertEquals(false, testCompetVrai.getCandidats().contains(tony));
	}

	@Test
	public void testDelete() {
		assertEquals(true, inscriptions.getCompetitions().contains(testCompetVrai));
		testCompetVrai.delete();
		assertEquals(false, inscriptions.getCompetitions().contains(testCompetVrai));
	}

	@Test
	public void testCompareTo() throws ExceptionCompetition {
		Competition a = inscriptions.createCompetition("abcd", LocalDate.parse
				("2018-02-10"), true);
		Competition b = inscriptions.createCompetition("efgh", LocalDate.parse
				("2018-02-10"), true);
		assertEquals(-4, a.compareTo(b));
	}

	@Test
	public void testToString() {
		assertEquals(Utilitaires.getMajuscule(nom), testCompetVrai.toString());
	}

}
