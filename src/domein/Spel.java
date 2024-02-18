package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import exceptions.EdeleNietBeschikbaarException;
import exceptions.EdelsteenficheAantalTeVeelException;
import exceptions.EdelsteenficheGeenInBezitException;
import exceptions.EdelsteenficheNietGenoegVoorraadException;
import exceptions.OngeldigAantalException;
import exceptions.OngeldigEdelsteenficheException;

public class Spel {
	
	private Speler huidigeSpeler;
	private List<Speler> spelers;
	private List<Ontwikkelingskaart> stapelNiveau1; 
	private List<Ontwikkelingskaart> stapelNiveau2; 
	private List<Ontwikkelingskaart> stapelNiveau3; 
	private HashMap<Edelsteenfiche, Integer> edelsteenfiches;
	private List<Edele> edelen;
	private int ronde = 1;
	private int beurt = 1;
	
	/**
	 * Constructor die alle nodige instanties initialiseert
	 */
	public Spel(
		List<Edelsteenfiche> edelsteenfiches, 
		List<Ontwikkelingskaart> niveau1,
		List<Ontwikkelingskaart> niveau2,
		List<Ontwikkelingskaart> niveau3) {
		
		spelers = new ArrayList<Speler>();
		setEdelsteenfiches(edelsteenfiches);
		edelen = new ArrayList<Edele>();
		setStapelNiveau1(niveau1);
		setStapelNiveau2(niveau2);
		setStapelNiveau3(niveau3);
	}
	
	/**UC1:
	 * Voegt een speler toe aan de lijst van spelers in het spel.
	 * @param speler - Speler die toegevoegd moet worden aan de lijst.
	 */
	public void voegSpelerToe(Speler speler) {
		if(speler != null)
			spelers.add(speler);
	}
	
	/**UC1:
	 * Geeft aantal spelers in het spel terug.
	 * @return int: aantal spelers in spel.
	 */
	public int geefAantalSpelers() {
		return spelers.size();
	}
	
	/**UC1:
	 * Controleert of een speler met een bepaalde gebruikersnaam en geboortejaar 
	 * in de lijst van het spel voorkomt.
	 * @param gebruikersnaam - Gebruikersnaam van de speler
	 * @param geboortejaar - Geboortejaar van de speler
	 * @return boolean: Of de speler in de lijst voorkomt of niet
	 */
	public boolean bestaatSpeler(String gebruikersnaam, int geboortejaar) {
		for (Speler speler : spelers) {
			if (speler.getGebruikersnaam().equals(gebruikersnaam) && speler.getGeboortejaar() == geboortejaar) {
				return true;
			}
		}
		return false;
	}
	
	/**UC1:
	 * Geeft alle gebruikersnamen van de deelnemende spelers terug.
	 * @return String: gebruikersnamen van spelers in spel
	 */
	public String geefDeelnemendeSpelers() {
		String deelnemendeSpelers = " ";
		for (int i = 0; i < spelers.size(); i++) {
			deelnemendeSpelers+= spelers.get(i).getGebruikersnaam() + ((i == spelers.size() -1)? "": ", ");
		}
		return deelnemendeSpelers;
	}
	
	/**UC1:
	 * Controleert of het spel een geldig aantal spelers heeft volgens DR
	 * @return boolean: of het spel een geldig aantal spelers heeft
	 */
	public boolean heeftGeldigAantalSpelers() {
		var aantalSpelers = geefAantalSpelers();
		return (aantalSpelers >= 2 && aantalSpelers <= 4);
	}
	
