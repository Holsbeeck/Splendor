package gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.Scene;
import util.Taal;

public class StartSchermController extends BorderPane{

	private DomeinController dc;
	private Taal taal;
	
	@FXML
	private MenuButton btnLang;
    @FXML
    private Button btnOptionNL;
    @FXML
    private Button btnOptionEN;
    @FXML
    private Button btnStart;
    @FXML 
    private Button btnQuit;
    @FXML
    private Button btnSpelregels;
    
    public StartSchermController(DomeinController dc) {
    	
    	this.dc = dc;
    	this.taal = dc.getTaal();
    	buildGui();
    	setTextComponents();
    }
    
    /**
	 * Laadt het scherm en bouwt de GUI op  
	 */
    private void buildGui() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScherm.fxml"));
            loader.setController(this);
            loader.setRoot(this);
        	loader.load();

        }catch(IOException ex) {
        	System.out.println(ex.getMessage());
        	throw new RuntimeException(ex);
        }	
    }
    
    /**
	 * Zet de tekst elementen goed in de GUI  
	 */
    private void setTextComponents() {
    	btnStart.setText(taal.geefString("BtnStart"));
    	btnQuit.setText(taal.geefString("BtnQuit"));
    	btnSpelregels.setText(taal.geefString("BtnSpelregels"));
    	btnOptionNL.setText(taal.geefString("nederlands"));
    	btnOptionEN.setText(taal.geefString("engels"));
    }
     
    /**
     * Zet de taal naar Engels 
     */
    @FXML 
    private void setLanguageEnglish() {
    	dc.setTaal("en");
    	taal = dc.getTaal();
    	setTextComponents();
    }
    
    /**
     * Zet de taal naar Nederlands 
     */
    @FXML 
    private void setLanguageNederlands() {
    	dc.setTaal("nl");
    	taal = dc.getTaal();
    	setTextComponents();
    }
    
    /**
     * Beëindigt het spel volledig
     */
    @FXML 
    private void quitOut() {
    	dc.endGame();
    }
    
    /**
     * Verwijst de gebruiker door naar de spelregels in de juiste taal 
     */
    @FXML
    private void geefSpelregels() {
    	        Desktop desktop = Desktop.getDesktop();
    	        try {
    	        	if (btnSpelregels.getText().equals("Spelregels"))
    	        		desktop.browse(new URI("https://agmj.be/wp-content/uploads/2016/01/Spelregels-Splendor.pdf"));
    	        	else
    	        		desktop.browse(new URI("https://cdn.1j1ju.com/medias/7f/91/ba-splendor-rulebook.pdf"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
    }
    
    /**
     * Start een nieuw spel op en geeft het scherm registreer spelers 
     */
    @FXML
    private void startNieuwSpel() {
    	
    	dc.startNieuwSpel();
    	
    	var registreerSpelerScherm = new RegistreerSpelerSchermController(dc);
    	Scene huidigeScene = this.getScene();
    	var breedte = 1100;
    	var hoogte = 846;
    	var scene = new Scene(registreerSpelerScherm, breedte, hoogte);
    	Stage stage = (Stage)huidigeScene.getWindow();
    	stage.setMinHeight(hoogte);
    	stage.setMinWidth(breedte);
    	stage.setMaxHeight(hoogte);
    	stage.setMaxWidth(breedte);
    	stage.centerOnScreen();
    	stage.setScene(scene);
    }
}