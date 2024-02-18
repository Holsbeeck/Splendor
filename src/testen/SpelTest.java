package testen;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Edele;
import domein.EdeleRepository;
import domein.Edelsteenfiche;
import domein.EdelsteenficheRepository;
import domein.Ontwikkelingskaart;
import domein.Spel;
import domein.Speler;
import exceptions.EdeleNietBeschikbaarException;

class SpelTest {

	EdelsteenficheRepository edelsteenRepo = new EdelsteenficheRepository();
	EdeleRepository edeleRepo = new EdeleRepository();
	Spel sp, spel, geldigSpel, spelUC3;
	Speler speler1;
	Speler speler2;
	Speler speler3;
	Speler speler4;
	Speler speler5;
	Speler spelerUC3_1, spelerUC3_2;
	Ontwikkelingskaart k0, k1, k2, k3;
	Edele edele1, edele2, edele3;
	List<Edele> edelen;
	ArrayList<Edelsteenfiche> edelsteenfiches;

	@BeforeEach
	void setUp() {
		sp = new Spel(new ArrayList<Edelsteenfiche>(), new ArrayList<Ontwikkelingskaart>(),
				new ArrayList<Ontwikkelingskaart>(), new ArrayList<Ontwikkelingskaart>());
		speler1 = new Speler("Jean Louis", 1980);
		speler2 = new Speler("Jean Jacques", 1981);
		speler3 = new Speler("Yean Jules", 1980);
		speler4 = new Speler("Jean Pierre", 1980);
		speler5 = new Speler("Jean Michel", 1980);
	}

	@Test
	void maakSpel_TeWeinigSpelers_returntFalse() {
		sp.voegSpelerToe(speler1);
		assertFalse(sp.heeftGeldigAantalSpelers());
	}

	@Test
	void maakSpel_TeVeelSpelers_returntFalse() {
		sp.voegSpelerToe(speler1);
		sp.voegSpelerToe(speler2);
		sp.voegSpelerToe(speler3);
		sp.voegSpelerToe(speler4);
		sp.voegSpelerToe(speler5);
		assertFalse(sp.heeftGeldigAantalSpelers());
	}

	@Test
	void maakSpel_met2Spelers_returntTrue() {
		sp.voegSpelerToe(speler1);
		sp.voegSpelerToe(speler2);
		assertTrue(sp.heeftGeldigAantalSpelers());
	}

	@Test
	void maakSpel_met3Spelers_returntTrue() {
		sp.voegSpelerToe(speler1);
		sp.voegSpelerToe(speler2);
		sp.voegSpelerToe(speler3);
		assertTrue(sp.heeftGeldigAantalSpelers());
	}

	@Test
	void maakSpel_met4Spelers_returntTrue() {
		sp.voegSpelerToe(speler1);
		sp.voegSpelerToe(speler2);
		sp.voegSpelerToe(speler3);
		sp.voegSpelerToe(speler4);
		assertTrue(sp.heeftGeldigAantalSpelers());
	}

	@Test
	void bepaalStartendeSpeler_met1SpelerJongste_returnJongste() {
		sp.voegSpelerToe(speler1); // Jean Louis, 1980
		sp.voegSpelerToe(speler2); // Jean Jacques, 1981

		sp.bepaalStartendeSpeler();
		var starter = sp.getHuidigeSpeler();

		assertTrue(starter.getGebruikersnaam() == "Jean Jacques");
	}

	@Test
	void bepaalStartendeSpeler_met2SpelersEvenJong_returnLangsteNaam() {
		sp.voegSpelerToe(speler1); // Jean Louis, 1980
		sp.voegSpelerToe(speler4); // Jean Pierre, 1980

		sp.bepaalStartendeSpeler();
		var starter = sp.getHuidigeSpeler();

		assertTrue(starter.getGebruikersnaam() == "Jean Pierre");
	}

	@Test
	void bepaalStartendeSpeler_met2SpelersEvenJongEnEvenLangeNaam_returnOmgekeerdAlfabetisch() {
		sp.voegSpelerToe(speler1); // Jean Louis, 1980
		sp.voegSpelerToe(speler3); // Yean Jules, 1980

		sp.bepaalStartendeSpeler();
		var starter = sp.getHuidigeSpeler();

		assertTrue(starter.getGebruikersnaam() == "Yean Jules");
	}
	
	
	/** UC1 testen of correct aantal Edelen en correct aantal edelsteenfiches per type op spelbord ligt
	 *     bij verschillend aantal spelers in spel
	 */
	
