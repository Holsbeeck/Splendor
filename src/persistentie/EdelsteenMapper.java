package persistentie;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import domein.Edelsteenfiche;
import exceptions.EdelsteenficheBestaatNietException;

public class EdelsteenMapper {

	private List<Edelsteenfiche> edelsteenfiches;
	private Edelsteenfiche smaragd;
	private Edelsteenfiche diamant;
	private Edelsteenfiche saffier;
	private Edelsteenfiche onyx;
	private Edelsteenfiche robijn;
	
	public EdelsteenMapper() {
		smaragd = new Edelsteenfiche("smaragd", "groen");
		diamant = new Edelsteenfiche("diamant", "wit");
		saffier = new Edelsteenfiche("saffier", "blauw");
		onyx = new Edelsteenfiche("onyx", "zwart");
		robijn = new Edelsteenfiche("robijn", "rood");
		
		edelsteenfiches = new ArrayList<Edelsteenfiche>();
		
		edelsteenfiches.add(smaragd);
		edelsteenfiches.add(diamant);
		edelsteenfiches.add(saffier);
		edelsteenfiches.add(onyx);
		edelsteenfiches.add(robijn);
	}
	
	public List<Edelsteenfiche> geefEdelsteenfiches(){
		return edelsteenfiches;
	}
	
	public Edelsteenfiche geefEdelsteenfiche(String type) {
		return this.edelsteenfiches
			.stream()
			.filter(e -> e.getNaam() == type)
			.findFirst()
			.get();
	}
}