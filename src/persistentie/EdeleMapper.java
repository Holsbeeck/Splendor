package persistentie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domein.Edele;

public class EdeleMapper {

	private List<Edele> edele;
	private Edele edele1;
	private Edele edele2;
	private Edele edele3;
	private Edele edele4;
	private Edele edele5;
	private Edele edele6;
	private Edele edele7;
	private Edele edele8;
	private Edele edele9;
	private Edele edele10;
	
	public EdeleMapper() {
		
		HashMap<String, Integer> stenen1 = new HashMap<String, Integer>();
		stenen1.put("smaragd", 0);
		stenen1.put("diamant", 4);
		stenen1.put("saffier", 0);
		stenen1.put("onyx", 4);
		stenen1.put("robijn", 0);
		edele1 = new Edele(1, 3, stenen1, "e-1.png");
		
		HashMap<String, Integer> stenen2 = new HashMap<String, Integer>();
		stenen2.put("smaragd", 0);
		stenen2.put("diamant", 3);
		stenen2.put("saffier", 0);
		stenen2.put("onyx", 3);
		stenen2.put("robijn", 3);
		edele2 = new Edele(2, 3, stenen2, "e-2.png");
		
		HashMap<String, Integer> stenen3 = new HashMap<String, Integer>();
		stenen3.put("smaragd", 0);
		stenen3.put("diamant", 3);
		stenen3.put("saffier", 3);
		stenen3.put("onyx", 3);
		stenen3.put("robijn", 0);
		edele3 = new Edele(3, 3, stenen3, "e-3.png");
		
		HashMap<String, Integer> stenen4 = new HashMap<String, Integer>();
		stenen4.put("smaragd", 3);
		stenen4.put("diamant", 3);
		stenen4.put("saffier", 3);
		stenen4.put("onyx", 0);
		stenen4.put("robijn", 0);
		edele4 = new Edele(4, 3, stenen4, "e-4.png");
		
		HashMap<String, Integer> stenen5 = new HashMap<String, Integer>();
		stenen5.put("smaragd", 3);
		stenen5.put("diamant", 0);
		stenen5.put("saffier", 0);
		stenen5.put("onyx", 3);
		stenen5.put("robijn", 3);
		edele5 = new Edele(5, 3, stenen5, "e-5.png");
		
		HashMap<String, Integer> stenen6 = new HashMap<String, Integer>();
		stenen6.put("smaragd", 3);
		stenen6.put("diamant", 0);
		stenen6.put("saffier", 3);
		stenen6.put("onyx", 0);
		stenen6.put("robijn", 3);
		edele6 = new Edele(6, 3, stenen6, "e-6.png");
		
		HashMap<String, Integer> stenen7 = new HashMap<String, Integer>();
		stenen7.put("smaragd", 4);
		stenen7.put("diamant", 0);
		stenen7.put("saffier", 4);
		stenen7.put("onyx", 0);
		stenen7.put("robijn", 0);
		edele7 = new Edele(7, 3, stenen7, "e-7.png");
		
		HashMap<String, Integer> stenen8 = new HashMap<String, Integer>();
		stenen8.put("smaragd", 0);
		stenen8.put("diamant", 0);
		stenen8.put("saffier", 0);
		stenen8.put("onyx", 4);
		stenen8.put("robijn", 4);
		edele8 = new Edele(8, 3, stenen8, "e-8.png");
		
		HashMap<String, Integer> stenen9 = new HashMap<String, Integer>();
		stenen9.put("smaragd", 0);
		stenen9.put("diamant", 4);
		stenen9.put("saffier", 4);
		stenen9.put("onyx", 0);
		stenen9.put("robijn", 0);
		edele9 = new Edele(9, 3, stenen9, "e-9.png");
		
		HashMap<String, Integer> stenen10 = new HashMap<String, Integer>();
		stenen10.put("smaragd", 4);
		stenen10.put("diamant", 0);
		stenen10.put("saffier", 0);
		stenen10.put("onyx", 0);
		stenen10.put("robijn", 4);
		edele10 = new Edele(10, 3, stenen10, "e-10.png");
		
		edele = new ArrayList<Edele>();
		
		edele.add(edele1);
		edele.add(edele2);
		edele.add(edele3);
		edele.add(edele4);
		edele.add(edele5);
		edele.add(edele6);
		edele.add(edele7);
		edele.add(edele8);
		edele.add(edele9);
		edele.add(edele10);
	}
	
	public List<Edele> geefEdele(){
		return edele;
	}
	
	public Edele geefEdele(int id) {
		return edele
			.stream()
			.filter(e -> e.getId() == id)
			.findFirst()
			.get();
	}
}