	/**UC1:
	 * Bepaalt de startende speler van het spel <br/><br/>
	 * Dit volgens volgende regels:<br/>
	 * <ul>
	 * <li>De jongste mag starten</li>
	 * <li>beiden even oud, dan kijken naar lengte van gebruikersnaam</li>
	 * <li>beiden even oud en gebruikersnaam even lang, dan kijken naar de gebruikersnaam omgekeerd alfabetisch</li>
	 * </ul>
	 * @return Speler: De speler die het spel mag starten
	 */
	public void bepaalStartendeSpeler() {
		// volgens DR_SPEL_STARTER
		Comparator<String> reverseComparator = Comparator.reverseOrder();
		int aantalSpelers = geefAantalSpelers();
		// starten met eerste speler in lijst als jongste
		Speler startendeSpeler = spelers.get(0);
		// starten vanaf index 1
		for(int i = 1;i < aantalSpelers;i++) {
			
			// elke volgende speler in lijst vergelijken met huidige jongste
			Speler volgendeSpeler = spelers.get(i);
			int geboortejaarJongste = startendeSpeler.getGeboortejaar();
			int geboortejaarVolgende = volgendeSpeler.getGeboortejaar();
			int lengteGebruikersnaamJongste = startendeSpeler.getGebruikersnaam().length();
			int lengteGebruikersnaamVolgende = volgendeSpeler.getGebruikersnaam().length();
			
			if(geboortejaarVolgende > geboortejaarJongste) { 
				// als volgende speler jonger is, dan deze aanduiden als jongste
				startendeSpeler = volgendeSpeler;
			}else if(geboortejaarVolgende == geboortejaarJongste && lengteGebruikersnaamVolgende > lengteGebruikersnaamJongste) { 
				// als beiden even jong, kijken naar lengte gebruikersnaam
				startendeSpeler = volgendeSpeler;
			} else if(geboortejaarVolgende == geboortejaarJongste && lengteGebruikersnaamVolgende == lengteGebruikersnaamJongste) { 
				// als beiden even jong en zelfde lengte van gebruikersnaam => gebruikersnamen omgekeerd alfabetisch vergelijken
				int resultaat = reverseComparator.compare(volgendeSpeler.getGebruikersnaam(), startendeSpeler.getGebruikersnaam());
				if(resultaat < 0) {
					startendeSpeler = volgendeSpeler;
				}
			}
		}
		
		startendeSpeler.setIsStartendeSpeler(true);
		
		setHuidigeSpeler(startendeSpeler);
	}
	
	/**UC1:
	 * Bepaalt voor elk type edelsteenfiche hoeveel er in het spel mogen zijn.
	 * Dit gebeurt op basis van het aantal spelers in het spel
	 */
	public void bepaalAantalEdelsteenfiches() {
		
		int aantalEdelstenen = switch (spelers.size()) {
			case 2 -> 4;
			case 3 -> 5;
			case 4 -> 7; 
			default -> throw new RuntimeException("SPFiche");
		};
		
		for (var e : edelsteenfiches.keySet()) {
			edelsteenfiches.put(e, aantalEdelstenen);
		}
	}
	
	/**UC2:
	 * Geeft de omgedraaide/zichtbare kaarten van een bepaalde stapel.
	 * @param niveau - niveau van stapel/kaarten
	 * @return Lijst van Ontwikkelingskaart 
	 */
	public List<Ontwikkelingskaart> geefOmgedraaideKaarten(int niveau){
		
		switch(niveau) {
			case 1: 
				return this.stapelNiveau1.stream().limit(4).toList();
			case 2:
				return this.stapelNiveau2.stream().limit(4).toList();
			case 3:
				return this.stapelNiveau3.stream().limit(4).toList();
			default:
				return new ArrayList<Ontwikkelingskaart>();
		}
	}
	
