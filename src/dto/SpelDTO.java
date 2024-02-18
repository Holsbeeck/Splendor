package dto;

import java.util.List;

public record SpelDTO(
	List<EdeleDTO> edelen, 
	List<EdelsteenficheDTO> edelsteenfiches,
	List<OntwikkelingskaartDTO> niv1Omgedraaid,
	List<OntwikkelingskaartDTO> niv1,
	List<OntwikkelingskaartDTO> niv2Omgedraaid,
	List<OntwikkelingskaartDTO> niv2,
	List<OntwikkelingskaartDTO> niv3Omgedraaid,
	List<OntwikkelingskaartDTO> niv3) {}
