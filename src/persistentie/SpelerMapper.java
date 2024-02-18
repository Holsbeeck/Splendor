package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper {

	public boolean bestaatSpeler(Speler speler){
		
		try (
			Connection conn = DriverManager.getConnection(Connectie.JBDC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID399291_g128.Speler WHERE naam = ? AND geboortejaar = ?")) {
			
            query.setString(1, speler.getGebruikersnaam());
            query.setInt(2, speler.getGeboortejaar());
            
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                	return false;
                }
            
            } catch (SQLException ex) {
            	throw new RuntimeException("Query error" ,ex);
        }
	    } catch (SQLException ex) {
	        throw new RuntimeException("Database connectie error" ,ex);
	    }
	}
}