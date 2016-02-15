package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Inscriptions;

public class CompetitionTest {

	@Test
	public void testCompetition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNom() {
		fail("Not yet implemented");
	}

	@Test
	public void testInscriptionsOuvertes() {
		LocalDate dateCloture1, dateCloture2;
		dateCloture1 = LocalDate.parse("2015-01-10");
		dateCloture2 = LocalDate.parse("2018-04-20");
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompetVrai = inscriptions.createCompetition("test", dateCloture1, false);
		Competition testCompetFaux = inscriptions.createCompetition("test", dateCloture2, false);
		assertEquals(true, testCompetVrai.inscriptionsOuvertes());
		assertEquals(false, testCompetFaux.inscriptionsOuvertes());
	}

	@Test
	public void testGetDateCloture() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstEnEquipe() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDateCloture() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCandidats() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPersonne() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddEquipe() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