	@BeforeEach
	void maakSpelOmAantalFichesEnEdelenTeTesten() {
		List<Edelsteenfiche> fiches = new ArrayList<>();		
		fiches = edelsteenRepo.geefEdelsteenfiches(); 
		
		spel = new Spel(fiches, new ArrayList<Ontwikkelingskaart>(),
				new ArrayList<Ontwikkelingskaart>(), new ArrayList<Ontwikkelingskaart>());
		
		edelen = new ArrayList<>();		
		edelen = edeleRepo.geefEdele(); 
	}
	
	@Test
	void bepaalAantalEdelenEnFichesInSpel_spelMet2Spelers_Returnt3EdelenEn4EdelstenenPerType() {
		spel.voegSpelerToe(speler1);
		spel.voegSpelerToe(speler2);
		
		spel.setEdelen(edelen);
				
		assertEquals(2, spel.geefAantalSpelers());
		assertEquals(3, spel.getEdelen().size()); //controle aantal edelen in spel: 2 spelers = 3 edelen
		
		//controle of spel juiste aantal edelsteenfiches heeft
		spel.bepaalAantalEdelsteenfiches();
		
		HashMap<Edelsteenfiche, Integer> aantalPerEdelsteenfiche = spel.getEdelsteenfiches();
		assertEquals(5, aantalPerEdelsteenfiche.size()); //test aantal verschillende soorten edelstenen
		for(var e : aantalPerEdelsteenfiche.keySet()) {  //test voor elke type edelsteenfiche het aantal fiches:
			var aantal = aantalPerEdelsteenfiche.get(e);
			assertEquals(4, aantal);   //Spel met 2spelers heeft 4 fiches per type
		}
	}

	@Test
	void bepaalAantalEdelenenEnFichesInSpel_spelMet3Spelers_Returnt4EdelenEn5EdelstenenPerType() {
		spel.voegSpelerToe(speler1);
		spel.voegSpelerToe(speler2);
		spel.voegSpelerToe(speler3);
	
		spel.setEdelen(edelen);
		
		assertEquals(3, spel.geefAantalSpelers());
		assertEquals(4, spel.getEdelen().size()); //controle aantal edelen in spel: 3 spelers = 4 edelen
		
		//controle of spel juiste aantal edelsteenfiches heeft
		spel.bepaalAantalEdelsteenfiches();
				
		HashMap<Edelsteenfiche, Integer> aantalPerEdelsteenfiche = spel.getEdelsteenfiches();
		assertEquals(5, aantalPerEdelsteenfiche.size()); //test aantal verschillende soorten edelstenen
		for(var e : aantalPerEdelsteenfiche.keySet()) {  //test voor elke type edelsteenfiche het aantal fiches:
			var aantal = aantalPerEdelsteenfiche.get(e);
			assertEquals(5, aantal);   //Spel met 3 spelers heeft 5 fiches per type
		}
	}
	
	@Test
	void bepaalAantalEdelenenEnFichesInSpel_spelMet4Spelers_Returnt5EdelenEn7EdelstenenPerType() {
		spel.voegSpelerToe(speler1);
		spel.voegSpelerToe(speler2);
		spel.voegSpelerToe(speler3);
		spel.voegSpelerToe(speler4);
	
		spel.setEdelen(edelen);
		
		assertEquals(4, spel.geefAantalSpelers());
		assertEquals(5, spel.getEdelen().size()); //controle aantal edelen in spel: 4 spelers = 5 edelen
		
		//controle of spel juiste aantal edelsteenfiches heeft
		spel.bepaalAantalEdelsteenfiches();
						
		HashMap<Edelsteenfiche, Integer> aantalPerEdelsteenfiche = spel.getEdelsteenfiches();
		assertEquals(5, aantalPerEdelsteenfiche.size()); //test aantal verschillende soorten edelstenen
		for(var e : aantalPerEdelsteenfiche.keySet()) {  //test voor elke type edelsteenfiche het aantal fiches:
			var aantal = aantalPerEdelsteenfiche.get(e);
			assertEquals(7, aantal);   //Spel met 4 spelers heeft 7 fiches per type
		}
	}
	
	/** UC2 Testen: controleren of één of meerdere spelers aangeduid worden als winnaar bij behalen eindscore
	*/
	
