package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import dto.EdeleDTO;
import dto.EdelsteenficheDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelDTO;
import dto.SpelerDTO;
import exceptions.EdeleBestaatNietException;
import exceptions.EdelsteenficheBestaatNietException;
import exceptions.OntwikkelingskaartBestaatNietException;
import exceptions.SpelerBestaatNietException;
import exceptions.SpelerReedsInSpelException;
import util.Taal;

public class DomeinController{
	
	private Taal taal;
	private Spel spel;
	private SpelerRepository spelerRepository;
	private EdelsteenficheRepository edelsteenficheRepository;
	private EdeleRepository edeleRepository;
	private OntwikkelingskaartRepository kaartRepository;
	
	/**
	 * constructor die nodige instanties aanmaakt en de default taal instelt.
	 */
	public DomeinController() {
		this.spelerRepository = new SpelerRepository();
		this.edelsteenficheRepository = new EdelsteenficheRepository();
		this.edeleRepository = new EdeleRepository();
		this.kaartRepository = new OntwikkelingskaartRepository();
		setTaal("nl"); // default nl
	}
	
	/**EXTRA:
	 * Quit button methode voor testing purposes en spel gemakkelijk af te sluiten. 
	 */
	public void endGame() {
		System.exit(0);
	}
	
	/**UC1:
	 * Maakt een nieuw spel aan en haalt hiervoor de nodige data op.
	 */
	public void startNieuwSpel() {
		var fiches = edelsteenficheRepository.geefEdelsteenfiches();
		var niveau1 = kaartRepository.geefOntwikkelingskaartenNiveau1();
		var niveau2 = kaartRepository.geefOntwikkelingskaartenNiveau2();
		var niveau3 = kaartRepository.geefOntwikkelingskaartenNiveau3();
		spel = new Spel(fiches, niveau1, niveau2, niveau3);
	}
	
	/**UC1:
	 * Voegt een speler toe aan het spel, indien deze voorkomt in de database
	 * en er voldaan wordt aan de nodige regels.
	 * @param gebruikersnaam - naam van de speler die toegevoegd wordt
	 * @param geboortejaar - geboortejaar van de speler die toegevoegd wordt
	 */
	public void voegSpelerToe(String gebruikersnaam, int geboortejaar){
		
		if(bestaatSpeler(gebruikersnaam, geboortejaar)) {
			throw new SpelerReedsInSpelException("bestaandeSpeler");
		}
		
		var speler = new Speler(gebruikersnaam, geboortejaar);
		
		if(spelerRepository.bestaatSpeler(speler)) {
			spel.voegSpelerToe(speler);
		} else {
			throw new SpelerBestaatNietException("onbestaandeSpeler");
		}
	}
	
	/**UC1:
	 * Controleert of het aantal spelers geldig is om een spel te starten
	 * @return boolean - geldig of niet geldig 
	 */
	public boolean isEindeRegistratie() { 
		return spel.heeftGeldigAantalSpelers();
	}
	
	/**UC1:
	 * Voert de laatste logica uit om een spel compleet te registreren.
	 * Er worden bepaalde zaken goedgezet op basis van aantal spelers.
	 * Er wordt ook een overzicht gegeven van de objecten in het spel 
	 */
	public void registreerNieuwSpel() {
		// set juiste aantallen van edelsteenfiches
		spel.bepaalAantalEdelsteenfiches();
		// set de edelen
		spel.setEdelen(edeleRepository.geefEdele());
		// bepaal startende speler
		spel.bepaalStartendeSpeler();
	}

	/**UC1:
	 * Geeft het aantal spelers die in het spel zitten
	 * @return int - aantal spelers in spel 
	 */
	public int geefAantalSpelers() {
		return spel.geefAantalSpelers();
	}

	/**UC1:
	 * Geeft alle namen van de spelers terug die in het spel zitten
	 * @return String - namen van spelers in spel 
	 */
	public String geefDeelnemendeSpelers() {
		return spel.geefDeelnemendeSpelers();
	}
	
	/**UC1:
	 * Geeft aan of de opgegeven speler reeds in het spel zit.
	 * @param gebruikersnaam - naam van de speler die gecontroleerd wordt
	 * @param geboortejaar - geboortejaar van de speler die gecontroleerd wordt
	 */
	public boolean bestaatSpeler(String gebruikersnaam, int geboortejaar) {
		return spel.bestaatSpeler(gebruikersnaam, geboortejaar);
	}
	