	/**UC2:
	 * Kijkt naar de score van iedere speler en als deze score groter of gelijk
	 * is aan 15, dan is deze speler een winnaar.
	 * @return Lijst van Speler: de winnaars
	 */
	public List<Speler> geefWinnaars() {


		var winnaars = this.spelers
			.stream()
			.filter(s -> (s.getScore() >= 15))
			.toList();

		//bepaal hoogste score behaald bij de spelers
		int hoogsteScore = 0;
		for (Speler speler: winnaars) {
			if (speler.getScore() > hoogsteScore)
				hoogsteScore = speler.getScore();
		}
		System.out.printf("%nHoogste score behaald: %d%n", hoogsteScore);
		
		//bepaal aantal spelers met de hoogste score
		//en voeg deze spelers toe aan de lijst spelersMetGelijkeEindscore
		List <Speler> spelersMetGelijkeEindscore = new ArrayList<>();
		int aantalSpelersMetZelfdeEindscore = 0;
		for (Speler speler: winnaars) {
			if (speler.getScore() == hoogsteScore) {
				spelersMetGelijkeEindscore.add(speler);
				aantalSpelersMetZelfdeEindscore++;
			}
		}
		System.out.printf("Aantal spelers met de maximum eindscore: %d%n", aantalSpelersMetZelfdeEindscore);
		System.out.println(spelersMetGelijkeEindscore);
		
		//uit de lijst met spelers met gelijke eindscore bepalen we het laagst aantal ontwikkelingskaarten in bezit		
		int laagsteAantalKaartenInBezit = Integer.MAX_VALUE;
		for (Speler speler : spelersMetGelijkeEindscore) {
			System.out.printf("Aantal kaarten in bezit van speler: %d%n", speler.getKaartenInBezit().size());
			if(speler.getKaartenInBezit().size() < laagsteAantalKaartenInBezit) {
				laagsteAantalKaartenInBezit = speler.getKaartenInBezit().size();
			}
		}
		System.out.printf("Laagste aantal kaarten in bezit: %d%n", laagsteAantalKaartenInBezit);
		
		//De winnaars die het laagst aantal ontwikkelingskaarten in bezit hebben worden toegevoegd aan de lijst winnaars
		List<Speler> winnaarsFiltered = new ArrayList<>();
		for (Speler speler : spelersMetGelijkeEindscore) {
			if (speler.getKaartenInBezit().size() == laagsteAantalKaartenInBezit) {
				winnaarsFiltered.add(speler);
			}
		}
		System.out.println("Winnaars: " + winnaarsFiltered);
		
		return winnaarsFiltered;
	}
	
	/**UC2:
	 * Kijkt of er spelers zijn die als winnaar aangeduid worden.
	 * @return boolean: of er winnaars zijn
	 */
	public boolean heeftWinnaars() {
		return geefWinnaars().size() > 0;
	}
	
	/**UC2:
	 * Geeft de namen van de winnaars
	 * @return String: namen van winnaars
	 */
	public String geefNamenWinnaars() {
		//omzetten vd lijst winnaars naar een String met daarin hun naam
		String naamWinnaars = " ";
		for (int i = 0; i < geefWinnaars().size(); i++) {
			naamWinnaars += geefWinnaars().get(i).getGebruikersnaam() + ((i == geefWinnaars().size() -1)? "": ", ");
		}
		return naamWinnaars;
	}
	
	/**UC3
	* Controleert of de ronde is afgelopen volgens DR_RONDE_BEURT
	* @return boolean: of iedereen zijn ronde gespeeld heeft
	*/
	public boolean isEindeRonde() {
		if(beurt % spelers.size() == 1) {
			ronde++;
			return true;
		}else {
			return false;
		}
	 }
	
	/** Extra of mss nodig in UC4
	* Geeft het rondenummer
	* @return int: nummer van de rond
	*/
	public int geefRonde() {
		return ronde;
	}
	
	/**UC3:
	 * Controleert of de edele aangekocht kan worden door de huidige speler
	 * @param edele - edele die huidige speler wilt aankopen
	 * @return boolean: of deze edele aangekocht kan worden dooe huidige speler 
	 */
	private boolean isEdeleAankoopMogelijk(Edele edele) {
		 
		int smaragd = 0;
		int diamant = 0;
		int saffier = 0;
		int onyx = 0;
		int robijn = 0;
		
		for(Ontwikkelingskaart ok : huidigeSpeler.getKaartenInBezit()) { // ontwikkelingskaarten overlopen en de bonussen berekenen
			if (ok.getBonusficheId() == 1) smaragd++ ;
			if (ok.getBonusficheId() == 2) diamant++ ;
			if (ok.getBonusficheId() == 3) saffier++ ;
			if (ok.getBonusficheId() == 4) onyx++ ;
			if (ok.getBonusficheId() == 5) robijn++ ;
		}
		
		//controlleren of speler de edele kan kopen
		return (edele.getKostprijs().get("smaragd") <=  smaragd 
				&& edele.getKostprijs().get("diamant") <= diamant
				&& edele.getKostprijs().get("saffier") <= saffier
				&& edele.getKostprijs().get("onyx") <= onyx
				&& edele.getKostprijs().get("robijn") <= robijn);
	}
	
