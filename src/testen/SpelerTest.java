package testen;
import domein.Speler;
import exceptions.OngeldigGeboortejaarException;
import exceptions.OngeldigeNaamException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class SpelerTest {
	
	Speler s;
//	@BeforeEach(){
//		Speler s = new Speler("Bert", 0)
//	}

	@ParameterizedTest
	@ValueSource(ints = {0,50,1899,2018})
	void maakSpeler_ongeldigGeboorteJaar_werptException(int geboorteDatum) {
		assertThrows(OngeldigGeboortejaarException.class, () ->  s = new Speler("Bert", geboorteDatum));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1900,1950,2000,2017})
	void maakSpeler_geldigGeboorteJaar_maaktSpeler(int geboorteJaar) {
		s = new Speler("Frank", geboorteJaar);
		assertEquals(geboorteJaar, s.getGeboortejaar());
	}
	
	@ParameterizedTest
	@ValueSource(strings ={"Bert", "B bert", "b123", "b 1"})
	void maakSpeler_geldigeNaam_maaktSpeler(String naam) {
		s = new Speler(naam, 1980);
		assertEquals(naam, s.getGebruikersnaam());
	}
	
	@ParameterizedTest
	@NullSource
	@ValueSource(strings ={" Bert", "1B bert", "b@123", "b 1!", "_Bert", "  "})
	void maakSpeler_ongeldigeNaam_werptException(String naam) {
		assertThrows(OngeldigeNaamException.class, () -> s = new Speler(naam, 1980));
	}
	
	
	

}
