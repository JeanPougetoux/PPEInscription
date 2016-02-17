package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.sun.corba.se.impl.orb.ParserTable.TestContactInfoListFactory;

import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CandidatTest {

//	@Test
//	public void testCandidat() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		assertEquals("Tony", tony.getNom());
	}

	@Test
	public void testSetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		tony.setNom("Robert");
		assertEquals("Robert", tony.getNom());
	}

	@Test
	public void testGetCompetitions() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet1 = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Competition testCompet2 = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompet1.add(tony);
		testCompet2.add(tony);
		assertEquals(true, tony.getCompetitions().contains(testCompet1));
		assertEquals(true, tony.getCompetitions().contains(testCompet2));
	}

	@Test
	public void testAdd() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompet.add(tony);
		assertEquals(true, tony.getCompetitions().contains(testCompet));
	}

	@Test
	public void testRemove() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		testCompet.add(tony);
		assertEquals(true, tony.getCompetitions().contains(testCompet));
		testCompet.remove(tony);
		assertEquals(false, tony.getCompetitions().contains(testCompet));
	}

	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne tony = inscriptions.createPersonne("Tony", null, null);
		Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		testCompet.add(tony);
		assertEquals(true, inscriptions.getCandidats().contains(tony));
		tony.delete();
		assertEquals(false, testCompet.getCandidats().contains(tony));
		assertEquals(false, inscriptions.getCandidats().contains(tony));
	}

	@Test
	public void testCompareTo() {
		Inscriptions inscription = Inscriptions.getInscriptions();
		Personne a = inscription.createPersonne("abcd", null, null);
		Personne b = inscription.createPersonne("efgh", null, null);
		assertEquals(-4, a.compareTo(b));
	}

	@Test
	public void testToString() {
		String nom = "george";
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne george = inscriptions.createPersonne(nom, null, null);
		assertEquals("\n" + nom + " -> inscrit à " + george.getCompetitions() + " membre de " + 
					george.getEquipes().toString(), george.toString());
	}

}
