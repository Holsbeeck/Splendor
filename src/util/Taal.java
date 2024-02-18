package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {
	
	private ResourceBundle bundle;
	
	public Taal(String taal) {
		var locale = new Locale.Builder().setLanguage(taal).build();
		setBundle(ResourceBundle.getBundle("util.LabelsBundle", locale));
	}
	
	public String geefString(String key) {
		return bundle.getString(key);
	}

	private void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
}
