package domein;

import java.util.HashMap;

public class Ontwikkelingskaart {
	
	private int id;
	private int niveau; //niveau vd kaart, 1 tot en met 3
	private int prestigePunten;
	private int bonusficheId; //bonus bij bezit van ontwikkelingskaart
	private HashMap<String, Integer> aankoopprijs; // Vereiste fiches/bonussen om ontwikkelingskaart te kopen
	private String afbeelding;

	/**
	 * Constructor die alle nodige instanties initialiseert. 
	 */
	public Ontwikkelingskaart(int id, int niveau, int prestigePunten, int bonusficheId, HashMap<String, Integer> aankoopprijs, String afbeelding) {
		setId(id);
		setNiveau(niveau);
		setPrestigePunten(prestigePunten);
		setBonusficheId(bonusficheId);
		setAankoopprijs(aankoopprijs);
		setAfbeelding(afbeelding);
	}

	//region GETTERS and SETTERS
	
	/**
	 * GETTER voor niveau 
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * SETTER voor niveau 
	 */
	private void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/**
	 * GETTER voor prestigepunten 
	 */
	public int getPrestigePunten() {
		return prestigePunten;
	}

	/**
	 * SETTER voor prestigepunten 
	 */
	private void setPrestigePunten(int prestigePunten) {
		this.prestigePunten = prestigePunten;
	}

	/**
	 * GETTER voor aankoopprijs 
	 */
	public HashMap<String, Integer> getAankoopprijs() {
		return aankoopprijs;
	}

	/**
	 * SETTER voor aankoopprijs 
	 */
	private void setAankoopprijs(HashMap<String, Integer> aankoopprijs) {
		this.aankoopprijs = aankoopprijs;
	}

	/**
	 * GETTER voor bonusficheID 
	 */
	public int getBonusficheId() {
		return bonusficheId;
	}

	/**
	 * SETTER voor bonusficheID 
	 */
	private void setBonusficheId(int bonusficheId) {
		this.bonusficheId = bonusficheId;
	}

	/**
	 * GETTER voor ID 
	 */
	public int getId() {
		return id;
	}

	/**
	 * SETTER voor ID 
	 */
	private void setId(int id) {
		this.id = id;
	}

	/**
	 * GETTER voor afbeelding 
	 */
	public String getAfbeelding() {
		return afbeelding;
	}

	/**
	 * SETTER voor afbeelding 
	 */
	private void setAfbeelding(String afbeelding) {
		this.afbeelding = afbeelding;
	}
	
	//endregion
}