	/**UC2:
	 * Geeft een overzicht van alle elementen in het spel
	 */
	public SpelDTO geefOverzichtSpel() {
		
		return new SpelDTO(
			geefEdelen(), 
			geefEdelsteenfiches(),
			geefOmgedraaideKaarten(1),
			geefStapel(1),
			geefOmgedraaideKaarten(2),
			geefStapel(2),
			geefOmgedraaideKaarten(3),
			geefStapel(3));
	}
	
	/**UC2:
	 * Geeft een overzicht van alle spelers in het spel
	 */
	public List<SpelerDTO> geefOverzichtSpelers(){
		return spel
			.getSpelers()
			.stream()
			.map(x -> geefInfoSpeler(x))
			.toList();
	}
	
	/**UC2:
	 * Mapt alle elementen van een speler naar de gewenste DTO's en verzamelt deze DTO's in SpelerDTO
	 * @param speler - speler die gemapt moet worden naar DTO
	 * @return SpelerDTO
	 */
	private SpelerDTO geefInfoSpeler(Speler speler) {
		
		Speler huidigeSpeler = spel.getHuidigeSpeler();
		
		boolean isAanDeBeurt = 
			speler.getGebruikersnaam() == huidigeSpeler.getGebruikersnaam() &&
			speler.getGeboortejaar() == huidigeSpeler.getGeboortejaar();
		
		List<OntwikkelingskaartDTO> kaartenInBezit = speler
			.getKaartenInBezit()
			.stream()
			.map(x -> new OntwikkelingskaartDTO(x.getId(), x.getNiveau(), x.getPrestigePunten(), x.getBonusficheId(), x.getAankoopprijs().toString(), x.getAfbeelding()))
			.toList();
		
		
		List<EdelsteenficheDTO> edelstenenInBezit = new ArrayList<EdelsteenficheDTO>();
		var stenenSpeler = speler.getEdelstenenInBezit();
		
		for(Edelsteenfiche e : stenenSpeler.keySet()) {
			var aantal = stenenSpeler.get(e);
			edelstenenInBezit.add(new EdelsteenficheDTO(e.getNaam(), e.getKleur(), aantal));
		}
		
		List<EdeleDTO> edelenBezit = speler
				.getEdelenInBezit()
				.stream()
				.map(x -> new EdeleDTO(x.getId(), x.getPrestigePunten(), x.getKostprijs().toString(), x.getAfbeelding()))
				.toList();
		
		List<EdelsteenficheDTO> bonusfichesInBezit = new ArrayList<EdelsteenficheDTO>();
		var bonusfichesSpeler = speler.geefBonusfichesInBezit();
		
		for(String type : bonusfichesSpeler.keySet()) {
			var bonusfiche = edelsteenficheRepository.geefEdelsteenfiche(type);
			var aantal = bonusfichesSpeler.get(type);
			bonusfichesInBezit.add(new EdelsteenficheDTO(bonusfiche.getNaam(), bonusfiche.getKleur(), aantal));
		}
		
		return new SpelerDTO(
			speler.getGebruikersnaam(), 
			speler.getScore(),
			isAanDeBeurt,
			speler.getIsStartendeSpeler(),
			kaartenInBezit,
			edelstenenInBezit,
			edelenBezit,
			bonusfichesInBezit);
	}
	
	/**UC2:
	 * Mapt alle beschikbare edelen in het spel naar de gewenste DTO's
	 * @return Lijst van EdeleDTO
	 */
	private List<EdeleDTO> geefEdelen(){
		return spel
			.getEdelen()
			.stream()
			.map(e -> new EdeleDTO(
				e.getId(),
				e.getPrestigePunten(), 
				e.getKostprijs().toString(),
				e.getAfbeelding()))
			.toList();
	}
	
	/**UC2:
	 * Mapt alle type edelsteenfiches en hun aantallen naar de gewenste DTO's
	 * @return Lijst van EdelsteenficheDTO
	 */
	private List<EdelsteenficheDTO> geefEdelsteenfiches(){
		HashMap<Edelsteenfiche, Integer> edelsteenfiches = spel.getEdelsteenfiches();
		List<EdelsteenficheDTO> edelsteenfichesDTOs = new ArrayList<EdelsteenficheDTO>();
		
		for(var e : edelsteenfiches.keySet()) {
			var aantal = edelsteenfiches.get(e);
			edelsteenfichesDTOs.add(new EdelsteenficheDTO(e.getNaam(),e.getKleur(), aantal));
		}
		
		return edelsteenfichesDTOs;
	}
	
