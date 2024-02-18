package persistentie;

import java.util.ArrayList;
import util.Taal;
import java.util.HashMap;
import java.util.List;
import domein.Ontwikkelingskaart;

public class OntwikkelingskaartMapper {
	
	private List<Ontwikkelingskaart> niveau1;
	private List<Ontwikkelingskaart> niveau2;
	private List<Ontwikkelingskaart> niveau3;
	private HashMap<Integer, String> fiches;
	final int AANTAL_KAARTEN_NIVEAU1 = 40;
	final int AANTAL_KAARTEN_NIVEAU2 = 30;
	final int AANTAL_KAARTEN_NIVEAU3 = 20;
	final int AANTAL_ATTRIBUTEN_OP_KAART = 7;

	public OntwikkelingskaartMapper() {
		// Hashmap waarmee data in 2D-Array zal omgezet worden naar de String bonusfiche in constructor Ontwikkelingskaart 
		this.fiches = new HashMap<Integer, String>();
		fiches.put(1, "smaragd"); // groen
		fiches.put(2, "diamant"); // wit
		fiches.put(3, "saffier"); // blauw
		fiches.put(4, "onyx"); // zwart
		fiches.put(5, "robijn"); // rood
		
		setOntwikkelingskaartenNiveau1();
		setOntwikkelingskaartenNiveau2();
		setOntwikkelingskaartenNiveau3();
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau1() {
		return niveau1;
	}
	
	private void setOntwikkelingskaartenNiveau1() {

		this.niveau1 = new ArrayList<Ontwikkelingskaart>();
		
		int[][] stapel = new int[AANTAL_KAARTEN_NIVEAU1][AANTAL_ATTRIBUTEN_OP_KAART];
		
		// prestige; bonus; smaragd; diamant; saffier; onyx; robijn
		stapel[0] = new int[] { 1, 4, 0, 0, 4, 0, 0 };
		stapel[1] = new int[] { 0, 3, 1, 1, 0, 1, 2 };
		stapel[2] = new int[] { 0, 3, 0, 1, 0, 2, 0 };
		stapel[3] = new int[] { 0, 4, 1, 0, 0, 1, 3 };
		stapel[4] = new int[] { 0, 5, 0, 3, 0, 0, 0 };
		stapel[5] = new int[] { 0, 5, 1, 2, 0, 2, 0 };
		stapel[6] = new int[] { 0, 1, 0, 2, 1, 0, 0 };
		stapel[7] = new int[] { 0, 4, 3, 0, 0, 0, 0 };
		stapel[8] = new int[] { 0, 2, 0, 0, 2, 2, 0 };
		stapel[9] = new int[] { 0, 1, 0, 0, 0, 0, 3 };
		stapel[10] = new int[] { 0, 5, 0, 1, 0, 3, 1 };
		stapel[11] = new int[] { 0, 5, 1, 0, 2, 0, 0 };
		stapel[12] = new int[] { 0, 4, 2, 2, 0, 0, 0 };
		stapel[13] = new int[] { 0, 1, 0, 1, 1, 1, 1 };
		stapel[14] = new int[] { 0, 2, 1, 0, 1, 1, 1 };
		stapel[15] = new int[] { 0, 4, 0, 2, 2, 0, 1 };
		stapel[16] = new int[] { 0, 1, 1, 1, 3, 0, 0 };
		stapel[17] = new int[] { 0, 2, 2, 0, 1, 1, 1 };
		stapel[18] = new int[] { 0, 2, 2, 0, 2, 1, 0 };
		stapel[19] = new int[] { 0, 2, 0, 0, 3, 0, 0 };
		stapel[20] = new int[] { 0, 4, 1, 1, 1, 0, 1 };
		stapel[21] = new int[] { 0, 2, 0, 3, 1, 1, 0 };
		stapel[22] = new int[] { 0, 4, 2, 0, 0, 0, 1 };
		stapel[23] = new int[] {0, 1, 0, 0, 1, 2, 2};
		stapel[24] = new int[] {0, 3, 0, 0, 0, 3, 0};
		stapel[25] = new int[] {0, 5, 1, 2, 1, 1, 0};
		stapel[26] = new int[] {0, 1, 0, 1, 1, 2, 1};
		stapel[27] = new int[] {0, 2, 0, 0, 0, 1, 2};
		stapel[28] = new int[] {0, 3, 3, 0, 1, 0, 1};
		stapel[29] = new int[] {0, 3, 2, 1, 0, 0, 2};
		stapel[30] = new int[] {1, 2, 4, 0, 0, 0, 0};
		stapel[31] = new int[] {1, 1, 0, 0, 0, 4, 0};
		stapel[32] = new int[] {0, 1, 0, 0, 2, 0, 2};
		stapel[33] = new int[] {0, 5, 0, 2, 0, 0, 2};
		stapel[34] = new int[] {1, 5, 0, 4, 0, 0, 0};
		stapel[35] = new int[] {0, 3, 2, 0, 0, 2, 0};
		stapel[36] = new int[] {0, 3, 1, 1, 0, 1, 1};
		stapel[37] = new int[] {1, 3, 0, 0, 0, 0, 4};
		stapel[38] = new int[] {0, 5, 1, 1, 1, 1, 0};
		stapel[39] = new int[] {0, 4, 1, 1, 2, 0, 1};
		
		int startId = 1;
		
		maakKaartenAanEnVoegToeAanStapel(1, AANTAL_KAARTEN_NIVEAU1, startId, stapel);
	}
	
	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau2() {
		return niveau2;
	}
	
	private void setOntwikkelingskaartenNiveau2() {
		
		this.niveau2 = new ArrayList<Ontwikkelingskaart>();
		
		int[][] stapel = new int[AANTAL_KAARTEN_NIVEAU2][AANTAL_ATTRIBUTEN_OP_KAART];
		                        
		stapel[0] = new int[] { 2, 4, 0, 5, 0, 0, 0 };
		stapel[1] = new int[] { 1, 4, 2, 3, 2, 0, 0 };
		stapel[2] = new int[] { 1, 5, 0, 0, 3, 3, 2 };
		stapel[3] = new int[] { 1, 1, 0, 2, 3, 2, 0 };
		stapel[4] = new int[] { 2, 2, 1, 0, 0, 2, 4 };
		stapel[5] = new int[] { 1, 2, 0, 2, 3, 0, 3 };
		stapel[6] = new int[] { 1, 1, 2, 3, 0, 0, 3 };
		stapel[7] = new int[] { 2, 3, 0, 2, 0, 4, 1 };
		stapel[8] = new int[] { 2, 5, 0, 0, 0, 5, 0 };
		stapel[9] = new int[] { 2, 5, 0, 3, 0, 5, 0 };
		stapel[10] = new int[] { 1, 3, 3, 0, 2, 3, 0 };
		stapel[11] = new int[] { 1, 5, 0, 2, 0, 3, 2 };
		stapel[12] = new int[] { 3, 1, 6, 0, 0, 0, 0 };
		stapel[13] = new int[] { 2, 5, 2, 1, 4, 0, 0 };
		stapel[14] = new int[] { 2, 3, 0, 0, 5, 0, 0 };
		stapel[15] = new int[] { 2, 1, 3, 0, 5, 0, 0 };
		stapel[16] = new int[] { 2, 1, 0, 4, 2, 1, 0 };
		stapel[17] = new int[] { 2, 2, 0, 0, 0, 0, 5 };
		stapel[18] = new int[] { 3, 2, 0, 6, 0, 0, 0 };
		stapel[19] = new int[] { 2, 4, 4, 0, 1, 0, 2 };
		stapel[20] = new int[] { 3, 3, 0, 0, 6, 0, 0 };
		stapel[21] = new int[] { 2, 4, 5, 0, 0, 0, 3 };
		stapel[22] = new int[] { 1, 2, 3, 0, 0, 2, 2 };
		stapel[23] = new int[] { 1, 3, 2, 0, 2, 0, 3 };
		stapel[24] = new int[] { 1, 4, 3, 3, 0, 2, 0 };
		stapel[25] = new int[] { 2, 1, 5, 0, 0, 0, 0 };
		stapel[26] = new int[] { 2, 2, 0, 0, 0, 3, 5 };
		stapel[27] = new int[] { 3, 4, 0, 0, 0, 6, 0 };
		stapel[28] = new int[] { 2, 3, 0, 5, 3, 0, 0 };
		stapel[29] = new int[] { 3, 5, 0, 0, 0, 0, 6 };
		
		int startId = 41;
		
		maakKaartenAanEnVoegToeAanStapel(2,AANTAL_KAARTEN_NIVEAU2, startId, stapel);
	}
	
	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau3() {
		return niveau3;
	}
	
	private void setOntwikkelingskaartenNiveau3() {
		
		this.niveau3 = new ArrayList<Ontwikkelingskaart>();
		
		int[][] stapel = new int[AANTAL_KAARTEN_NIVEAU3][AANTAL_ATTRIBUTEN_OP_KAART];
		
		stapel[0] = new int[] { 4, 5, 6, 0, 3, 0, 3 };
		stapel[1] = new int[] { 5, 4, 0, 0, 0, 3, 7 };
		stapel[2] = new int[] { 4, 4, 3, 0, 0, 3, 6 };
		stapel[3] = new int[] { 4, 1, 3, 3, 6, 0, 0 };
		stapel[4] = new int[] { 5, 2, 0, 3, 0, 7, 0 };
		stapel[5] = new int[] { 3, 2, 3, 0, 3, 3, 5 };
		stapel[6] = new int[] { 5, 5, 7, 0, 0, 0, 3 };
		stapel[7] = new int[] { 3, 3, 3, 3, 0, 5, 3 };
		stapel[8] = new int[] { 3, 1, 0, 5, 3, 3, 3 };
		stapel[9] = new int[] { 4, 5, 7, 0, 0, 0, 0 };
		stapel[10] = new int[] { 4, 3, 0, 7, 0, 0, 0 };
		stapel[11] = new int[] { 4, 4, 0, 0, 0, 0, 7 };
		stapel[12] = new int[] { 4, 2, 0, 0, 0, 7, 0 };
		stapel[13] = new int[] { 5, 1, 3, 0, 7, 0, 0 };
		stapel[14] = new int[] { 4, 2, 0, 3, 0, 6, 3 };
		stapel[15] = new int[] { 4, 3, 0, 6, 3, 3, 0 };
		stapel[16] = new int[] { 4, 1, 0, 0, 7, 0, 0 };
		stapel[17] = new int[] { 3, 4, 5, 3, 3, 0, 3 };
		stapel[18] = new int[] { 3, 5, 3, 3, 5, 3, 0 };
		stapel[19] = new int[] { 5, 3, 0, 7, 3, 0, 0 };
		
		int startId = 71;
		
		// vul stapel op met kaarten
		maakKaartenAanEnVoegToeAanStapel(3, AANTAL_KAARTEN_NIVEAU3, startId, stapel);
	}
	
	private void maakKaartenAanEnVoegToeAanStapel(int niveau, int eindIndex, int startId,  int[][] attrOpIndex) {
		// vul stapel op met kaarten
		for (int kaartNr = 0; kaartNr < eindIndex; kaartNr++) { // overlopen elke kaart uit de stapel
			
			HashMap<String, Integer> kostprijs = new HashMap<String, Integer>();
			int id = startId + kaartNr;
			int prestigePunten = attrOpIndex[kaartNr][0];
			int bonusficheId = attrOpIndex[kaartNr][1];
			String afbeelding = "k-" + id + ".png";
			
			// kijk wat de kostprijs is en vul een lijst op
			for(int j = 2;j < 7;j++) {
				kostprijs.put(fiches.get(j-1), attrOpIndex[kaartNr][j]);
			}
			
			Ontwikkelingskaart kaart = new Ontwikkelingskaart(
				id,
				niveau, //int niveau
				prestigePunten, //int prestigepunten
				bonusficheId, //int bonusficheId
			    kostprijs, 
			    afbeelding); //Hashmap met per edelsteen het aantal
			
			switch(niveau) {
				case 1: 
					niveau1.add(kaart);
					break;
				case 2: 
					niveau2.add(kaart);
					break;
				case 3: 
					niveau3.add(kaart);
					break;
				default:
					throw new RuntimeException("OKniveau");
			}
		}
	}
	
	/**UC4:
	 * haal een kaart op obv van een id
	 * @return Ontwikkelingskaart: kaart met juiste id
	 */
	public Ontwikkelingskaart geefOntwikkelingskaart(int id) {
		// alle kaarten in 1 lijst
		List<Ontwikkelingskaart> alleKaarten = new ArrayList<>();
		alleKaarten.addAll(niveau1);
		alleKaarten.addAll(niveau2);
		alleKaarten.addAll(niveau3);
		
		// zoeken op id en deze teruggeven
		return alleKaarten
			.stream()
			.filter(e -> e.getId() == id)
			.findFirst()
			.get();
	}
}