	@BeforeEach
	void maakGeldigSpel() {
		geldigSpel = new Spel(new ArrayList<Edelsteenfiche>(), new ArrayList<Ontwikkelingskaart>(),
				new ArrayList<Ontwikkelingskaart>(), new ArrayList<Ontwikkelingskaart>());
		geldigSpel.voegSpelerToe(speler1);
		geldigSpel.voegSpelerToe(speler2);
		geldigSpel.voegSpelerToe(speler3);
	}

	@BeforeEach
	void maakOntwikkelingskaarten() {
		HashMap<String, Integer> aankoopprijs = new HashMap<String, Integer>();
		k0 = new Ontwikkelingskaart(1, 1, 0, 1, aankoopprijs, ""); // kaart id1 niveau1 met 0 prestigepunten, String afbeelding
		k1 = new Ontwikkelingskaart(2, 1, 2, 2, aankoopprijs, ""); // kaart id2 niveau1 met 2 prestigepunt
		k2 = new Ontwikkelingskaart(3, 2, 3, 3, aankoopprijs, ""); // kaart id3 niveau2 met 3 prestigepunten
		k3 = new Ontwikkelingskaart(4, 3, 6, 4, aankoopprijs, ""); // kaart id4 niveau3 met 6 prestigepunten
	}
	
	@BeforeEach
	void geefSpelersNetNietVoldoendePrestigepuntenInBezit() {
		// Speler1 krijgt 4 kaarten en één edele met een totaal aantal aan prestigepunten = 14
		speler1.setKaartenInBezit(k0); // kaart met prestigepunten: 0
		speler1.setKaartenInBezit(k1); // kaart met prestigepunten: 2
		speler1.setKaartenInBezit(k2); // kaart met prestigepunten: 3
		speler1.setKaartenInBezit(k3); // kaart met prestigepunten: 6
		speler1.voegEdeleInBezitToe(new Edele(1, 3 , new HashMap<String, Integer>(), "")); // edele met prestigepunten: 3
		// Speler2 krijgt 4 kaarten met een totaal aantal aan prestigepunten = 14
		speler2.setKaartenInBezit(k1); // kaart met prestigepunten: 2
		speler2.setKaartenInBezit(k2); // kaart met prestigepunten: 3
		speler2.setKaartenInBezit(k2); // kaart met prestigepunten: 3
		speler2.setKaartenInBezit(k3); // kaart met prestigepunten: 6
	}

	@Test
	void bepaalScoreEnOfErWinnaarIs_spelersHebbenNetNietVoldoendePrestigepunten_returntGeenWinnaarEnScoreElkeSpeler() {
		//controle of spelers kaarten en edelen die ze hebben meegekregen in bezit hebben
		assertEquals(4,speler1.getKaartenInBezit().size());
		assertEquals(1,speler1.getEdelenInBezit().size());
		assertEquals(4,speler2.getKaartenInBezit().size());
		assertEquals(0,speler2.getEdelenInBezit().size());
		
		//controle van de score bij onze spelers
		assertEquals(14, speler1.getScore()); // totaal aantal prestigepunten in bezit van speler1 = 14
		assertEquals(14, speler2.getScore()); // totaal aantal prestigepunten in bezit van speler2 = 14
		assertEquals(0, speler3.getScore());
		
		assertFalse(geldigSpel.heeftWinnaars());
	}

	@Test
	void bepaalScoreEnWinnaar_éénSpelerHeeftNetVoldoendePrestigepunten_returntScoreEnNaamWinnaar() {
		System.out.println("\nTest bepaalScoreEnWinnaar_éénSpelerHeeftNetVoldoendePrestigepunten_returntScoreEnNaamWinnaar");
		speler1.setKaartenInBezit(k1); // speler1 krijgt extra kaart met prestigepunten: 2 en haalt eindscore van 16
		assertEquals(16, speler1.getScore()); 
		assertTrue(geldigSpel.heeftWinnaars());
		assertTrue(geldigSpel.geefNamenWinnaars().contains("Jean Louis"));
	}
	