	/**UC2:
	 * Mapt alle kaarten in een stapel naar de gewenste DTO's
	 * @param niv - niveau van kaarten in stapel
	 * @return Lijst van OntwikkelingskaartDTO
	 */
	private List<OntwikkelingskaartDTO> geefStapel(int niv){
		
		List<Ontwikkelingskaart> stapel = new ArrayList<Ontwikkelingskaart>();
		
		switch(niv) {
			case 1: stapel = spel.getStapelNiveau1(); 
				break;
			case 2: stapel = spel.getStapelNiveau2();
				break;
			case 3: stapel = spel.getStapelNiveau3(); 
				break;
			default: stapel = new ArrayList<Ontwikkelingskaart>(); 
				break;
		}
		
		return stapel
			.stream()
			.map(s -> new OntwikkelingskaartDTO(
				s.getId(), 
				s.getNiveau(),
				s.getPrestigePunten(), 
				s.getBonusficheId(), 
				s.getAankoopprijs().toString(),
				s.getAfbeelding()))
			.toList();
	}
	
	/**UC2:
	 * Mapt alle omgedraaide kaarten van een bepaald niveau naar de gewenste DTO's
	 * @param niv - niveau van omgedraaide kaarten
	 * @return Lijst van OntwikkelingskaartDTO
	 */
	private List<OntwikkelingskaartDTO> geefOmgedraaideKaarten(int niv){
		
		List<Ontwikkelingskaart> omgedraaideKaarten = spel.geefOmgedraaideKaarten(niv);
		
		return omgedraaideKaarten
			.stream()
			.map(s -> new OntwikkelingskaartDTO(
				s.getId(), 
				s.getNiveau(),
				s.getPrestigePunten(), 
				s.getBonusficheId(), 
				s.getAankoopprijs().toString(),
				s.getAfbeelding()))
			.toList();
	}
	
	/**UC2:
	 * Controleert of het spel een winnaar of winnaars heeft en als het 
	 * zo is, betekent dit het einde van het spel.
	 * @return boolean: of het spel gedaan is of niet
	 */
	public boolean isEindeSpel() {
		return spel.heeftWinnaars();
	}
	
	/**UC2:
	 * Geeft naam/namen van de winnaar(s)
	 * @return String: naam/namen winnaar(s)
	 */
	public String geefNamenWinnaars() {
		return spel.geefNamenWinnaars();
	}
	
	public List<Speler> geefWinnaars(){
		return spel.geefWinnaars();
	}
	
	/**UC4:
	 * Voegt edelstenen toe aan de huidige speler
	 * @param type - naam van edelsteenfiche
	 * @param aantal - aantal stenen 
	 */
	public void kiesEdelsteenfiches(String type, int aantal) {
		
		Edelsteenfiche edelsteen;
		
		try {
			edelsteen = edelsteenficheRepository.geefEdelsteenfiche(type);
		}catch(NoSuchElementException ex) {
			throw new EdelsteenficheBestaatNietException("EdelsteenficheBestaatNiet");
		}
		
		spel.kiesEdelsteenfichesHuidigSpeler(edelsteen, aantal);
	}

	/**UC4:
	 * Neemt edelsteenfiches weg van de huidige speler
	 * @param type - naam van edelsteenfiche
	 * @param aantal - aantal stenen 
	 */
	public void kiesTeruggaveEdelsteenfiches(String type, int aantal) {
		
		Edelsteenfiche edelsteen;
		
		try {
			edelsteen = edelsteenficheRepository.geefEdelsteenfiche(type);
		} catch(NoSuchElementException ex) {
			throw new EdelsteenficheBestaatNietException("EdelsteenficheBestaatNiet");
		}
		
		spel.kiesTeruggaveEdelsteenfichesHuidigeSpeler(edelsteen, aantal);
	}