	/**UC3:
	 * Kijkt welke edelen beschikbaar zijn om aan te kopen voor de huidige speler
	 * @return Lijst van Edelen: edelen die aangekocht kunnen worden door huidige speler 
	 */
	public List<Edele> geefBeschikbareTegelsHuidigeSpeler(){
		 
		List<Edele> beschikBareTegels = new ArrayList<Edele>(); //maakt een nieuwe List aan waarin we de beschikbare edelen stoppen
		for( Edele edele : this.edelen ) { //elke edele overlopen
			
			if (isEdeleAankoopMogelijk(edele))
				beschikBareTegels.add(edele);
		}
		
		return beschikBareTegels;
	}
	
	/**UC3
	* Speler koopt een edele, en dezelde edele wordt uit het spel verwijderd
	* @param edelen: De edele die de speler kiest
	*/
	public void kiesTegel(Edele edele) {
		if(isEdeleAankoopMogelijk(edele)) {
			huidigeSpeler.voegEdeleInBezitToe(edele); // De gekozen edele wordt toegevoegd aan de lijst edelenInBezit 
			this.edelen.remove(edele); // de gekozen edele wordt uit verwijderd uit de lijst edelen
		}else {
			throw new EdeleNietBeschikbaarException("EdeleNietBeschikbaar");
		}
	}
	
	/**UC4:
	 * Controleert of er voldoende stenen in voorraad blijven na de aanpassing
	 * @param fiche - edelsteenfiche die gecontroleerd moet worden
	 * @param aantalGekozen - aantal dat uit voorraad wordt genomen
	 * @param aantalOverblijven - aantal dat moet overblijven in voorraad na aanpassing
	 * @return boolean: of er voldoende in voorraad is
	 */
	private boolean heeftVoldoendeInVoorraad(Edelsteenfiche fiche, int aantalGekozen, int aantalOverblijven) {
		return (this.edelsteenfiches.get(fiche) - aantalGekozen) >= aantalOverblijven;
	}
	
	/**UC4:
	 * vermindert de edelsteenfiches in het spel
	 * @param fiche - edelsteenfiche die vermindert moet worden
	 * @param aantal - hoeveel er verminderd moet worden
	 */
	private void verminderEdelsteenfiche(Edelsteenfiche fiche, int aantal) {
		var aantalInSpel = this.edelsteenfiches.get(fiche);
		this.edelsteenfiches.put(fiche, aantalInSpel - aantal);
	}
	
	/**UC4:
	 * geeft de huidige speler de gekozen edelsteenfiches en vermindert dit in de voorraad van spel
	 * @param fiche - edelsteenfiche die gekozen is door huidige speler
	 * @param aantal - aantal dat gekozen is door huidige speler
	 */
	public void kiesEdelsteenfichesHuidigSpeler(Edelsteenfiche fiche, int aantal) {
		
		if(fiche == null) {
			throw new OngeldigEdelsteenficheException("EdelsteenficheNULL");
		}
		
		if(aantal < 0) {
			throw new OngeldigAantalException("OngeldigGetal");
		}
		
		// aantal max 2 en mag niet groter zijn dan wat er in voorraad is
		// als aantal = 2 dan moeten er minstens 2 overblijven in voorraad -> aangepast naar 1 edelsteenfiche pakken, en zien of er nog voldoende in voorraad zijn
		if(aantal > 2 || heeftVoldoendeInVoorraad(fiche, aantal, 0) == false) {
			throw new EdelsteenficheAantalTeVeelException("EdelsteenficheTeVeel");
		}else if (aantal == 2 && heeftVoldoendeInVoorraad(fiche, aantal, 2) == false) {
			throw new EdelsteenficheNietGenoegVoorraadException("EdelsteenficheNietGenoegVoorraad");
		} 
		
		huidigeSpeler.kiesEdelsteenfiches(fiche, aantal);
		verminderEdelsteenfiche(fiche, aantal);
	}
	
