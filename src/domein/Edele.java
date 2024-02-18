package domein;

import java.util.HashMap;

public class Edele {

	private int id;
	private int prestigePunten;
	private HashMap<String, Integer> kostprijs;
	private String afbeelding;
	
	/**
	 * Constructor die alle nodige instanties initialiseert 
	 */
	public Edele(int id, int prestigePunten, HashMap<String, Integer> kostprijs, String afbeelding) {
		setId(id);
		setPrestigePunten(prestigePunten);
		setKostprijs(kostprijs);
		setAfbeelding(afbeelding);
	}
	
	//region GETTERS and SETTERS
	
	/**
	 * GETTER voor de kostprijs 
	 */
	public HashMap<String, Integer> getKostprijs() {
		return kostprijs;
	}
	
	/**
	 * SETTER voor de kostprijs
	 */
	private void setKostprijs(HashMap<String, Integer> kostprijs) {
		this.kostprijs = kostprijs;
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