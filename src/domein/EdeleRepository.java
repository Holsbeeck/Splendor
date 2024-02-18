package domein;

import java.util.List;

import persistentie.EdeleMapper;

public class EdeleRepository {

	private EdeleMapper edeleMapper;
	
	/**
	 * Constructor die alle nodige instanties initialiseert 
	 */
	public EdeleRepository(){
		edeleMapper = new EdeleMapper();
	}
	
	/**UC1:
	 * Haalt de edelen op uit de mapper
	 * @return Lijst van Edele
	 */
	public List<Edele> geefEdele(){
		return edeleMapper.geefEdele();
	}
	
	/**UC3:
	 * Haalt een edele op obv een id
	 * @return Edele: edele met deze id
	 */
	public Edele geefEdele(int id){
		return edeleMapper.geefEdele(id);
	}

}