	/**UC4:
	 * vermeerder de edelsteenfiches in het spel
	 * @param fiche - edelsteenfiche die vermeerderd moet worden
	 * @param aantal - hoeveel er vermeerderd moet worden
	 */
	private void vermeerderEdelsteenfiche(Edelsteenfiche fiche, int aantal) {
		var aantalInSpel = this.edelsteenfiches.get(fiche);
		this.edelsteenfiches.put(fiche, aantalInSpel + aantal);
	}
	
	/**UC4:
	 * neemt edelsteenfiches van huidige speler en voegt deze toe aan de voorraad in spel
	 * @param fiche - edelsteenfiche die gekozen is door huidige speler
	 * @param aantal - aantal dat gekozen is door huidige speler
	 */
	public void kiesTeruggaveEdelsteenfichesHuidigeSpeler(Edelsteenfiche fiche, int aantal) {
		
		if(fiche == null) {
			throw new OngeldigEdelsteenficheException("EdelsteenficheNULL");
		}
		
		if(aantal < 0) {
			throw new OngeldigAantalException("OngeldigGetal");
		}
		
		int aantalInBezit = huidigeSpeler.getEdelstenenInBezit().containsKey(fiche) 
			? huidigeSpeler.getEdelstenenInBezit().get(fiche) 
			: 0;
		
		if(aantalInBezit == 0) {
			throw new EdelsteenficheGeenInBezitException("EdelsteenficheGeenInBezit");
		} else if (aantal > aantalInBezit) {
			throw new EdelsteenficheAantalTeVeelException("EdelsteenficheTeVeel");
		}
		
		huidigeSpeler.kiesTeruggaveEdelsteenfiches(fiche, aantal);
		vermeerderEdelsteenfiche(fiche, aantal);
	}
	
	/**UC4:
	 * Bepaalt welke speler aan de beurt is obv de speler die net aan de beurt is geweest
	 * verhoogt beurt met 1
	 */
	public void bepaalVolgendeSpelerAanDeBeurt() {
		int indexHuidigeSpeler = spelers.indexOf(huidigeSpeler);
		if (indexHuidigeSpeler == (spelers.size()-1)) indexHuidigeSpeler = -1;
		Speler volgendeSpeler = spelers.get(++indexHuidigeSpeler);
		setHuidigeSpeler(volgendeSpeler);
		beurt++;
	}
	
	/**UC4:
	 * Controleert of huidige speler te veel edelsteenfiches heeft
	 * @return boolean: of huidig speler te veel edelsteenfiches heeft
	 */
	public boolean heeftHuidigSpelerTeVeelEdelsteenfiches() {
		return huidigeSpeler.heeftTeVeelEdelsteenfiches();
	} 
	