	@Test
	void bepaalScoreEnWinnaars_tweeSpelershebbenGelijkeMaxScoreEnGelijkAantalOntwikkelingskaarten_returntNamen2Winnaars() {
		System.out.println("\nTest bepaalScoreEnWinnaars_tweeSpelershebbenGelijkeMaxScoreEnGelijkAantalOntwikkelingskaarten_returntNamen2Winnaars");
		speler1.setKaartenInBezit(k2); // speler1 krijgt extra kaart met prestigepunten: 3
		speler2.setKaartenInBezit(k2); // speler1 krijgt extra kaart met prestigepunten: 3
		
		//twee spelers hebben een eindscore van 17 behaald en een gelijk aantal ontwikkelingskaarten in bezit
		assertEquals(17, speler1.getScore());
		assertEquals(17, speler2.getScore());
		assertTrue(geldigSpel.heeftWinnaars());
		assertTrue(geldigSpel.geefNamenWinnaars().contains("Jean Louis"));
		assertTrue(geldigSpel.geefNamenWinnaars().contains("Jean Jacques"));
	}
	
	@Test
	void bepaalScoreEnWinnaar_tweeSpelershebbenGelijkeMaxScoreMaarOngelijkAantalOntwikkelingskaarten_returntNaamVanEénWinnaar() {
		System.out.println("\nTest bepaalScoreEnWinnaar_tweeSpelershebbenGelijkeMaxScoreMaarOngelijkAantalOntwikkelingskaarten_returntNaamVanEénWinnaar");
		speler1.setKaartenInBezit(k3); // speler1 krijgt één extra kaart met prestigepunten: 6
		
		speler2.setKaartenInBezit(k2); 
		speler2.setKaartenInBezit(k2); // speler2 krijgt twee extra kaarten met prestigepunten: 3
		
		//controle of spelers correcte aantal kaarten en edelen die ze hebben meegekregen in bezit hebben
				assertEquals(5,speler1.getKaartenInBezit().size()); 
				assertEquals(1,speler1.getEdelenInBezit().size());
				assertEquals(6,speler2.getKaartenInBezit().size());
				assertEquals(0,speler2.getEdelenInBezit().size());
		
		//beide spelers hebben een eindscore van 20 behaald
		assertEquals(20, speler1.getScore());
		assertEquals(20, speler2.getScore());
		assertTrue(geldigSpel.heeftWinnaars());
		//speler1("Jean Louis") heeft één ontwikkelingskaart minder in bezit dan speler2 ("Jean Jacques") en wint het spel
		assertTrue(geldigSpel.geefNamenWinnaars().contains("Jean Louis")); 
		assertFalse(geldigSpel.geefNamenWinnaars().contains("Jean Jacques"));
	}
	
	/** UC3 testen: verkrijgen van een tegel (edele) 
	 */
	
	@BeforeEach
	void maakEdelenVoorTestUC3() {
		//Aanmaken edele1, kostprijs samenstellen in een Hashmap:
		HashMap<String, Integer> kost3Groen3Wit3Zwart = new HashMap<String, Integer>();
		kost3Groen3Wit3Zwart.put("smaragd", 3); // instellen 3 groen
		kost3Groen3Wit3Zwart.put("diamant", 3); // instellen 3 wit
		kost3Groen3Wit3Zwart.put("onyx", 3); // instellen 3 zwart
		kost3Groen3Wit3Zwart.put("saffier", 0);
		kost3Groen3Wit3Zwart.put("robijn", 0); 
		// edele1 met idNr=1, 3 prestigepunten en zijn kostprijs
		edele1 = new Edele(1, 3, kost3Groen3Wit3Zwart,"");
		
		//Aanmaken edele2, kostprijs samenstellen in een Hashmap:
		HashMap<String, Integer> kost3Groen3Wit3Rood = new HashMap<String, Integer>();
		kost3Groen3Wit3Rood.put("smaragd", 3); // instellen 3 groen
		kost3Groen3Wit3Rood.put("diamant", 3); // instellen 3 wit
		kost3Groen3Wit3Rood.put("robijn", 3); // instellen 3 rood
		kost3Groen3Wit3Rood.put("saffier", 0); 
		kost3Groen3Wit3Rood.put("onyx", 0); 
		// edele2 met idNr=2, 3 prestigepunten en zijn kostprijs
		edele2 = new Edele(2, 3, kost3Groen3Wit3Rood,""); 
		
		//Aanmaken edele3, kostprijs samenstellen in een Hashmap:
		HashMap<String, Integer> kost3Groen3Wit3Blauw = new HashMap<String, Integer>();
		kost3Groen3Wit3Rood.put("smaragd", 3); // instellen 3 groen
		kost3Groen3Wit3Rood.put("diamant", 3); // instellen 3 wit
		kost3Groen3Wit3Rood.put("saffier", 3); // instellen 3 blauw
		kost3Groen3Wit3Rood.put("onyx", 0); 
		kost3Groen3Wit3Rood.put("robijn", 0); 
		// edele met idNr=3, 3 prestigepunten en zijn kostprijs
		edele3 = new Edele(3, 3, kost3Groen3Wit3Blauw,""); 
	}
	
