package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class EquipeTest {

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
	Personne boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
	Equipe lesManouches = inscriptions.createEquipe("Les Manouches");

	@Test
	public void testEquipe() {
		Equipe lesPatriks = inscriptions.createEquipe("Les patriks");
		assertEquals("Les patriks", lesPatriks.getNom());
	}
	
	@Test
	public void testDelete() {
		assertEquals(true, inscriptions.getCandidats().contains(lesManouches));
		lesManouches.delete();
		assertEquals(false, inscriptions.getCandidats().contains(lesManouches));
	}

	@Test
	public void testToString() {
		assertEquals("Equipe " + "\n" + lesManouches.getNom() + " -> inscrit à " + 
					lesManouches.getCompetitions() , lesManouches.toString());
	}

	@Test
	public void testGetMembres() {
		lesManouches.add(boris);
		lesManouches.add(tony);
		assertEquals(true, lesManouches.getMembres().contains(tony));
		assertEquals(true, lesManouches.getMembres().contains(boris));
	}

	@Test
	public void testAddPersonne() {
		lesManouches.add(tony);
		assertEquals(true, lesManouches.getMembres().contains(tony));
	}

	@Test
	public void testRemovePersonne() {
		lesManouches.add(tony);
		lesManouches.remove(tony);
		assertEquals(false, lesManouches.getMembres().contains(tony));
	}

}
