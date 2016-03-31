package exceptions;

import java.time.LocalDate;

public class ExceptionDateCompetition extends ExceptionPrincipale 
{

	private LocalDate date;
	public ExceptionDateCompetition(LocalDate localDate) {
		date = localDate;
	}
	
	public String toString()
	{
		return "Impossible d'avancer la compétition avant le : "+date.toString();
	}
}
