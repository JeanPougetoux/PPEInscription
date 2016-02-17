package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CompetitionTest {

	@Test
	public void testCompetition() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetNom() {
		String nom = "test de l'inscription";
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition(nom, null, false);
		assertEquals(nom, testCompet.getNom());
	}

	@Test
	public void testInscriptionsOuvertes() {
		LocalDate dateCloture1 = LocalDate.parse("2015-01-10");
		LocalDate dateCloture2 = LocalDate.parse("2018-04-20");
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetFaux = inscriptions.createCompetition("test", dateCloture1, false);
		Competition testCompetVrai = inscriptions.createCompetition("test", dateCloture2, false);
		assertEquals(true, testCompetVrai.inscriptionsOuvertes());
		assertEquals(false, testCompetFaux.inscriptionsOuvertes());
	}

	@Test
	public void testGetDateCloture() {
		LocalDate dateCloture1 = LocalDate.parse("2019-01-10");
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", dateCloture1, false);
		assertEquals(dateCloture1, testCompet.getDateCloture());
	}

	@Test
	public void testEstEnEquipe() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetTrue = inscriptions.createCompetition("test", null, true);
		Competition testCompetFalse = inscriptions.createCompetition("test", null, false);
		assertEquals(true, testCompetTrue.estEnEquipe());
		assertEquals(false, testCompetFalse.estEnEquipe());
	}

	@Test
	public void testSetDateCloture() {
		LocalDate dateCloture1 = LocalDate.parse("2017-01-10");
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", dateCloture1, false);
		LocalDate nouvelleDate = LocalDate.parse("2018-02-12");
		testCompet.setDateCloture(nouvelleDate);
		assertEquals(nouvelleDate, testCompet.getDateCloture());
	}

	@Test
	public void testGetCandidats() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		Personne robert = inscriptions.createPersonne("Robert", null, null);
		Personne arthur = inscriptions.createPersonne("Arthur", null, null);
		testCompet.add(tony);
		testCompet.add(robert);
		assertEquals(true, testCompet.getCandidats().contains(tony));
		assertEquals(true, testCompet.getCandidats().contains(robert));
		assertEquals(false, testCompet.getCandidats().contains(arthur));	
	}

//	TEST ADD PERSONNE
	
	@Test
	public void testAddPersonneNormal() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompet.add(tony);
		assertEquals(true, testCompet.getCandidats().contains(tony));
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddPersonneEquipe() throws Exception{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetEquipe = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), true);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompetEquipe.add(tony);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddPersonneClos() throws Exception{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetClose = inscriptions.createCompetition("test", LocalDate.parse
				("2015-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompetClose.add(tony);
	}

	// TEST ADD EQUIPE
	
	@Test
	public void testAddEquipeNormal() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), true);
		Equipe manouches = inscriptions.createEquipe("Les manouches");
		testCompet.add(manouches);
		assertEquals(true, testCompet.getCandidats().contains(manouches));
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddEquipePersonne() throws Exception{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetPersonne = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Equipe manouches = inscriptions.createEquipe("Les manouches");
		testCompetPersonne.add(manouches);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddEquipeClos() throws Exception{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetClose = inscriptions.createCompetition("test", LocalDate.parse
				("2015-02-10"), true);
		Equipe manouches = inscriptions.createEquipe("Les manouches");
		testCompetClose.add(manouches);
	}

	@Test
	public void testRemove() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompet.add(tony);
		assertEquals(true, testCompet.getCandidats().contains(tony));
		testCompet.remove(tony);
		assertEquals(false, testCompet.getCandidats().contains(tony));
	}

	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		assertEquals(true, inscriptions.getCompetitions().contains(testCompet));
		testCompet.delete();
		assertEquals(false, inscriptions.getCompetitions().contains(testCompet));
	}

	@Test
	public void testCompareTo() {
		Inscriptions inscription = Inscriptions.getInscriptions();
		Competition a = inscription.createCompetition("abcd", LocalDate.parse
				("2018-02-10"), true);
		Competition b = inscription.createCompetition("efgh", LocalDate.parse
				("2018-02-10"), true);
		assertEquals(-4, a.compareTo(b));
	}

	@Test
	public void testToString() {
		String nom = "test de l'inscription";
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition(nom, null, false);
		assertEquals(nom, testCompet.toString());
	}

}
