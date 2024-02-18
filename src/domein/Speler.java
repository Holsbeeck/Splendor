package domein;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import exceptions.EdelsteenficheAantalTeWeinigException;
import exceptions.EdelsteenficheGeenInBezitException;
import exceptions.OngeldigAantalException;
import exceptions.OngeldigEdelsteenficheException;
import exceptions.OngeldigGeboortejaarException;
import exceptions.OngeldigeNaamException;

public class Speler {

	private String gebruikersnaam;
	private int geboortejaar;
	private boolean isStartendeSpeler;
	
	private int score; 
	private List<Ontwikkelingskaart> kaartenInBezit;
	private HashMap<Edelsteenfiche, Integer> edelstenenInBezit;
	private List<Edele> edelenInBezit;
	
	/**
	 * Constructor die alle nodige instanties initialiseert
	 */ 
	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setScore(0);
		setIsStartendeSpeler(false);
		this.kaartenInBezit = new ArrayList<Ontwikkelingskaart>();
		this.edelstenenInBezit = new HashMap<Edelsteenfiche,Integer>();
		this.edelenInBezit = new ArrayList<Edele>();
	}

	/**UC2:
	 * Berekent de score van de speler op basis van de prestigepunten die de kaarten en edelen 
	 * in zijn/haar bezit hebben.
	 */
	private void berekenScore() {
		
		// steeds van 0 beginnen
		int score = 0;
		
		if (kaartenInBezit.size() > 0) {
			
			// voor elke kaart in bezit => prestige punten bij score optellen
			for (Ontwikkelingskaart kaart : kaartenInBezit) {
				score += kaart.getPrestigePunten(); 
			}
		}
			
		if (edelenInBezit.size() > 0) {
			
			// voor elke edele in bezit => prestige punten bij score optellen
			for (Edele edele : edelenInBezit) {
				score += edele.getPrestigePunten();  
			}
		
		}
			
		// totaal score wordt steeds opnieuw berekend en nieuwe score wordt gezet
		setScore(score);
	}
	
	/**UC4:
	 * voegt een aantal edelsteenfiches toe aan speler
	 * @param steen - edelsteenfiche
	 * @param aantal - aantal dat toegevoegd moet worden
	 */
	public void kiesEdelsteenfiches(Edelsteenfiche steen, int aantalKeuze) {
		
		var heeftEdelsteen = this.edelstenenInBezit.containsKey(steen);
		
		if(heeftEdelsteen) {
			var aantal = this.edelstenenInBezit.get(steen);
			this.edelstenenInBezit.put(steen, aantal + aantalKeuze);
		}else {
			this.edelstenenInBezit.put(steen, aantalKeuze);
		}
	}
	
	/**UC4:
	 * neemt een aantal edelsteenfiches weg van speler
	 * @param steen - edelsteenfiche
	 * @param aantal - aantal dat toegevoegd moet worden
	 */
	public void kiesTeruggaveEdelsteenfiches(Edelsteenfiche steen, int aantalKeuze) {
		
		var heeftEdelsteen = this.edelstenenInBezit.containsKey(steen);
		
		if(heeftEdelsteen) {
			var aantal = this.edelstenenInBezit.get(steen);
			
			if(aantal < aantalKeuze) {
				throw new EdelsteenficheAantalTeWeinigException("EdelsteenficheTeWeinig");
			}
			
			this.edelstenenInBezit.put(steen, aantal - aantalKeuze);
		}else {
			throw new EdelsteenficheGeenInBezitException("EdelsteenficheGeenInBezit");
		}
	}
	
	/**UC4:
	 * Controleert of speler te veel edelsteenfiches in bezit heeft. (max 10)
	 * @return boolean - of speler te veel fiches heeft in totaal
	 */
	public boolean heeftTeVeelEdelsteenfiches() {
		var aantal = 0;
		
		for(Edelsteenfiche e : this.edelstenenInBezit.keySet()) {
			aantal += this.edelstenenInBezit.get(e);
		}
		
		return aantal > 10;
	}
	
	/**DEMO:
	 * Geeft de optie om score toe te voegen vanuit de GUI 
	 * @param score - hoeveelheid score dat moet toegevoegd worden 
	 */
	public void geefScorePunten(int score) {
		int huidigeScore = getScore();
		score += huidigeScore;
		setScore(score);
	}
	
	/**
	 * Haalt de bonusfiches op uit de kaarten in bezit
	 * @return HashMap: type bonusfiche en aantal
	 */
	public HashMap<String,Integer> geefBonusfichesInBezit(){
		
		var bonusfiches = new HashMap<String,Integer>();
		
		for(Ontwikkelingskaart k : kaartenInBezit) {
			
			var type = "";
			switch(k.getBonusficheId()) {
				case 1: type = "smaragd";break;
				case 2: type = "diamant";break;
				case 3: type = "saffier";break;
				case 4: type = "onyx";break;
				case 5: type = "robijn";break;
			}
			
			// bonusfiche komt reeds voor in lijst => +1
			if(bonusfiches.containsKey(type)) {
				var aantal = bonusfiches.get(type);
				bonusfiches.put(type, ++aantal);
			}else { // bonusfiche komt niet voor in lijst => 1
				bonusfiches.put(type, 1);
			}
		}
		
		return bonusfiches;
	}
	
	//region GETTERS and SETTERS
	
	/**UC1:
	 * SETTER voor gebruikersnaam van speler.<br/>
	 * Deze naam moet aan een paar regels voldoen:<br/>
	 * <ul>
	 * <li>Gebruikersnaam mag niet null of een lege String zijn</li>
	 * <li>De gebruikersnaam moet altijd starten met een letter (groot of klein)</li>
	 * <li>Gebruikersnaam mag spaties of _ bevatten maar geen andere speciale tekens, enkel cijfers en letters.</li>
	 * </ul>
	 * @param gebruikersnaam - gebruikersnaam van speler
	 */
	private void setGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException {
		
		// Gebruikersnaam mag spaties of _ bevatten maar geen andere speciale tekens, enkel cijfers en letters.
		// De gebruikersnaam moet altijd starten met een letter (groot of klein).

		if (gebruikersnaam == null || gebruikersnaam.isBlank()) {
			throw new OngeldigeNaamException("legeGebruiker");
		}
		if (!Character.isLetter(gebruikersnaam.charAt(0))) {
			throw new OngeldigeNaamException("startGebruiker");
		}
		if (!gebruikersnaam.matches("^[a-zA-Z][\\w\\s]*$")) {
			throw new OngeldigeNaamException("illigaleGebruiker");
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	/**UC1:
	 * SETTER voor geboortejaar.<br/>
	 * Dit geboortejaar moet voldoen aan:<br/>
	 * <ul>
	 * <li>Een geldig geboortejaar</li>
	 * <li>Lager zijn dan (huidig jaar - 6): speler moet ouder zijn dan 6 jaar</li>
	 * </ul>
	 * @param geboortejaar - geboortejaar van speler
	 */
	private void setGeboortejaar(int geboortejaar) {
		
		// Geboortejaar moet geldig zijn en elke gebruiker is minstens 6 jaar oud

		int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
		int minimumJaar = huidigJaar - 6;
		if (geboortejaar >= 1900 && geboortejaar <= minimumJaar) {
			this.geboortejaar = geboortejaar;
		} else {
			throw new OngeldigGeboortejaarException("invalidBirth");
		}
	}
	
	/**
	 * GETTER voor geboortejaar 
	 */
	public int getGeboortejaar() {
		return geboortejaar;
	}
	
	/**
	 * GETTER voor gebruikersnaam 
	 */
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	
	/**
	 * SETTER voor score  
	 */
	private void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * GETTER voor score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * GETTER voor IsStartendeSpeler.<br/>
	 * Houdt bij of deze speler het spel mocht starten
	 */
	public boolean getIsStartendeSpeler() {
		return isStartendeSpeler;
	}

	/**
	 * SETTER voor isStartendeSpeler 
	 */
	public void setIsStartendeSpeler(boolean isStartendeSpeler) {
		this.isStartendeSpeler = isStartendeSpeler;
	}
	
	/**
	 * GETTER voor kaarten in bezit van speler 
	 */
	public List<Ontwikkelingskaart> getKaartenInBezit() {
		return kaartenInBezit;
	}
	
	/**
	 * SETTER voor kaarten in bezit van de speler
	 */
	public final void setKaartenInBezit(Ontwikkelingskaart ok) {
		kaartenInBezit.add(ok);
		// bereken score na elke kaart die toegevoegd wordt
		berekenScore();
	}
	
	/**
	 * Voegt een edele toe aan het bezit van de speler en berekent opnieuw de score.
	 *  @param edele - de edele die toegevoegd moet worden
	 */
	public final void voegEdeleInBezitToe(Edele edele) {
		edelenInBezit.add(edele);
		// bereken score na elke edele die toegevoegd wordt
		berekenScore();
	}
	
	/**
	 * GETTER voor edelsteenfiches in bezit van speler 
	 */
	public HashMap<Edelsteenfiche, Integer> getEdelstenenInBezit() {
		return edelstenenInBezit;
	}

	/**
	 *  GETTER voor edelen in bezit van speler
	 */
	public List<Edele> getEdelenInBezit() {
		return edelenInBezit;
	}
	
	//endregion
}
