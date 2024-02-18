package gui;

import java.io.IOException;
import java.time.Duration;

import domein.DomeinController;
import domein.Spel;
import dto.SpelDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import util.Taal;

public class EindeSpelSchermController extends GridPane {

	private DomeinController dc;
	private Taal taal;
	private Duration tijd;
	
	@FXML
	private Label lblMessage;
	@FXML
	private Label lblWinner;
	@FXML
	private Label lblNamenWinnaars;
	@FXML 
	private Label lblRondes;
	@FXML
	private Label lblTijd;
	
	@FXML
	private Label lblRondesNummer;
	@FXML
	private Label lblTijdKlok;
	
	@FXML
	private Button btnStop;
	@FXML
	private Button btnOpnieuw;
	
	public EindeSpelSchermController(DomeinController dc) {	
    	this.dc = dc;
    	this.taal = dc.getTaal();
    	buildGui();
    	setTextComponents();
    }
	
	public EindeSpelSchermController(DomeinController dc, String tijd) {	
    	this.dc = dc;
    	this.taal = dc.getTaal();
    	buildGui();
    	lblTijdKlok.setText(tijd);
    	setTextComponents();
    }
    
	/**
	 * Laadt het scherm en bouwt de GUI op  
	 */
    private void buildGui() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("EindeSpelScherm.fxml"));
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
    @FXML
    private void setTextComponents(){
    	bepaalMeervoud();
    	btnOpnieuw.setText(taal.geefString("BtnMenu"));
    	btnStop.setText(taal.geefString("BtnQuit"));
    	lblNamenWinnaars.setText(dc.geefNamenWinnaars());
    	lblRondes.setText(taal.geefString("LblRondes"));
    	lblTijd.setText(taal.geefString("LblTijd"));
    	
    	lblRondesNummer.setText(Integer.toString(dc.rondeNummer() - 1));
    }
    
    /**
     *  Bepaalt de tekst van de winnaars: of het meervoud is of niet
     */
    private void bepaalMeervoud() {
    	var aantalWinnaars = dc.geefWinnaars().size();
    	
    	switch(aantalWinnaars) {
    		case 0:
    			lblMessage.setText(taal.geefString("GeenWinnaar"));
    			lblWinner.setVisible(false);
    			break;
    		case 1:
    			lblMessage.setText(taal.geefString("1Winnaar"));
    			lblWinner.setText(taal.geefString("1Winnaar2"));
    			break;
    		case 2, 3, 4:
    			lblMessage.setText(taal.geefString("MWinnaars"));
    			lblWinner.setText(taal.geefString("MWinnaars2"));
    			break;
    		default:
    			lblMessage.setText(taal.geefString("fout"));
    			lblWinner.setText(taal.geefString("fout"));
    	}
    }
    
    /**
     * Beëindigt het spel volledig
     */
    @FXML
    private void quitOut(ActionEvent arg) 
	{
		dc.endGame();
	}
    
    /**
     * Voert de logica uit om terug naar het startscherm te gaan
     */
    @FXML
    private void startNieuwSpel() {
    	var startScherm = new StartSchermController(dc); 
    	Scene huidigeScene = this.getScene();
    	var breedte = 903;
    	var hoogte = 703;
    	var scene = new Scene(startScherm, breedte, hoogte);
    	Stage stage = (Stage)huidigeScene.getWindow();
    	stage.setMinHeight(hoogte);
    	stage.setMinWidth(breedte);
    	stage.setMaxHeight(hoogte);
    	stage.setMaxWidth(breedte);
    	stage.centerOnScreen();
    	stage.setScene(scene);
    }
	
}
