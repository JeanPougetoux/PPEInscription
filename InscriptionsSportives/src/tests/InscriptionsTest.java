package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class InscriptionsTest {

	private Inscriptions inscriptions = Inscriptions.getInscriptions();
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
	Competition flechettes = inscriptions.createCompetition("flechette", null, true);
	Equipe lesManouches = inscriptions.createEquipe("Les Manouches");

	@Test
	public void testGetCompetitions() {
		assertEquals(true, inscriptions.getCompetitions().contains(flechettes));
	}

	@Test
	public void testGetCandidats() {
		assertEquals(true, inscriptions.getCandidats().contains(tony));
		assertEquals(true, inscriptions.getCandidats().contains(lesManouches));
	}

	@Test
	public void testCreateCompetition() {
		Competition billard = inscriptions.createCompetition("billard", null, true);
		assertEquals(true, inscriptions.getCompetitions().contains(billard));
	}

	@Test
	public void testCreatePersonne() {
		Personne robert = inscriptions.createPersonne("Robert", null, null);
		assertEquals(true, inscriptions.getCandidats().contains(robert));
	}

	@Test
	public void testCreateEquipe() {
		Equipe lesGorets = inscriptions.createEquipe("Les gorets");
		assertEquals(true, inscriptions.getCandidats().contains(lesGorets));
	}
}
