package main;

import cui.SplendorApplicatie;
import domein.DomeinController;

public class StartupCLI {

	public static void main(String[] args){

		//maak een DC-object DomeinController
		DomeinController dc = new DomeinController(); 
		//maak een applicatie-object met als argument de dc
		SplendorApplicatie sa = new SplendorApplicatie(dc);
		//voer de applicatie-methode met de flow vh programma uit
		sa.startSplendorSpel(); }
		//voer de applicatie-methode met de flow vh programma uit

	}