	/**UC4:
	 * Controleert welke open kaart de huidige speler kan kopen 
	 * @param kaart : kaart om de controle op uit te voeren
	 * @return boolean: is aankoop mogelijk
	 */
	public boolean isKaartAankoopMogelijk(Ontwikkelingskaart ok) {
		
		// starten als aankoopbaar
		boolean isAankoopbaar = true;
		var bezitSpeler = huidigeSpeler.getEdelstenenInBezit();
		var bonusfichesSpeler = huidigeSpeler.geefBonusfichesInBezit();
		
		// elke steen die als aankoopprijs nodig is controleren tussen speler en kaart
		for(String s : ok.getAankoopprijs().keySet()) {
			int ficheKaartAantal = ok.getAankoopprijs().get(s);
			
			Optional<Edelsteenfiche> ficheSpeler = bezitSpeler
				.keySet()
				.stream()
				.filter(x -> x.getNaam() == s)
				.findFirst();
			
			// edelsteenfiche kan null zijn in speler zijn bezit
			// als hij/zij deze nog nooit verkregen heeft
			// (finetuning: voorkomen dat dit null kan zijn, geen prio 1 for nu)
			int ficheSpelerAantal = ficheSpeler.isEmpty()
				? 0 
				: bezitSpeler.get(ficheSpeler.get());
			
			// kijken of bonusfiche in bezit is van speler => anders 0
			var bonusficheInBezit  = bonusfichesSpeler.containsKey(s) 
					? bonusfichesSpeler.get(s) 
					: 0;
			
			// voeg bonusfiche aantal bij aantal in bezit speler
			ficheSpelerAantal += bonusficheInBezit;
			
			// als kost van kaart groter is dan bezit van speler => niet aankoopbaar
			// vanaf dat er van 1 type edelsteenfiche te weinig zijn => niet aankoopbaar
			if (ficheKaartAantal > ficheSpelerAantal) 
				isAankoopbaar = false;
		}
		
		// als alle nodige stenen in kaart aanwezig zijn in bezit speler
		// dan returned dit true
		return isAankoopbaar;
	}
	
	/**UC4
	 * Lijst met alle kaarten die speler kan kopen op moment van de beurt via de aankoopcontrole
	 * @return Lijst : met de mogelijke kaarten die huidige speler kan kopen
	 */
	public List<Ontwikkelingskaart> mogelijkeKaarten(){
		List<Ontwikkelingskaart> niveau1 = geefOmgedraaideKaarten(1);
		List<Ontwikkelingskaart> niveau2 = geefOmgedraaideKaarten(2);
		List<Ontwikkelingskaart> niveau3 = geefOmgedraaideKaarten(3);
		
		List<Ontwikkelingskaart> koopbareKaarten = new ArrayList<Ontwikkelingskaart>();
 		
		for (Ontwikkelingskaart ok : niveau1) {
			if (isKaartAankoopMogelijk(ok))
				koopbareKaarten.add(ok);
		}
		
		for (Ontwikkelingskaart ok : niveau2) {
			if (isKaartAankoopMogelijk(ok))
				koopbareKaarten.add(ok);
		}
		
		for (Ontwikkelingskaart ok : niveau3) {
			if (isKaartAankoopMogelijk(ok))
				koopbareKaarten.add(ok);
		}
		
		return koopbareKaarten;
	}
	
	/**UC4:
	 * Huidige speler kiest een kaart en deze wordt in de voorraad van de speler geplaatst met de controle of deze koopbaar was
	 * De kostprijs wordt ook afgetrokken bij de speler en terug in de stapels van spel geplaatst
	 * @param kaart - de kaart die gekozen wordt door de speler 
	 */
	public void kiesOntwikkelingskaart(Ontwikkelingskaart kaart) {
		//de huidige kaart toevoegen aan de speler, controle of deze koopbaar is
		if (isKaartAankoopMogelijk(kaart)) {
			 // voeg toe aan speler
			huidigeSpeler.setKaartenInBezit(kaart);
			
			// verwijder uit stapel
			switch(kaart.getNiveau()) {
				case 1: stapelNiveau1.remove(kaart);break;
				case 2: stapelNiveau2.remove(kaart);break;
				case 3: stapelNiveau3.remove(kaart);break;
			}
			
			HashMap<String, Integer> edelsteenfichesInKaart = kaart.getAankoopprijs();
			HashMap<String,Integer> bonusfichesInBezit = huidigeSpeler.geefBonusfichesInBezit();

			 // loop over alle edelstenen in spel => deze ga je ook vermeerderen
			for (Edelsteenfiche steen : this.edelsteenfiches.keySet()) {
				
				// haal type uit edelsteenfiche om het juiste aantal uit kostprijs te krijgen
				var type = steen.getNaam();
				// aantal opgehaald uit hashmap obv type (string)
				Integer aantal = edelsteenfichesInKaart.get(type);
				
				// skip als aantal 0 is
				if(aantal == 0) {
					continue;
				}
				
				var aantalBonusfiche  =  bonusfichesInBezit.containsKey(type) 
					? bonusfichesInBezit.get(type) 
					: 0;
				
				aantal -= aantalBonusfiche;
				
				// aantal mag niet onder 0 gaan
				if(aantal < 0)
					aantal = 0;
				
				// neem dit aantal weg van speler
				huidigeSpeler.kiesTeruggaveEdelsteenfiches(steen, aantal);
				// voeg dit toe aan spel
				vermeerderEdelsteenfiche(steen, aantal);
			}
		}
	}
	
