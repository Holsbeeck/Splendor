package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.Taal;

public class RegistreerSpelerSchermController extends GridPane {

	private DomeinController dc;
	private Taal taal;

    @FXML
    private Label lblBirthYear;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblOutput;
    @FXML
    private Label lblAantalSpelers;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnVoegSpelerToe;
    @FXML
    private Button btnAnnuleer;
    @FXML
    private TextField inpBirthYear;
    @FXML
    private TextField inpUser;
    @FXML 
    private Label lblSpelerLijst;
    @FXML
    private Button btnSnelleStart;
    @FXML 
    private Label lblSpelers;
    @FXML 
    private ImageView ivUitroep;
	
	public RegistreerSpelerSchermController(DomeinController dc) {
		this.dc = dc;
		this.taal = dc.getTaal();
		buildGui();
		setTextComponents();
		updateStateGame();
	}
	
	/**
	 * Laadt het scherm en bouwt de GUI op  
	 */
	private void buildGui() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistreerSpelerScherm.fxml"));
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
		lblUser.setText(taal.geefString("LblUser"));
		lblBirthYear.setText(taal.geefString("LblBirthYear"));
		lblAantalSpelers.setText(taal.geefString("LblAantalSpelers") + ": 0/4");
    	btnVoegSpelerToe.setText(taal.geefString("BtnVoegSpelerToe"));
    	btnStart.setText(taal.geefString("BtnStart"));
    	btnAnnuleer.setText(taal.geefString("BtnAnnuleer"));
    	btnSnelleStart.setText(taal.geefString("BtnSnelleStart"));
    	lblSpelers.setText(taal.geefString("LblSpelers"));
    }
	
	/**
	 * Past het label aan van het aantal spelers. 
	 */
	private void updateLblAantalSpelers() {
		lblAantalSpelers.setText(taal.geefString("LblAantalSpelers") + ": " + dc.geefAantalSpelers() + "/4");
	}
	
	/**
	 * Toont de validatie van registreer speler
	 * @param output - validatie bericht
	 * @param output - Kleur van de tekst
	 */
	private void showOutput(String output, Color color) {
		lblOutput.setText(output);
		lblOutput.setTextFill(color);
		lblOutput.setVisible(true);
		ivUitroep.setVisible(true);
	}
	
	/**
	 * Zet bepaalde labels goed adhv de staat van het spel 
	 */
	private void updateStateGame() {
		//update labele amount players
		updateLblAantalSpelers();
		// einde registratie true => enable (false), else disable (true)
		btnStart.setDisable(!dc.isEindeRegistratie());
		// max players reached => disable add
		btnVoegSpelerToe.setDisable(dc.geefAantalSpelers() == 4);
	}
	
	/**
	 * Maakt de invulvelden leeg 
	 */
	private void resetInputs() {
		inpUser.setText("");
		inpBirthYear.setText("");
		inpUser.requestFocus();;
	}
	
	/**
	 * Voert de logica uit om een speler toe te voegen  
	 */
	@FXML
    private void registreerSpeler(ActionEvent event) {
		var gebruikersnaam = inpUser.getText();
		var geboortejaarString = inpBirthYear.getText();
		int geboortejaarInt = 0;
		
		// try converting string to integer
		try {
			geboortejaarInt = Integer.parseInt(geboortejaarString);
		} catch(RuntimeException ex) {
			showOutput(taal.geefString("getalIngeven"), Color.DARKRED);
			return;
		}
		
		if(dc.bestaatSpeler(gebruikersnaam, geboortejaarInt)) {
			showOutput(taal.geefString("bestaandeSpeler"), Color.DARKRED);
			return;
		}
		
		// try adding player to game
		try {
			dc.voegSpelerToe(gebruikersnaam, geboortejaarInt);
		}catch(RuntimeException ex) {
			showOutput(taal.geefString(ex.getMessage()), Color.DARKRED);
			return;
		}
		
		//if we get here, everything OK => player added
		showOutput(gebruikersnaam + " " + taal.geefString("gebruikerToegevoegd"), Color.DARKGREEN);
		
		lblSpelerLijst.setText(dc.geefDeelnemendeSpelers());
		
		// update visuals to state of game
		updateStateGame();
		buttonDisable();
		resetInputs();
    }
	
	/**
	 * Controleert aantal spelers om te bepalen of snelle start kan of niet 
	 */
	private void buttonDisable() {
		if (dc.geefAantalSpelers() > 0)
			btnSnelleStart.setDisable(true);
	}
	
	/**
	 * Registreert nieuw spel en gaat naar SpeelVeldScherm 
	 */
    @FXML
    private void startSpel(ActionEvent event) {
    	dc.registreerNieuwSpel();
    	
    	var speelveldScherm = new SpeelVeldSchermController(dc); 
    	Scene huidigeScene = this.getScene();
    	var breedte = 1280;
    	var hoogte = 960;
    	var scene = new Scene(speelveldScherm, breedte, hoogte);
    	Stage stage = (Stage)huidigeScene.getWindow();
    	stage.setMinHeight(hoogte);
    	stage.setMinWidth(breedte);
    	stage.setMaxHeight(9000);
    	stage.setMaxWidth(9000);
    	stage.centerOnScreen();
    	stage.setScene(scene);
    }
    
    /**
     * Voert de logica uit om terug naar startscherm te gaan 
     */
    @FXML
    private void showStartScherm(ActionEvent event) {
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
    
    /**
     * Voert de logica uit om een snelle start te kunnen doen 
     */
    @FXML // maakt snel een spel aan met 2 spelers
    private void SnelleStart(ActionEvent event) {
    	dc.voegSpelerToe("Jelle", 1995);
    	dc.voegSpelerToe("Andy", 1993);
    	dc.registreerNieuwSpel();
    	
    	var speelveldScherm = new SpeelVeldSchermController(dc); 
    	Scene huidigeScene = this.getScene();
    	var breedte = 1280;
    	var hoogte = 960;
    	var scene = new Scene(speelveldScherm, breedte, hoogte);
    	Stage stage = (Stage)huidigeScene.getWindow();
    	stage.setMinHeight(hoogte);
    	stage.setMinWidth(breedte);
    	stage.setMaxHeight(9000);
    	stage.setMaxWidth(9000);
    	stage.centerOnScreen();
    	stage.setScene(scene);
    	
    }
    
}
