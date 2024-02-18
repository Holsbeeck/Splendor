package domein;

import persistentie.SpelerMapper;

public class SpelerRepository {

	private SpelerMapper spelerMapper;
	
	/**
	 * Constructor die alle nodige instanties initialiseert 
	 */
	public SpelerRepository(){
		spelerMapper = new SpelerMapper();
	}
	
	/**UC1:
	 * Controleert of een speler bestaat
	 * @param speler - speler die de controle nodig heeft
	 * @return boolean: of speler bestaat of niet 
	 */
	public boolean bestaatSpeler(Speler speler){
		return spelerMapper.bestaatSpeler(speler);
	}

}
