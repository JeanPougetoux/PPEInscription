package tests;


import org.junit.Test;

import dialogueUtilisateur.Utilitaires;
import exceptions.ExceptionNomEquipe;
import exceptions.ExceptionRetraitPersonneEquipe;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import junit.framework.TestCase;

public class EquipeTest extends TestCase{

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Personne tony = null;
	Personne boris = null;
	Equipe lesManouches = null;
	private int falseId;
	
	public void setUp() throws Exception
	{
		super.setUp();
		tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty",getFalseId());
		boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza",getFalseId());
		lesManouches = inscriptions.createEquipe("Les Manouches",getFalseId());
	}

	@Test
	public void testEquipe() throws ExceptionNomEquipe {
		Equipe lesPatriks = inscriptions.createEquipe("Les patriks",getFalseId());
		assertEquals("Les patriks", lesPatriks.getNom());
	}
	
	@Test
	public void testDelete() throws ExceptionRetraitPersonneEquipe {
		assertEquals(true, inscriptions.getCandidats().contains(lesManouches));
		lesManouches.delete();
		assertEquals(false, inscriptions.getCandidats().contains(lesManouches));
	}

	@Test
	public void testToString() {
		assertEquals("Equipe '" + Utilitaires.getMajuscule(lesManouches.getNom()) + "'" , lesManouches.toString());
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

	public int getFalseId()
	{
		setFalseId(falseId++);
		return falseId;
	}

	public void setFalseId(int falseId)
	{
		this.falseId = falseId;
	}

}
