package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.ExceptionMailPersonne;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class PersonneTest {

	Inscriptions inscription = Inscriptions.getInscriptions();
	Personne a = null;
	Equipe equipe = null;
	
	public void setUp() throws Exception
	{
		a = inscription.createPersonne("ecalle", "thomas", "thomasecalle@hotmail.fr");
		equipe = inscription.createEquipe("les semis-croustillants");
	}
	
	@Test
	public void testPersonne() throws ExceptionMailPersonne {
		Personne thomas = inscription.createPersonne("ecalle", "thomas", "thomasecalle@hotmail.fr");
		assertEquals("ecalle", thomas.getNom());
		assertEquals("thomas", thomas.getPrenom());
		assertEquals("thomasecalle@hotmail.fr", thomas.getMail());
		assertEquals(true, thomas.getCompetitions().isEmpty());
	}
	
	@Test
	public void testDelete() throws ExceptionRetraitPersonneEquipe {
		a.delete();
		assertEquals(false,inscription.getCandidats().contains(a));
	}

	@Test
	public void testGetPrenom() {
		assertEquals("thomas", a.getPrenom());
	}

	@Test
	public void testSetPrenom() {
		a.setPrenom("george");
		assertEquals("george",a.getPrenom());
	}

	@Test
	public void testGetMail() {
		assertEquals("thomasecalle@hotmail.fr",a.getMail());
	}

	@Test
	public void testSetMail() {
		a.setMail("test@hotmail.com");
		assertEquals("test@hotmail.com",a.getMail());
	}

	@Test
	public void testGetEquipes() {
		equipe.add(a);
		assertEquals(true,a.getEquipes().contains(equipe));
	}

}
