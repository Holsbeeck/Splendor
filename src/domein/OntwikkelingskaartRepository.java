package domein;

import java.util.List;
import persistentie.OntwikkelingskaartMapper;

public class OntwikkelingskaartRepository {

	private OntwikkelingskaartMapper ontwikkelingskaartMapper;
	
	/**
	 * Constructor die alle nodige instanties initialiseert 
	 */
	public OntwikkelingskaartRepository() {
		ontwikkelingskaartMapper = new OntwikkelingskaartMapper();
	}
	
	/**UC1:
	 * Haalt de ontwikkelingskaarten van niveau 1 op.
	 * @return Lijst van Ontwikkelingskaart niveau 1
	 */
	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau1(){
		return ontwikkelingskaartMapper.geefOntwikkelingskaartenNiveau1();
	}
	
	/**UC1:
	 * Haalt de ontwikkelingskaarten van niveau 2 op.
	 * @return Lijst van Ontwikkelingskaart niveau 2
	 */
	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau2(){
		return ontwikkelingskaartMapper.geefOntwikkelingskaartenNiveau2();
	}
	
	/**UC1
	 * Haalt de ontwikkelingskaarten van niveau 3 op.
	 * @return Lijst van Ontwikkelingskaart niveau 3
	 */
	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau3(){
		return ontwikkelingskaartMapper.geefOntwikkelingskaartenNiveau3();
	}

	/**UC4:
	 * Haalt een ontwikkelingskaart op obv een id
	 * @return Ontwikkelingskaart: kaart met juiste id
	 */
	public Ontwikkelingskaart geefOntwikkelingskaart(int id) {
		return ontwikkelingskaartMapper.geefOntwikkelingskaart(id);
	}
}