	public HashMap<String, Integer> geefBonusfichesHuidigeSpeler(){
		return huidigeSpeler.geefBonusfichesInBezit();
	}
	
	//region GETTERS and SETTERS
	
	/**
	 * GETTER voor edelsteenfiches 
	 */
	public HashMap<Edelsteenfiche, Integer> getEdelsteenfiches() {
		return edelsteenfiches;
	}
	
	/**
	 * SETTER voor edelsteenfiches 
	 */
	private void setEdelsteenfiches(List<Edelsteenfiche> edelsteenfiches) {
		
		this.edelsteenfiches = new HashMap<Edelsteenfiche, Integer>();
		
		for(var e : edelsteenfiches) {
			this.edelsteenfiches.put(e, 0);
		}
	}
	
	/**
	 * GETTER voor edelen 
	 */
	public List<Edele> getEdelen() {
		return edelen;
	}
	
	/**
	 * SETTER voor edelen
	 */
	public void setEdelen(List<Edele> edelen){
		
		int aantalEdele = switch (spelers.size() ) {
			case 2 -> 3;
			case 3 -> 4;
			case 4 -> 5;
			default -> throw new RuntimeException("SPEdele");
		};
		
		Collections.shuffle(edelen);
		
		for(int i = 0; i < aantalEdele; i++) {
			this.edelen.add(edelen.get(i));
		}
	}
	
	/**
	 * GETTER voor de niet zichtbare kaarten in stapel niveau 1   
	 */
	public List<Ontwikkelingskaart> getStapelNiveau1() {
		return stapelNiveau1.stream().skip(4).toList();
	}

	/**
	 * SETTER voor de niet zichtbare kaarten in stapel niveau 1 
	 */
	private void setStapelNiveau1(List<Ontwikkelingskaart> stapelNiveau1) {
		Collections.shuffle(stapelNiveau1);
		this.stapelNiveau1 = stapelNiveau1;
	}

	/**
	 * GETTER voor de niet zichtbare kaarten in stapel niveau 2
	 */
	public List<Ontwikkelingskaart> getStapelNiveau2() {
		return stapelNiveau2.stream().skip(4).toList();
	}

	/**
	 * SETTER voor de niet zichtbare kaarten in stapel niveau 2 
	 */
	private void setStapelNiveau2(List<Ontwikkelingskaart> stapelNiveau2) {
		Collections.shuffle(stapelNiveau2);
		this.stapelNiveau2 = stapelNiveau2;
	}

	/**
	 * GETTER voor de niet zichtbare kaarten in stapel niveau 3
	 */
	public List<Ontwikkelingskaart> getStapelNiveau3() {
		return stapelNiveau3.stream().skip(4).toList();
	}

	/**
	 * SETTER voor de niet zichtbare kaarten in stapel niveau 3
	 */
	private void setStapelNiveau3(List<Ontwikkelingskaart> stapelNiveau3) {
		Collections.shuffle(stapelNiveau3);
		this.stapelNiveau3 = stapelNiveau3;
	}
		
	/**
	 * GETTER voor spelers in spel
	 */
	public List<Speler> getSpelers(){
		return this.spelers;
	}

	/**
	 * GETTER voor huidige speler in het spel
	 */
	public Speler getHuidigeSpeler() {
		return huidigeSpeler;
	}
	
	/**
	 * SETTER voor huidige speler in het spel 
	 */
	private void setHuidigeSpeler(Speler huidigeSpeler) {
		this.huidigeSpeler = huidigeSpeler;
	}
	
	//endregion
}
