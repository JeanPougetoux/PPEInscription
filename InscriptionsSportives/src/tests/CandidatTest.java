package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import dialogueUtilisateur.Utilitaires;
import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CandidatTest {

	String nomTony = "Tony";
	String nomRobert = "Robert";
	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Personne tony = inscriptions.createPersonne(nomTony, null, null);
	Competition testCompet = inscriptions.createCompetition("test", LocalDate.parse
			("2018-02-10"), false);
	Competition testCompet2 = inscriptions.createCompetition("test", LocalDate.parse
			("2018-02-10"), false);
	
	@Test
	public void testCandidat() {
		Personne jean = inscriptions.createPersonne("pougetoux", "jean", "spyroo913@hotmail.com");
		assertEquals("pougetoux", jean.getNom());
		
	}
	
	@Test
	public void testGetNom() {
		assertEquals(nomTony, tony.getNom());
	}

	@Test
	public void testSetNom() {
		tony.setNom(nomRobert);
		assertEquals(nomRobert, tony.getNom());
	}

	@Test
	public void testGetCompetitions() {
		testCompet.add(tony);
		testCompet2.add(tony);
		assertEquals(true, tony.getCompetitions().contains(testCompet));
		assertEquals(true, tony.getCompetitions().contains(testCompet2));
	}

	@Test
	public void testDelete() {
		testCompet.add(tony);
		assertEquals(true, inscriptions.getCandidats().contains(tony));
		tony.delete();
		assertEquals(false, testCompet.getCandidats().contains(tony));
		assertEquals(false, inscriptions.getCandidats().contains(tony));
	}

	@Test
	public void testCompareTo() {
		Personne a = inscriptions.createPersonne("abcd", null, null);
		Personne b = inscriptions.createPersonne("efgh", null, null);
		assertEquals(-4, a.compareTo(b));
	}

	@Test
	public void testToString() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne george = inscriptions.createPersonne(nomRobert, null, null);
		assertEquals(Utilitaires.getMajuscule(george.getNom())+ " " + Utilitaires.getMajuscule(george.getPrenom()), george.toString());
	}

}