	@BeforeEach
	void setUpUC3() {
		spelUC3 = new Spel(new ArrayList<Edelsteenfiche>(), new ArrayList<Ontwikkelingskaart>(),
				new ArrayList<Ontwikkelingskaart>(), new ArrayList<Ontwikkelingskaart>());
		spelerUC3_1 = new Speler("speler 1", 1993);
		spelerUC3_2 = new Speler("speler 2", 1993);
		spelUC3.voegSpelerToe(spelerUC3_1);
		spelUC3.voegSpelerToe(spelerUC3_2);
			
		List<Edele> edelen_UC3 = new ArrayList<>();
		edelen_UC3.add(edele1);
		edelen_UC3.add(edele2);
		edelen_UC3.add(edele3);
		spelUC3.setEdelen(edelen_UC3);
	
		spelUC3.bepaalVolgendeSpelerAanDeBeurt();
		
		//speler1 (huidigeSpeler) wordt in het bezit gebracht van ontwikkelingskaarten:
		//	3 kaarten met bonusficheId 1 en 3 kaarten met bonusficheId 2
		for (int i = 1 ; i <= 3 ; i++) {
			spelerUC3_1.setKaartenInBezit(new Ontwikkelingskaart(i   , 1, 0, 1, new HashMap<String, Integer>(),""));
			spelerUC3_1.setKaartenInBezit(new Ontwikkelingskaart(i+10, 1, 0, 2, new HashMap<String, Integer>(),""));
		}
	}	
	
	@Test
	void bepaalHuidigeSpelerInSpelUC3EnZijnStatus_speler1WordtAangesteldAlsHuidigeSpeler() {
		//spelUC3.bepaalVolgendeSpelerAanDeBeurt();
		Speler huidigeSpeler = spelUC3.getHuidigeSpeler();
		
		//speler1 wordt aangeduid als huidige speler
		assertEquals("speler 1", huidigeSpeler.getGebruikersnaam());
		
		//controle kaarten in bezit van speler1
		assertEquals(6, spelerUC3_1.getKaartenInBezit().size());
	}
	
	@Test
	void bepaalOfEdelenInSpelUC3_returntEdeleEnAantalEdelen() {
		assertEquals(3, spelUC3.getEdelen().size());
		assertTrue(spelUC3.getEdelen().contains(edele1));
	}
		
	@Test 
	void bepaalOfHuidigeSpelerTegelKrijgt_spelerHeeftOnvoldoendeBonusfiches_spelerKrijgtGeenTegel() {
		//speler1 heeft edele1 nog niet in zijn bezit
		assertFalse(spelerUC3_1.getEdelenInBezit().contains(edele1));
	
		//speler1 wil edele aankopen maar krijgt exception te zien
		assertThrows(EdeleNietBeschikbaarException.class, () -> spelUC3.kiesTegel(edele1));
		
		//aangezien speler1 niet de benodigde bonusfiches heeft is aankoop edele niet gelukt
		assertFalse(spelerUC3_1.getEdelenInBezit().contains(edele1));
	}
	
	@Test
	void bepaalOfHuidigeSpelerTegelKrijgt_spelerHeeftVoldoendeBonusfichesVoorEénTegel_spelerKrijgtTegel() {
		//speler1 heeft edele1 nog niet in zijn bezit
		assertFalse(spelerUC3_1.getEdelenInBezit().contains(edele1));
		
		//speler1 krijgt 3 extra kaarten met bonusficheId 3 zodat speler één tegel kan verkrijgen
		for (int i = 1 ; i <= 3 ; i++) {
			spelerUC3_1.setKaartenInBezit(new Ontwikkelingskaart(i+20, 1, 0, 4, new HashMap<String, Integer>(),""));
		}
		
		//speler1 wil edele aankopen
		spelUC3.kiesTegel(edele1);
				
		//speler1 heeft tegel succesvol in bezit genomen
		assertTrue(spelerUC3_1.getEdelenInBezit().contains(edele1));
		
		//controle of tegel (edele1) is verwijderd uit aanbod na aankoop
		assertEquals(2, spelUC3.getEdelen().size());
		assertFalse(spelUC3.getEdelen().contains(edele1));
	}
}

