package domein;

import java.util.List;

import persistentie.EdelsteenMapper;

public class EdelsteenficheRepository {

	private EdelsteenMapper edelsteenMapper;
	
	/**
	 * Constructor die alle nodige instanties initialiseert 
	 */
	public EdelsteenficheRepository(){
		edelsteenMapper = new EdelsteenMapper();
	}
	
	/**UC1:
	 * Haalt de edelsteenfiches op uit de mapper.
	 * @return Lijst van Edelsteenfiche 
	 */
	public List<Edelsteenfiche> geefEdelsteenfiches(){
		return edelsteenMapper.geefEdelsteenfiches();
	}
	
	/**
	 * Haalt de edelsteenfiche op uit de mapper obv id.
	 * @return Edelsteenfiche 
	 */
	public Edelsteenfiche geefEdelsteenfiche(String type) {
		return edelsteenMapper.geefEdelsteenfiche(type);
	} 
}