	/**UC4:
	 * Controlleert of de huidige speler te veel fiches heeft in totaal. (max 10)
	 * @return boolean - of huidige speler te veel fiches heeft
	 */
	public boolean heeftSpelerTeVeelEdelsteenfiches() {
		return spel.heeftHuidigSpelerTeVeelEdelsteenfiches();
	}
	
	/**UC3
	* Geeft een lijst weer met alle edelen die de speler kan kopen
	* @return List<Edele>: elke edele waarvoor de speler over genoeg ontwikkelingskaarten beschikt
	*/
	 public List<EdeleDTO> geefBeschikbareTegels(){
		 
		 var beschikbareEdelen = spel.geefBeschikbareTegelsHuidigeSpeler();
		 
		 return beschikbareEdelen
			 .stream()
			 .map(e -> new EdeleDTO(
				e.getId(), 
				e.getPrestigePunten(), 
				e.getKostprijs().toString(), 
				e.getAfbeelding()))
			 .toList();
	 }
	
	 /**UC3:
	  * voegt een edele toe aan een speler obv een id 
	  */
	 public void kiesTegel(int id) {
		 
		 Edele edele;
		 
		 try {
			 edele = edeleRepository.geefEdele(id);
		 } catch(NoSuchElementException ex) {
			 throw new EdeleBestaatNietException("EdeleBestaatNiet");
		 }
		 
		 spel.kiesTegel(edele);
	 }
	 
	 /**UC4:
	  * voert de logica uit die moet gebeure als de speler beslist om een beurt over te slaan
	  */
	 public void slaBeurtOver() {
		 spel.bepaalVolgendeSpelerAanDeBeurt();
	 }
	 
	 /**UC4:
	  * geeft de aankoopbare kaarten terug voor de huidige speler
	  * @return List van OntwikkelingskaartDTO: beschikbare kaarten voor huidige speler
	  */
	 public List<OntwikkelingskaartDTO> geefBeschikbareKaarten() {
		 
		 return spel
			 .mogelijkeKaarten()
			 .stream()
			 .map(k -> new OntwikkelingskaartDTO(
				 k.getId(), 
				 k.getNiveau(), 
				 k.getPrestigePunten(), 
				 k.getBonusficheId(), 
				 k.getAankoopprijs().toString(), 
				 k.getAfbeelding()))
			 .toList();
	 }
	 
	 /**UC4:
	  * voegt een kaart toe aan de huidige speler en geeft deze speler ook een bonusfiche 
	  * @param id - id van gekozen ontwikkelingskaart
	  */
	 public void kiesOntwikkelingskaart(int id) {
		 
		 Ontwikkelingskaart ok;
		 
		 try {
			 ok = kaartRepository.geefOntwikkelingskaart(id);
		 } catch(NoSuchElementException ex) {
			 throw new OntwikkelingskaartBestaatNietException("OntwikkelingskaartBestaatNiet");
		 }
		 
		 spel.kiesOntwikkelingskaart(ok);
	 }
	 
	//region GETTERS and SETTERS
	
	/**
	 * Geeft de taal van het spel.
	 * @return Taal - object Taal 
	 */
	public Taal getTaal() {
		return taal;
	}
	
	/**
	 * Zet de taal van het spel op basis van keuze gebruiker
	 * @param taal - gekozen taal door gebruiker
	 */
	public void setTaal(String taal) {
		this.taal = new Taal(taal);
	}
	
	/**
	 * geeft het rondeNummer weer
	 * @return int rondeNummer
	 */
	public int rondeNummer() {
		return spel.geefRonde();
	}
	
	/**
	 * return true als de ronde is beëindigd 
	 * @return boolean isEindeRonde
	 */
	public boolean eindeRonde() {
		return spel.isEindeRonde();
	}
	
	/**
	 * geeft info over de huidige speler die aan de beurt is
	 * @return SpelerDTO: info van huidige speler
	 */
	public SpelerDTO geefHuidigeSpeler() {
		Speler huidigeSpeler = spel.getHuidigeSpeler();
		return geefInfoSpeler(huidigeSpeler);
	}
	
	/**
	 * geeft punten handmatig voor de demo
	 * @param score - 3 keuzes tijdens de demo
	 */
	public void geefPunten(int score) {
		Speler huidigeSpeler = spel.getHuidigeSpeler();
		huidigeSpeler.geefScorePunten(score);
	}
	
	//endregion
}