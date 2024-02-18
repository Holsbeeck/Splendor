package domein;

import exceptions.OngeldigeNaamException;

public class Edelsteenfiche {

	private String naam;
	private String kleur;

	/**
	 * Constructor die alle nodige instanties initialiseert. 
	 */
	public Edelsteenfiche(String naam, String kleur) {
		setNaam(naam);
		setKleur(kleur);
	}
	
	//region GETTERS and SETTERS
	
	/**
	 * GETTER voor naam
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * SETTER voor naam 
	 */
	private void setNaam(String naam) {
		
		if(naam == null || naam.isBlank()) {
			throw new OngeldigeNaamException("ESLegenaam");
		}
		
		this.naam = naam;
	}

	/**
	 * GETTER voor kleur 
	 */
	public String getKleur() {
		return kleur;
	}

	/**
	 * SETTER voor kleur
	 */
	private void setKleur(String kleur) {
		
		if(kleur == null || kleur.isBlank()) {
			throw new OngeldigeNaamException("ESLegenaam");
		}
		
		this.kleur = kleur;
	}	

	//endregion
}
