package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionAjoutPersonneCompetition;
import exceptions.ExceptionMailPersonne;
import exceptions.ExceptionNomEquipe;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import junit.framework.TestCase;

public class CandidatTest  extends TestCase{

	private String nomRobert = "Robert";
	private String nomTony = "Tony";
	private Inscriptions inscriptions;
	private Personne tony ;
	private Competition testCompet ;
	private Competition testCompet2 ;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		inscriptions = Inscriptions.getInscriptions();
		tony = inscriptions.createPersonne(nomTony, "lala", "azerty");
		testCompet = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
		testCompet2 = inscriptions.createCompetition("test", LocalDate.parse
				("2018-02-10"), false);
	}
	
	
	@Test
	public void testCandidat() throws Exception {
		
		Personne jean = inscriptions.createPersonne("pougetoux", "jean", "spyroo913@hotmail.com");
		assertEquals("pougetoux", jean.getNom());
		
	}
	
	@Test
	public void testGetNom() throws Exception {
		assertEquals(nomTony, tony.getNom());
	}

	@Test
	public void testSetNom() throws ExceptionNomEquipe {
		tony.setNom(nomRobert);
		assertEquals(nomRobert, tony.getNom());
	}

	@Test
	public void testGetCompetitions() throws ExceptionAjoutPersonneCompetition {
		testCompet.add(tony);
		testCompet2.add(tony);
		assertEquals(true, tony.getCompetitions().contains(testCompet));
		assertEquals(true, tony.getCompetitions().contains(testCompet2));
	}

	@Test
	public void testDelete() throws Exception {
		testCompet.add(tony);
		assertEquals(true, inscriptions.getCandidats().contains(tony));
		tony.delete();
		assertEquals(false, testCompet.getCandidats().contains(tony));
		assertEquals(false, inscriptions.getCandidats().contains(tony));
	}

	@Test
	public void testCompareTo() throws ExceptionMailPersonne {
		Personne a = inscriptions.createPersonne("abcd", "test", "a");
		Personne b = inscriptions.createPersonne("efgh", "test", "b");
		assertEquals(-1, a.compareTo(b));
	}

	@Test
	public void testToString() throws Exception {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Equipe equipe = inscriptions.createEquipe("les manouches");
		assertEquals(Utilitaires.getMajuscule("Equipe '" + Utilitaires.getMajuscule(equipe.getNom()) + "'")
				, equipe.toString());
		Utilitaires.getMajuscule(equipe.toString());
		
	}

}
