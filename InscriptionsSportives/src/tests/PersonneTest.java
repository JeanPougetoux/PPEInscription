package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class PersonneTest {

	Inscriptions inscription = Inscriptions.getInscriptions();
	Personne a = inscription.createPersonne("ecalle", "thomas", "thomasecalle@hotmail.fr");
	Equipe equipe = inscription.createEquipe("les semis-croustillants");
	
	@Test
	public void testDelete() {
		a.delete();
		assertEquals(false,inscription.getCandidats().contains(a));
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPersonne() {
		fail("Not yet implemented");
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

	@Test
	public void testAddEquipe() {
		fail("not yet implemented");
	}

	@Test
	public void testRemoveEquipe() {
		fail("Not yet implemented");
	}

}
