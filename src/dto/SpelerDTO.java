package dto;

import java.util.List;

public record SpelerDTO(
	String gebruikersnaam, 
	int prestigePunten, 
	boolean isAanDeBeurt,
	boolean isStartendeSpeler,
	List<OntwikkelingskaartDTO> kaartenInBezit,
	List<EdelsteenficheDTO> fichesInBezit,
	List<EdeleDTO> edelenInBezit,
	List<EdelsteenficheDTO> bonusfichesInBezit) {}
