package cui;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.EdelsteenficheDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelDTO;
import dto.SpelerDTO;
import exceptions.OngeldigAantalException;
import exceptions.OngeldigGeboortejaarException;
import exceptions.OngeldigeNaamException;
import exceptions.SpelerBestaatNietException;
import exceptions.SpelerReedsInSpelException;
import util.Taal;

public class SplendorApplicatie {

	private DomeinController dc;
	private Scanner input;
	private Taal taal;
	private int spelerToegevoegd = 0;
	private int invoer = 0;

	/**
	 * constructor die alle nodige instanties initialiseert.
	 */	
	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
		this.taal = dc.getTaal();
	}
	
	/**
	 * functie die alle logica uitvoert om een nieuw spel te starten.
	 */
	public void startSplendorSpel() {

		kiesTaal();

		dc.startNieuwSpel();

		do {
			registreerSpeler(); // Eerste 2 spelers registreren

		} while (dc.isEindeRegistratie() == false);

		do { // extra spelers toevoegen
			System.out
					.printf("1: " + taal.geefString("gebruikerExtra") + "%n2: " + taal.geefString("startSpel") + "%n");

			if (input.nextInt() == 2) {
				// dc.registreerNieuwSpel();
				invoer = 2;
			} else
				registreerSpeler();
		} while (invoer != 2 && dc.geefAantalSpelers() < 4);

		dc.registreerNieuwSpel();

		toonBevestiging();
		
		speelSpel();
	}
	
	/**
	 * Deze functie handelt de keuze van de taal af. 
	 * Er wordt gevraagd welke taal gebruiker wilt en adhv keuze wordt taal ingesteld in het spel
	 */
	private void kiesTaal() {

		String lang = "nl";
		boolean notNumber = true;
		while (notNumber)
			try {
				do {
					System.out.printf(taal.geefString("kiesTaal") + "%n1: " + taal.geefString("nederlands") + "%n2: "
							+ taal.geefString("engels") + "%n");
					int invoer = input.nextInt();

					if (invoer == 2) {
						lang = "en";
						notNumber = false;
						break;
					} else if (invoer == 1) {
						lang = "nl";
						notNumber = false;
						break;
					} else {
						System.out.printf(taal.geefString("OngeldigeInvoer") + "%n");
					}
				} while (invoer != 1 || invoer != 2);
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println(taal.geefString("getalIngeven"));
			}

		dc.setTaal(lang);
		taal = dc.getTaal();
	}

	/**UC1:
	 * Deze functie voert de logica van het toevoegen van een speler aan het spel uit.
	 * Men vraagt aan de gebruiker de gebruikersnaam en geboortejaar van de speler 
	 * die men wilt toevoegen. Als speler voorkomt in database, wordt deze toegevoegd.
	 * Anders komt er een melding dat speler niet toegevoegd is.
	 */
	private void registreerSpeler() {
		try {
			int geboortejaar = 0;
			System.out.printf(taal.geefString("gebruikersNaam") + "%n");
			String gebruikersnaam = input.next();
			System.out.printf(taal.geefString("geboortejaar") + "%n");
			
			if (input.hasNextInt()) {
				geboortejaar = input.nextInt();
			} else {
				System.out.println(taal.geefString("OngeldigeInvoer"));
				input.next();
			}
			
			if (dc.bestaatSpeler(gebruikersnaam, geboortejaar)) {
				System.out.println(taal.geefString("bestaandeSpeler")); 
				return;
			}

			dc.voegSpelerToe(gebruikersnaam, geboortejaar);

			System.out.printf(taal.geefString("gebruiker") + " %s " + taal.geefString("gebruikerToegevoegd") + "%n", gebruikersnaam);
			spelerToegevoegd++;
//			if (dc.geefAantalSpelers() != spelerToegevoegd) {
//				System.out.printf(taal.geefString("gebruiker") + " %s " + taal.geefString("gebruikerToegevoegd") + "%n",
//						gebruikersnaam);
//				spelerToegevoegd++;
//			} else {
//				System.out.println(taal.geefString("onbestaandeSpeler"));
//			}

			System.out.printf(taal.geefString("spelHeeftSpelers") + " %d " + taal.geefString("speler") + "%s%n",
					dc.geefAantalSpelers(), dc.geefAantalSpelers() == 1 ? "" : "s");

		} catch (OngeldigeNaamException e) {
			System.out.println(taal.geefString(e.getMessage()));
		} catch (OngeldigGeboortejaarException e) {
			System.out.println(taal.geefString(e.getMessage()) + ((Calendar.getInstance().get(Calendar.YEAR)) - 6));
		} catch (SpelerReedsInSpelException e) {
			System.out.println(taal.geefString(e.getMessage()));
		} catch (SpelerBestaatNietException e) {
			System.out.println(taal.geefString(e.getMessage()));
		} 
	}

	/**UC1:
	 * Dit toont een bevestiging aan de gebruiker dat het spel geregistreerd is en welke
	 * speler het spel mag starten.
	 */
	private void toonBevestiging() {

		String jongsteSpeler = dc.geefHuidigeSpeler().gebruikersnaam();
		System.out.printf("%n" + taal.geefString("spelStarten") + dc.geefDeelnemendeSpelers() + "%n%s "
				+ taal.geefString("jongsteSpeler") + "%n" , jongsteSpeler);
	}
	
	/**UC2:
	 * Geeft een overzicht:
	 * <ul>
	 * <li>per speler</li>
	 * <li>spel</li>
	 * </ul>
	 */
	private void geefInfoSpel() {
		
		System.out.println("- " + taal.geefString("Spelers") + ":");
		
		for(SpelerDTO e : dc.geefOverzichtSpelers()) {
			geefOverzichtSpeler(e);
		}
		
		geefOverzichtSpel(dc.geefOverzichtSpel());
	}
	
	/**UC2:
	 * Geeft een overzicht van spel:
	 * <ul>
	 * <li>Beschikbare edelen </li>
	 * <li>Per niveau van ontwikkelingskaarten: de maximaal 4 zichtbare kaarten en indien nog aanwezig de gedekte stapel</li>
	 * <li>Per type edelsteenfiche de resterende voorraad</li>
	 * </ul>
	 * @param infoSpel - alle informatie van spel
	 */
	private void geefOverzichtSpel(SpelDTO infoSpel) {
		
		System.out.println("---------------" + taal.geefString("Spel").toUpperCase() + "---------------");
		
		System.out.println("- " + taal.geefString("BeschikbareEdelen") + ":");
		
		for(var e : infoSpel.edelen()) {
			System.out.println("-- " + e.id() + " " + e.prestigePunten() + " " + e.kostprijs());
		}
		
		System.out.println("- " + taal.geefString("KaartenN1") + ":");
		
		System.out.println("-- Zichtbaar");
		
		for(var e : infoSpel.niv1Omgedraaid()) {
			System.out.println("--- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("-- Niet Zichtbaar");
		
		for(var e : infoSpel.niv1()) {
			System.out.println("--- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("- " + taal.geefString("KaartenN2") + ":");
		
		System.out.println("-- Zichtbaar");
		
		for(var e : infoSpel.niv2Omgedraaid()) {
			System.out.println("--- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("-- Niet Zichtbaar");
		
		for(var e : infoSpel.niv2()) {
			System.out.println("--- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("- " + taal.geefString("KaartenN3") + ":");
		
		System.out.println("-- Zichtbaar");
		
		for(var e : infoSpel.niv3Omgedraaid()) {
			System.out.println("--- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("-- Niet Zichtbaar");
		
		for(var e : infoSpel.niv3()) {
			System.out.println("-- " + e.id() + " " + e.niveau() + " " + e.prestigePunten() + " " + e.bonusficheId() + " " + e.kostprijs());
		}
		
		System.out.println("- " + taal.geefString("Edelsteenfiches") + ":");
		
		for(var e : infoSpel.edelsteenfiches()) {
			System.out.println("-- " + e.naam() + " " + e.kleur() + " " + e.aantal());
		}
		
		System.out.println("----------------------------------");
	}
	
	/**UC2:
	 * Geeft een overzicht van een speler:
	 * <ul>
	 * <li>Gebruikersnaam</li>
	 * <li>Totaal aantal prestigepunten</li>
	 * <li>Of hij al dan niet aan de beurt is</li>
	 * <li>Of hij al dan niet de startspeler is</li>
	 * <li>De ontwikkelingskaarten in zijn bezit</li>
	 * <li>Per type edelsteenfiche hoeveel hij er in zijn bezit heeft</li>
	 * <li>De edelen in zijn bezit</li>
	 * </ul>
	 * @param speler - alle informatie van een speler
	 * */
	private void geefOverzichtSpeler(SpelerDTO speler) {
		
		System.out.println("--------------" + taal.geefString("speler").toUpperCase() + "--------------");
		System.out.println("-- " + taal.geefString("Naam") + ": " +  speler.gebruikersnaam());
		System.out.println("-- " + taal.geefString("PrestigePunten") + ": " +  speler.prestigePunten());
		System.out.println("-- " + taal.geefString("IsAanDeBeurt") + ": " +  (speler.isAanDeBeurt() ? taal.geefString("Ja") : taal.geefString("Nee")));
		System.out.println("-- " + taal.geefString("IsStartendeSpeler") + ": " +  (speler.isStartendeSpeler() ? taal.geefString("Ja") : taal.geefString("Nee")));
		System.out.println("-- " + taal.geefString("KaartenInBezit") + ":");
		
		List<OntwikkelingskaartDTO> kaartenInBezit = speler.kaartenInBezit();
		
		if(kaartenInBezit.isEmpty()) {
			System.out.println("--- " + taal.geefString("Geen").toUpperCase());
		}else {
			for(OntwikkelingskaartDTO k : kaartenInBezit){
				System.out.println("--- " + k.id() + " " + k.niveau() + " " + k.prestigePunten() + " " + k.bonusficheId() + " " + k.kostprijs());
			}
		}
		
		System.out.println("-- " + taal.geefString("EdelsteenfichesInBezit") + ":");
		
		List<EdelsteenficheDTO> edelsteenfichesInBezit = speler.fichesInBezit();
		
		if(edelsteenfichesInBezit.isEmpty()) {
			System.out.println("--- " + taal.geefString("Geen").toUpperCase());
		}else {
			for(EdelsteenficheDTO esf : edelsteenfichesInBezit){
				System.out.println("--- " + esf.naam() + " " + esf.kleur() + " " + esf.aantal());
			}
		}
		
		System.out.println("-- " + taal.geefString("EdelenInBezit") + ":");
		
		List<EdeleDTO> edelenInBezit = speler.edelenInBezit();
		
		if(edelenInBezit.isEmpty()) {
			System.out.println("--- " + taal.geefString("Geen").toUpperCase());
		}else {
			for(EdeleDTO ed : edelenInBezit) {
				System.out.println("-- " + ed.id() + " " + ed.prestigePunten() + " " + ed.kostprijs());
			}
		}
		
		System.out.println("----------------------------------");
	}
	
	/**UC2:
	 * voert de logica uit om een spel te spelen
	 */
	private void speelSpel() {
		do {
			speelRonde();
		} while (!dc.isEindeSpel()); //Het spel is ten einde als er een winnaar is
		
		// geef winnaars
		dc.geefNamenWinnaars();
	}
	
	/**UC3:
	 * voert de logica uit om een ronde te spelen
	 */
	private void speelRonde() {
		
		geefInfoSpel();
		
		do {
			speelBeurt();
			kiesEdele();
			dc.slaBeurtOver(); // TODO
			System.out.println();
		} while(!dc.eindeRonde()); // ronde is gedaan als iedereen aan de beurt is geweest in de ronde
	}
	
	/**UC3:
	 * voert logica uit om een edele te kiezen
	 */
	private void kiesEdele() {
		
		var beschikbareEdelen = dc.geefBeschikbareTegels();
		
		System.out.println("- Beschikbare edelen:");
		
		if(beschikbareEdelen.size() == 0) {
			System.out.println("-- GEEN");
		}else {
			for(EdeleDTO e : dc.geefBeschikbareTegels()) {
				System.out.println("-- " + e.id() + " " + e.prestigePunten() + " " + e.kostprijs());
			}
			
			System.out.println("Wat wens je te doen:%n"
								+ "1: geen edele kiezen?%n"
								+ "2: wel een edele kiezen?");
			
			boolean isKeuzeEdeleGeldig = false;
			int keuze = input.nextInt();
			do {
				switch(keuze) {
				case 1: 
					System.out.println("Geen edele gekozen, het spel gaat verder");
				case 2: 
					System.out.println("Geef de ID van de edele die je wilt kopen");
					
					try {
						int keuzeEdele = input.nextInt();
						boolean isBeschikbaarId = beschikbareEdelen
							.stream().map(e -> e.id())
							.toList()
							.contains(keuzeEdele);
						
						// valideren dat input van gebruiker wel een id is die beschkbaar is
						if(!isBeschikbaarId) {
							System.out.println("Deze id is niet beschikbaar");
							continue;
						}
						
						dc.kiesTegel(keuze);
						isKeuzeEdeleGeldig = true;
					} catch(RuntimeException ex) {
						System.out.println(taal.geefString(ex.getMessage()));
					}
				}
			} while(!isKeuzeEdeleGeldig);
		}
	}

	/**UC4:
	 * voert de logica uit om een beurt te spelen per speler
	 */
	private void speelBeurt() {
		
		boolean isGeldigeBeurt = false;
		do {
			try {
				
				System.out.printf("Ronde %d: %s het is aan jouw!%n"
						+ "Wat wens je te doen?%n"
						+ "1: sla beurt over%n"
						+ "2: pak edelsteenfiches%n"
						+ "3: koop een ontwikkelingskaart%n",dc.rondeNummer(), dc.geefHuidigeSpeler().gebruikersnaam());
				
				int keuze = input.nextInt();
				
				if (keuze == 1) { //beurt overslaan
					System.out.printf("%s slaat deze beurt over!%n", dc.geefHuidigeSpeler().gebruikersnaam());
					//dc.slaBeurtOver();
					isGeldigeBeurt = true;
				}else if (keuze == 2) { // edelstenen nemen
					
					boolean isAankoopGeldig = false;
					do {
						try {
							kiesEdelstenen();
							isAankoopGeldig = true;
						}catch (InputMismatchException e) {
							input.nextLine();
							System.out.println(taal.geefString("getalIngeven"));
						} catch(RuntimeException ex) {
							System.out.println(taal.geefString("fout"));
						}
					} while(!isAankoopGeldig);
					
					// zolang huidige speler te veel edelsteenfiches heeft, moeten er teruggegeven worden
					while(dc.heeftSpelerTeVeelEdelsteenfiches()) {
						try {
							geefEdelsteenficheTerug();
						} catch (InputMismatchException e) {
							input.nextLine();
							System.out.println(taal.geefString("getalIngeven"));
						} catch(RuntimeException ex) {
							System.out.println(taal.geefString("fout"));
						}
					}
					
					// als we hier geraakt zijn, is alles goed verlopen
					isGeldigeBeurt = true;
					
				} else if (keuze == 3) {
					boolean isAankoopGeldig = false;
					do {
						try {
							kiesOntwikkelingskaart();
							isAankoopGeldig = true;
						}catch (InputMismatchException e) {
							input.nextLine();
							System.out.println(taal.geefString("getalIngeven"));
						} catch(RuntimeException ex) {
							System.out.println(taal.geefString("fout"));
						}
					} while(!isAankoopGeldig);
					
					// als we hier geraakt zijn, is alles goed verlopen
					isGeldigeBeurt = true;
					
				} else {
					System.out.println("Deze keuze is niet geldig!");
				}
			}catch (InputMismatchException e) {
				input.nextLine();
				System.out.println(taal.geefString("getalIngeven"));
			}
			catch(RuntimeException ex) {
				System.out.println(taal.geefString("fout"));
			}
		}while(!isGeldigeBeurt);
	}
	
	/**UC4:
	 * voert de logica uit zodat de speler edelstenen kan nemen
	 */
	private void kiesEdelstenen() {
		
		System.out.printf("Je koos er voor om edelsteenfiches te pakken.%n");
		System.out.printf("1: 3 verschillende?%n"
				+ "2: 2 van dezelfde soort?%n");
		
		int keuze =  input.nextInt();
		
		if (keuze == 1) { // 3 verschillende edelstenen kopen
			for(int i = 1; i <= 3;i++) {
				System.out.printf("Kies je %d%s edelsteenfiche%n", i, i == 1? "ste": "de");
				System.out.printf("1: saffier%n"
						+ "2: onyx%n"
						+ "3: robijn %n"
						+ "4: smaragd %n"
						+ "5: diamant%n");
				keuze =  input.nextInt();
				if (keuze == 1) dc.kiesEdelsteenfiches("saffier", 1);
				if (keuze == 2) dc.kiesEdelsteenfiches("onyx", 1);
				if (keuze == 3) dc.kiesEdelsteenfiches("robijn", 1);
				if (keuze == 4) dc.kiesEdelsteenfiches("smaragd", 1);
				if (keuze == 5) dc.kiesEdelsteenfiches("diamant", 1);
				if (keuze < 1 || keuze > 5) throw new OngeldigAantalException("OngeldigeInvoer");
			}
			System.out.print(dc.geefHuidigeSpeler().fichesInBezit());
			System.out.println();
		} else if (keuze == 2) { // 2 van dezelfde soort kopen
			System.out.printf("1: 2 saffieren%n"
					+ "2: 2 onyxen%n"
					+ "3: 2 robijnen %n"
					+ "4: 2 smaragden %n"
					+ "5: 2 diamanten%n");
			keuze =  input.nextInt();
			if (keuze == 1) dc.kiesEdelsteenfiches("saffier", 2);
			if (keuze == 2) dc.kiesEdelsteenfiches("onyx", 2);
			if (keuze == 3) dc.kiesEdelsteenfiches("robijn", 2);
			if (keuze == 4) dc.kiesEdelsteenfiches("smaragd", 2);
			if (keuze == 5) dc.kiesEdelsteenfiches("diamant", 2);
			if (keuze < 1 || keuze > 5) throw new OngeldigAantalException("OngeldigeInvoer");
			
			System.out.print(dc.geefHuidigeSpeler().fichesInBezit());
			System.out.println();
		} else {
			throw new OngeldigAantalException("OngeldigeInvoer");
		}
	}
	
	/**UC4:
	 * voert de logica uit om de huidige speler edelsteenfiches terug te laten geven
	 */
	private void geefEdelsteenficheTerug() {
		
		System.out.println(dc.geefHuidigeSpeler().gebruikersnaam() + "heeft te veel edelsteenfiches en moet er teruggeven");
		
		System.out.printf("Welke wil je teruggeven:"
				+ "1: saffier %n"
				+ "2: onyx %n"
				+ "3: robijn %n"
				+ "4: smaragd %n"
				+ "5: diamant %n");
		
		int keuzeSteenTeruggave =  input.nextInt();
		String type = "";
		
		if (keuzeSteenTeruggave == 1) type = "saffier";
		if (keuzeSteenTeruggave == 2) type = "onyx";
		if (keuzeSteenTeruggave == 3) type = "robijn";
		if (keuzeSteenTeruggave == 4) type = "smaragd";
		if (keuzeSteenTeruggave == 5) type = "diamant";
		if (keuzeSteenTeruggave < 0 || keuzeSteenTeruggave > 5) throw new OngeldigAantalException("OngeldigeInvoer");
		
		System.out.printf("Hoeveel wil je hiervan teruggeven:");
		
		int aantal =  input.nextInt();
		
		dc.kiesTeruggaveEdelsteenfiches(type, aantal);
	}
	
	/**UC4:
	 * Voert de logica uit zodat de speler een ontwikkelingskaart kan kopen
	 */
	private void kiesOntwikkelingskaart() {
		
		System.out.printf("Je koos er voor om een ontwikkelingskaart te kopen.%n");
		System.out.printf("- Beschikbare kaarten:");
			
		var beschikbareKaarten = dc.geefBeschikbareKaarten();
		
		if(beschikbareKaarten.size() == 0) {
			System.out.printf("-- GEEN");
		}else {
			for(OntwikkelingskaartDTO k : beschikbareKaarten) {
				System.out.printf("-- " + k.id() + " " + k.niveau() + " " + k.bonusficheId() + " " + k.prestigePunten() + " " + k.kostprijs());
			}
			
			System.out.println("Geef de ID van de kaart die je wilt kopen");
			
			boolean isKeuzeKaartGeldig = false;
			do {
				try {
					int keuzeKaart = input.nextInt();
					boolean isBeschikbaarId = beschikbareKaarten
						.stream().map(e -> e.id())
						.toList()
						.contains(keuzeKaart);
					
					// valideren dat input van gebruiker wel een id is die beschkbaar is
					if(!isBeschikbaarId) {
						System.out.println("Deze id is niet beschikbaar");
						continue;
					}
					
					dc.kiesOntwikkelingskaart(keuzeKaart);
					isKeuzeKaartGeldig = true;
				} catch(RuntimeException ex) {
					System.out.println(taal.geefString("fout"));
				}
			} while(!isKeuzeKaartGeldig);
		}
	}
}
