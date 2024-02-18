package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domein.DomeinController;
import dto.EdelsteenficheDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelDTO;
import dto.SpelerDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Taal;

public class SpeelVeldSchermController extends VBox {
	
	private DomeinController dc;
	private Taal taal;
	private SpelDTO spel;
	private List<SpelerDTO> spelers;
	private SpelerDTO huidigeSpeler;
	private int aantalEdelsteenfichesGepakt = 0;
	private int aantalDiamantenGepakt = 0;
	private int aantalSaffierenGepakt = 0;
	private int aantalOnyxenGepakt = 0;
	private int aantalSmaragdenGepakt = 0;
	private int aantalRobijnenGepakt = 0;
	private Timeline timeline;
	private ImageView selectedKaart;
	private ImageView selectedEdele;
	private String laatsteMelding;
	
	@FXML
	private GridPane gridDebug;
	@FXML 
	private ImageView debugLogo;
	@FXML
	private ImageView debugModus;
	@FXML 
	private Button score1;
	@FXML 
	private Button score2;
	@FXML 
	private Button score10;
	@FXML 
	private Button end;
	@FXML 
	private MenuItem optionNL;
	@FXML 
	private MenuItem optionEN;
	@FXML
	private MenuButton btnLang;
	@FXML 
	private Button btnOpties;
	@FXML
	private Button btnQuit;
	@FXML
	private Button btnBeurtOverSlaan;
	@FXML
    private Button btnKoopKaart;
	@FXML
	private Button btnKoopEdele;
	@FXML 
	private Button btnFullscreen;
	@FXML 
	private Button btnBevestigBeurt;
	@FXML
    private Label lblAantalDiamant;
    @FXML
    private Label lblAantalOnyx;
    @FXML
    private Label lblAantalRobijn;
    @FXML
    private Label lblAantalSaffier;
    @FXML
    private Label lblAantalSmaragd;
    
    @FXML
    private Label lblScoreSpeler1;
    @FXML
    private Label lblScoreSpeler2;
    @FXML
    private Label lblScoreSpeler3;
    @FXML
    private Label lblScoreSpeler4;
    
    @FXML
    private Label lblSpeler1;
    @FXML
    private Label lblSpeler2;
    @FXML
    private Label lblSpeler3;
    @FXML
    private Label lblSpeler4;
	
    @FXML
    private ImageView kaart;
    
    @FXML 
    private Label lblSpeler;
    @FXML
    private Label lblHuidigSpeler;
    @FXML
    private Label lblOutput;
    @FXML
    private Label lblTimer;
    
    @FXML
    private Label lblDS1;
    @FXML
    private Label lblRS1;
    @FXML
    private Label lblSaS1;
    @FXML
    private Label lblSmS1;
    @FXML
    private Label lblOS1;
    @FXML
    private Label lblDS2;
    @FXML
    private Label lblRS2;
    @FXML
    private Label lblSaS2;
    @FXML
    private Label lblSmS2;
    @FXML
    private Label lblOS2;
    @FXML
    private Label lblDS3;
    @FXML
    private Label lblRS3;
    @FXML
    private Label lblSaS3;
    @FXML
    private Label lblSmS3;
    @FXML
    private Label lblOS3;
    @FXML
    private Label lblDS4;
    @FXML
    private Label lblRS4;
    @FXML
    private Label lblSaS4;
    @FXML
    private Label lblSmS4;
    @FXML
    private Label lblOS4;
    @FXML
    private Label lblBDS1;
    @FXML
    private Label lblBRS1;
    @FXML
    private Label lblBSaS1;
    @FXML
    private Label lblBSmS1;
    @FXML
    private Label lblBOS1;
    @FXML
    private Label lblBDS2;
    @FXML
    private Label lblBRS2;
    @FXML
    private Label lblBSaS2;
    @FXML
    private Label lblBSmS2;
    @FXML
    private Label lblBOS2;
    @FXML
    private Label lblBDS3;
    @FXML
    private Label lblBRS3;
    @FXML
    private Label lblBSaS3;
    @FXML
    private Label lblBSmS3;
    @FXML
    private Label lblBOS3;
    @FXML
    private Label lblBDS4;
    @FXML
    private Label lblBRS4;
    @FXML
    private Label lblBSaS4;
    @FXML
    private Label lblBSmS4;
    @FXML
    private Label lblBOS4;
    @FXML 
    private GridPane spelerFiches1;
    @FXML 
    private GridPane spelerFiches2;
    @FXML 
    private GridPane spelerFiches3;
    @FXML 
    private GridPane spelerFiches4;
    @FXML 
    private ImageView ivN1;
    @FXML 
    private ImageView ivN2;
    @FXML 
    private ImageView ivN3;
    @FXML
    private Button btnReset;
    
    @FXML
    private ImageView imageDiamant;
    @FXML
    private ImageView imageOnyx;
    @FXML
    private ImageView imageRobijn;
    @FXML
    private ImageView imageSaffier;
    @FXML
    private ImageView imageSmaragd;
    
    /**
     * Constructor die alle nodige instanties initialiseert en bepaalde acties triggert om het spel te starten. 
     */
	public SpeelVeldSchermController(DomeinController dc) {
		this.dc = dc;
		this.taal = dc.getTaal();
		this.spel = dc.geefOverzichtSpel();
		this.spelers = dc.geefOverzichtSpelers();
		this.laatsteMelding = "StartNieuwSpel";
		buildGui();
		setTextComponents();
		setTimer();
	}
	
	/**
	 * Voert alle stappen uit om het speelvlak compleet te maken. 
	 */
	private void buildGui() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("SpeelVeldScherm.fxml"));
            loader.setController(this);
            loader.setRoot(this);
        	loader.load();
        	
        	initEdelen();
    		initEdelsteenfiches();
    		initSpelers();
    		initKaarten();
    		setLblHuidigSpeler();
        	
        }catch(IOException ex) {
        	System.out.println(ex.getMessage());
        	throw new RuntimeException(ex);
        }	
    }
	
	/**
	 * Zorgt ervoor dat bepaalde teksten in het spel geüpdatet worden naar de huidige taal
	 * die gekozen is. 
	 */
	private void setTextComponents() {
    	btnLang.setText(taal.geefString("BtnLang"));
    	btnQuit.setText(taal.geefString("BtnQuit"));
    	btnBeurtOverSlaan.setText(taal.geefString("BtnBeurtOverSlaan"));
    	lblSpeler.setText(taal.geefString("LblSpeler"));
    	btnOpties.setText(taal.geefString("BtnOpties"));
    	score1.setText(taal.geefString("punt1"));
    	score2.setText(taal.geefString("punt2"));
    	score10.setText(taal.geefString("punt10"));
    	optionNL.setText(taal.geefString("nederlands"));
    	optionEN.setText(taal.geefString("engels"));
    	end.setText(taal.geefString("end"));
    	btnKoopKaart.setText(taal.geefString("BtnKopen"));
    	btnKoopEdele.setText(taal.geefString("BtnEdele"));
    	btnBevestigBeurt.setText(taal.geefString("BtnBevestig"));
    	setLblOutput(laatsteMelding);
    	btnReset.setText(taal.geefString("BtnReset"));
    }
	
	/**
	 * Haalt alle spelers op uit het systeem en zet deze op de juiste plaats in het speelvlak. 
	 */
	private void initSpelers() {
		
		for(var i = 0;i < spelers.size();i++) {
			
			var speler = spelers.get(i);
			
			switch(i) {
				case 0: 
					lblSpeler1.setText(speler.gebruikersnaam());
					break;
				case 1: 
					lblSpeler2.setText(speler.gebruikersnaam());
					break;
				case 2: 
					lblSpeler3.setText(speler.gebruikersnaam());
					break;
				case 3: 
					lblSpeler4.setText(speler.gebruikersnaam());
					break;
				default: 
					break;
			}
		}
		
		if(spelers.size() < 3) {
			lblSpeler3.getParent().setVisible(false);
			lblSpeler4.getParent().setVisible(false);
			spelerFiches3.setVisible(false);
			spelerFiches4.setVisible(false);
		}else if (spelers.size() < 4) {
			lblSpeler4.getParent().setVisible(false);
			spelerFiches4.setVisible(false);
		}
		
		gridDebug.setVisible(false);
		debugLogo.setVisible(false);
		debugModus.setVisible(false);
	}
	
	/**
	 * Zorgt ervoor dat een volledig lege stapel verdwijnt 
	 */
	@FXML
	private void hideLegeStapels() {
		int aantalNiv1Kaarten = spel.niv1().size();
		int aantalNiv2Kaarten = spel.niv2().size();
		int aantalNiv3Kaarten = spel.niv3().size();
		
		if (aantalNiv1Kaarten == 0) {
		    ivN1.setVisible(false);
		}

		if (aantalNiv2Kaarten == 0) {
			ivN2.setVisible(false);
		}

		if (aantalNiv3Kaarten == 0) {
			ivN3.setVisible(false);
		}
	}
	
	/**
	 * Haalt alle edelen op uit het systeem en zet deze juist op het speelvlak. 
	 */
	private void initEdelen() {
		
		var edelen = spel.edelen();
		var gridPane = (GridPane) this.getChildren().get(0);
		
		for(var i = 0;i < edelen.size();i++) {
			
			var edele = edelen.get(i);
			var image = new Image(getClass().getResource("images/" + edele.afbeelding()).toExternalForm(), 183, 143, true, true);
			var edeleKaart = new ImageView(image);
			edeleKaart.setPreserveRatio(true);
			
			edeleKaart.getProperties().put("data-id", edele.id());
			edeleKaart.setId("edele");
			
			gridPane.add(edeleKaart, i, 1);
			
			GridPane.setHalignment(edeleKaart, HPos.CENTER);
			GridPane.setValignment(edeleKaart, VPos.CENTER);
			
			edeleKaart.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                	onClickEdele(event);
                }

            });
		}
	}
	
	/**
	 * Voert debug functionaliteit uit
	 */
	@FXML
	private void toonOpties() {
		if (gridDebug.isVisible() == true) {
			gridDebug.setVisible(false);
			debugLogo.setVisible(false);
			debugModus.setVisible(false);
		}	
		else {
			gridDebug.setVisible(true);
			debugLogo.setVisible(true);
			debugModus.setVisible(true);
		}
			
	}
	
	/**
	 * Haalt alle edelsteenfiches op uit het systeem en zet deze goed op het speelvlak. 
	 */
	private void initEdelsteenfiches() {
		var edelsteenfiches = spel.edelsteenfiches();
		
		for(var i = 0;i < edelsteenfiches.size();i++) {
			
			var fiche = edelsteenfiches.get(i);
			
			switch(i) {
				case 0: 
					lblAantalDiamant.setText(Integer.toString(fiche.aantal()));
					break;
				case 1: 
					lblAantalRobijn.setText(Integer.toString(fiche.aantal()));
					break;
				case 2: 
					lblAantalSaffier.setText(Integer.toString(fiche.aantal()));
					break;
				case 3: 
					lblAantalSmaragd.setText(Integer.toString(fiche.aantal()));
					break;
				case 4: 
					lblAantalOnyx.setText(Integer.toString(fiche.aantal()));
					break;
				default: 
					break;
			}
		}
	}
	
	/**
	 * Haalt alle ontwikkelingskaarten op en zet deze juist op het speelvlak.
	 */
	private void initKaarten() {
		initKaartenNiveau(1);
		initKaartenNiveau(2);
		initKaartenNiveau(3);
	}
	
	/**
	 * Haalt de ontwikkelingskaarten van een bepaald niveau op uit het systeem 
	 * en zet deze op de juiste plaats in het speelvlak. 
	 * @param niveau - niveau van de kaarten
	 */
	private void initKaartenNiveau(int niveau) {
		
		GridPane gridPane = ((GridPane)this.getChildren().get(0));
		
		List<OntwikkelingskaartDTO> kaarten = new ArrayList<OntwikkelingskaartDTO>();
		int rowNr = -1;
		
		switch(niveau) {
			case 1: kaarten = spel.niv1Omgedraaid(); rowNr = 4; break;
			case 2: kaarten = spel.niv2Omgedraaid(); rowNr = 3; break;
			case 3: kaarten = spel.niv3Omgedraaid(); rowNr = 2; break;
			default: break; // TODO
		}
		
		for(var i = 0; i < kaarten.size();i++) {
			var kaartDTO = kaarten.get(i);
			
			var kaart = maakImageViewKaart(kaartDTO);
			
			gridPane.add(kaart, (i + 1), rowNr);
			
			GridPane.setHalignment(kaart, HPos.CENTER);
			GridPane.setValignment(kaart, VPos.CENTER);
		}
	}
	
	/**
	 * Voert de logica uit om de taal van het spel te veranderen de gekozen taal.
	 * @param arg - event informatie die nodige data bevat om actie uit te voeren 
	 */
	@FXML
    private void setLanguage(ActionEvent arg) {
    	
    	MenuItem target = (MenuItem)arg.getSource();
    	
    	switch(target.getId()) {
    		case "optionNL": dc.setTaal("nl"); break; 
    		case "optionEN": dc.setTaal("en"); break; 
    		default: throw new RuntimeException(taal.geefString("OngeldigeInvoer"));
    	}
    	
    	taal = dc.getTaal();
    	setTextComponents();
    }
	
	/**
	 * Dit is een extra feature om het spel te versnellen. 
	 * Hiermee kan je de huidige speler een bepaald aantal punten geven .
	 * @param arg - event informatie die nodige data bevat om actie uit te voeren
	 */
	@FXML 
	private void geefPunten(ActionEvent arg) {
		
		Button target = (Button)arg.getSource();
		
		switch(target.getId()) {
			case "score1": dc.geefPunten(1); break;
			case "score2": dc.geefPunten(2); break;
			case "score10": dc.geefPunten(10); break; 
			default: throw new RuntimeException(taal.geefString("Fout"));
		}
		
		setScoreLabels();
		setFicheSpelerLabels();
		
		this.setLblOutput("PuntenToegekend");
		dc.slaBeurtOver();
		
		controleerEindeSpel();
		
		this.setLblHuidigSpeler();
		
	}
	
	@FXML
	private void resetBeurt(ActionEvent arg) {
		aantalEdelsteenfichesGepakt = 0;
		aantalDiamantenGepakt = 0;
		aantalSaffierenGepakt = 0;
		aantalOnyxenGepakt = 0;
		aantalRobijnenGepakt = 0;
		aantalSmaragdenGepakt = 0;

		// reset alles
		setFicheSpelerLabels();
		UpdateEdelsteenFicheLabels();

		 btnBevestigBeurt.setDisable(true);
		 btnBeurtOverSlaan.setDisable(false);
		 btnReset.setVisible(false);
		 return;
	}
	
	/**
	 * Voert de logica uit om het spel te beëindigen 
	 * @param arg - event informatie die nodige data bevat om actie uit te voeren
	 */
	@FXML
	private void quitOut(ActionEvent arg) 
	{
		dc.endGame();
	}
	
	/**
	 * Laat de gebruiker toe op de applicatie op fullscreen te zetten
	 * @param arg - event informatie om actie uit te voeren
	 */
	@FXML
	private void maxiScherm(ActionEvent arg){
		    Stage currentStage = (Stage) ((Node) arg.getSource()).getScene().getWindow();
		    if (currentStage.isMaximized()) {
		        currentStage.setMaximized(false);
		    } else {
		        currentStage.setMaximized(true);
		    }
		}
	
	/**
	 * Kijkt wie de huidige speler is en past dit aan in het balkje waar de 
	 * huidige speler in vermeld wordt. 
	 */
	private void setLblHuidigSpeler() 
	{
		huidigeSpeler = dc.geefHuidigeSpeler();
		lblHuidigSpeler.setText(" " + huidigeSpeler.gebruikersnaam());
	}
	
	/**
	 * Past de scores van de spelers visueel aan naar de laatste stand van zaken
	 */
	private void setScoreLabels() {
		
		// update gegevens spelers naar nieuwe situatie
		this.spelers = dc.geefOverzichtSpelers();
		
		for (int i = 0; i < spelers.size(); i++) {
		    
			var speler = spelers.get(i);
		    
		    switch(i) {
			case 0: 
				lblScoreSpeler1.setText(Integer.toString(speler.prestigePunten()));
				break;
			case 1: 
				lblScoreSpeler2.setText(Integer.toString(speler.prestigePunten()));
				break;
			case 2: 
				lblScoreSpeler3.setText(Integer.toString(speler.prestigePunten()));
				break;
			case 3: 
				lblScoreSpeler4.setText(Integer.toString(speler.prestigePunten()));
				break;
			default: 
				break;
		}
		}
		
	}
	
	/**
	 * Methode die de labels van de speler gaat goedzetten met het aantal fiches
	 */
	private void setFicheSpelerLabels() {
		this.spelers = dc.geefOverzichtSpelers();
		
		for (int i = 0; i < spelers.size(); i++) {
			
			var speler = spelers.get(i);
			var fiches = speler.fichesInBezit();
			var bonusfiches = speler.bonusfichesInBezit();
			
			int diamant = fiches.stream()
                    .filter(fiche -> fiche.naam().equals("diamant"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int saffier = fiches.stream()
                    .filter(fiche -> fiche.naam().equals("saffier"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int smaragd = fiches.stream()
                    .filter(fiche -> fiche.naam().equals("smaragd"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int onyx = fiches.stream()
                    .filter(fiche -> fiche.naam().equals("onyx"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int robijn = fiches.stream()
                    .filter(fiche -> fiche.naam().equals("robijn"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			
			int bonusDiamant = bonusfiches.stream()
                    .filter(fiche -> fiche.naam().equals("diamant"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int bonusSaffier = bonusfiches.stream()
                    .filter(fiche -> fiche.naam().equals("saffier"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int bonusSmaragd = bonusfiches.stream()
                    .filter(fiche -> fiche.naam().equals("smaragd"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int bonusOnyx = bonusfiches.stream()
                    .filter(fiche -> fiche.naam().equals("onyx"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			int bonusRobijn = bonusfiches.stream()
                    .filter(fiche -> fiche.naam().equals("robijn"))
                    .findFirst()
                    .map(EdelsteenficheDTO::aantal)
                    .orElse(0);
			
			switch(i) {
			case 0:
				lblDS1.setText(Integer.toString(diamant));
				lblRS1.setText(Integer.toString(robijn));
				lblSaS1.setText(Integer.toString(saffier));
				lblSmS1.setText(Integer.toString(smaragd));
				lblOS1.setText(Integer.toString(onyx));
				lblBDS1.setText(Integer.toString(bonusDiamant));
				lblBRS1.setText(Integer.toString(bonusRobijn));
				lblBSaS1.setText(Integer.toString(bonusSaffier));
				lblBSmS1.setText(Integer.toString(bonusSmaragd));
				lblBOS1.setText(Integer.toString(bonusOnyx));
				break;
			case 1:
				lblDS2.setText(Integer.toString(diamant));
				lblRS2.setText(Integer.toString(robijn));
				lblSaS2.setText(Integer.toString(saffier));
				lblSmS2.setText(Integer.toString(smaragd));
				lblOS2.setText(Integer.toString(onyx));
				lblBDS2.setText(Integer.toString(bonusDiamant));
				lblBRS2.setText(Integer.toString(bonusRobijn));
				lblBSaS2.setText(Integer.toString(bonusSaffier));
				lblBSmS2.setText(Integer.toString(bonusSmaragd));
				lblBOS2.setText(Integer.toString(bonusOnyx));
				break;
			case 2:
				lblDS3.setText(Integer.toString(diamant));
				lblRS3.setText(Integer.toString(robijn));
				lblSaS3.setText(Integer.toString(saffier));
				lblSmS3.setText(Integer.toString(smaragd));
				lblOS3.setText(Integer.toString(onyx));
				lblBDS3.setText(Integer.toString(bonusDiamant));
				lblBRS3.setText(Integer.toString(bonusRobijn));
				lblBSaS3.setText(Integer.toString(bonusSaffier));
				lblBSmS3.setText(Integer.toString(bonusSmaragd));
				lblBOS3.setText(Integer.toString(bonusOnyx));
				break;
			case 3:
				lblDS4.setText(Integer.toString(diamant));
				lblRS4.setText(Integer.toString(robijn));
				lblSaS4.setText(Integer.toString(saffier));
				lblSmS4.setText(Integer.toString(smaragd));
				lblOS4.setText(Integer.toString(onyx));
				lblBDS4.setText(Integer.toString(bonusDiamant));
				lblBRS4.setText(Integer.toString(bonusRobijn));
				lblBSaS4.setText(Integer.toString(bonusSaffier));
				lblBSmS4.setText(Integer.toString(bonusSmaragd));
				lblBOS4.setText(Integer.toString(bonusOnyx));
				break;
			}
			
		}
	}
		
	/**
	 * Zet de tekst in de informatiebalk van het spel
	 * @param msg - de tekst die in de balk moet komen 
	 */
	private void setLblOutput(String key) {
		this.laatsteMelding = key;
		lblOutput.setText(taal.geefString(key));
	}
	
	/**
	 * voert de logica uit om een beurt over te slaan en toont dit visueel en reset het aantal genomen fiches
	 * @param e - event informatie die nodige data bevat om actie uit te voeren
	 */
	@FXML
	private void slaBeurtOver(ActionEvent e){
		this.setLblOutput("SlaBeurtOver");
		dc.slaBeurtOver();
		this.setLblHuidigSpeler();
		this.setBordersBeschikbareKaarten();
		aantalEdelsteenfichesGepakt = 0;
		aantalDiamantenGepakt = 0;
		aantalSaffierenGepakt = 0;
		aantalOnyxenGepakt = 0;
		aantalRobijnenGepakt = 0;
		aantalSmaragdenGepakt = 0;
		controleerEindeSpel();
	}
	
	/**
	 * initialiseert een timer en start deze op, zodat het tijdsverloop van het spel getoond kan worden.
	 */
	private void setTimer() {
		this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			int seconds = 0;
			
            @Override
            public void handle(ActionEvent event) {
            	seconds++;
            	int minutes = seconds / 60;
                int restSeconds = seconds % 60;
                lblTimer.setText(String.format("%02d:%02d", minutes, restSeconds));
            }
        }));
		
		timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
	}
	
	private void UpdateEdelsteenFicheLabels() {
		this.spel = dc.geefOverzichtSpel();
		
		int smaragden;
		int robijnen;
		int saffieren;
		int diamanten;
		int onyxen;
		
		for (EdelsteenficheDTO edelsteen: dc.geefOverzichtSpel().edelsteenfiches()) {
		if ( edelsteen.naam()=="smaragd") { 
			smaragden = edelsteen.aantal();
		lblAantalSmaragd.setText(Integer.toString(smaragden - aantalSmaragdenGepakt));
		}
		if ( edelsteen.naam()=="robijn") { 
			robijnen = edelsteen.aantal();
			lblAantalRobijn.setText(Integer.toString(robijnen - aantalRobijnenGepakt));
		
		}
		if ( edelsteen.naam()=="saffier") {
			saffieren = edelsteen.aantal();
			lblAantalSaffier.setText(Integer.toString(saffieren - aantalSaffierenGepakt));
		}
		if ( edelsteen.naam()=="diamant") {
			diamanten = edelsteen.aantal();
			lblAantalDiamant.setText(Integer.toString(diamanten - aantalDiamantenGepakt));
		}
		if ( edelsteen.naam()=="onyx") {
			onyxen = edelsteen.aantal();
			lblAantalOnyx.setText(Integer.toString(onyxen - aantalOnyxenGepakt));
		}
		}
	}
	
	private void setDisabilitiesButtons() {
		if( aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.setDisable(false);
			btnBeurtOverSlaan.setDisable(true);
			btnReset.setVisible(true);
		} 
	}
	
	private boolean isGeldigeEdelsteenFiche(String type) {
		
		int huidigAantal = spel
				.edelsteenfiches()
				.stream()
				.filter(e -> e.naam() == type)
				.findFirst()
				.get()
				.aantal();
		
		switch(type) {
			case "smaragd":
				if((huidigAantal - aantalSmaragdenGepakt) <= 0){
					return false;
				} else if (aantalSmaragdenGepakt < 2 && 
					aantalDiamantenGepakt == 0 && 
					aantalOnyxenGepakt == 0 && 
					aantalSaffierenGepakt == 0 && 
					aantalRobijnenGepakt == 0) {
					return true;
				} else if (
					aantalSmaragdenGepakt == 0 && 
					aantalDiamantenGepakt < 2 && 
					aantalOnyxenGepakt < 2 && 
					aantalSaffierenGepakt < 2 && 
					aantalRobijnenGepakt < 2 &&
					aantalEdelsteenfichesGepakt < 3) {
					return true;
				} else {
					return false;
				}
			case "diamant":
				if((huidigAantal - aantalDiamantenGepakt) <= 0){
					return false;
				} else if (aantalSmaragdenGepakt == 0 && 
					aantalDiamantenGepakt < 2 && 
					aantalOnyxenGepakt == 0 && 
					aantalSaffierenGepakt == 0 && 
					aantalRobijnenGepakt == 0) {
					return true;
				} else if (
					aantalSmaragdenGepakt < 2 && 
					aantalDiamantenGepakt == 0 && 
					aantalOnyxenGepakt < 2 && 
					aantalSaffierenGepakt < 2 && 
					aantalRobijnenGepakt < 2 &&
					aantalEdelsteenfichesGepakt < 3) {
					return true;
				} else {
					return false;
				}
			case "saffier": 
				if((huidigAantal - aantalSaffierenGepakt) <= 0){
					return false;
				} else if (aantalSmaragdenGepakt == 0 && 
					aantalDiamantenGepakt == 0 && 
					aantalOnyxenGepakt == 0 && 
					aantalSaffierenGepakt < 2 && 
					aantalRobijnenGepakt == 0) {
					return true;
				} else if (
					aantalSmaragdenGepakt < 2 && 
					aantalDiamantenGepakt < 2 && 
					aantalOnyxenGepakt < 2 && 
					aantalSaffierenGepakt == 0 && 
					aantalRobijnenGepakt < 2 &&
					aantalEdelsteenfichesGepakt < 3) {
					return true;
				} else {
					return false;
				}
			case "onyx": 
				if((huidigAantal - aantalOnyxenGepakt) <= 0){
					return false;
				} else if (aantalSmaragdenGepakt == 0 && 
					aantalDiamantenGepakt == 0 && 
					aantalOnyxenGepakt < 2 && 
					aantalSaffierenGepakt == 0 && 
					aantalRobijnenGepakt == 0) {
					return true;
				} else if (
					aantalSmaragdenGepakt < 2 && 
					aantalDiamantenGepakt < 2 && 
					aantalOnyxenGepakt == 0 && 
					aantalSaffierenGepakt < 2 && 
					aantalRobijnenGepakt < 2 &&
					aantalEdelsteenfichesGepakt < 3) {
					return true;
				} else {
					return false;
				}
			case "robijn": 
				if((huidigAantal - aantalRobijnenGepakt) <= 0){
					return false;
				} else if (aantalSmaragdenGepakt == 0 && 
					aantalDiamantenGepakt == 0 && 
					aantalOnyxenGepakt == 0 && 
					aantalSaffierenGepakt == 0 && 
					aantalRobijnenGepakt < 2) {
					return true;
				} else if (
					aantalSmaragdenGepakt < 2 && 
					aantalDiamantenGepakt < 2 && 
					aantalOnyxenGepakt < 2 && 
					aantalSaffierenGepakt < 2 && 
					aantalRobijnenGepakt == 0 &&
					aantalEdelsteenfichesGepakt < 3) {
					return true;
				} else {
					return false;
				}
				default: 
					return false;
		}
	}
	
	/**
	* laat toe om een diamant te kunnen pakken of weer op de stapel te leggen 
	* als de speler te veel edelsteenfiches heeft.
	* @param MousEvent - op de afbeelding van de diamant geklikt wordt
	*/
	@FXML
	private void pakDiamant(MouseEvent mousevent) {
		
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
			btnKoopKaart.setDisable(true);
		}
		
		String fiche = "diamant"; 
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			setLblOutput("ReturnDiamant");
			try {
				dc.kiesTeruggaveEdelsteenfiches("diamant", 1);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
				return;
			}
			if(!dc.heeftSpelerTeVeelEdelsteenfiches()) {
				setLblOutput("EndBeurt");
				dc.slaBeurtOver();
				lblHuidigSpeler.setText(dc.geefHuidigeSpeler().gebruikersnaam());
				btnBeurtOverSlaan.setDisable(false);
				resetGlowEdelsteenfiches();
			}
		}
		else {
			if (isGeldigeEdelsteenFiche(fiche))	{
				try {
					aantalEdelsteenfichesGepakt++;
					aantalDiamantenGepakt++;
					setLblOutput("DiamantGenomen");
				} catch (Exception e) {
					setLblOutput("StapelLeeg");
				}
			}			
			else {
				 setLblOutput("GeenDiamantPakken");
			} 
		setDisabilitiesButtons();
		}
		
		UpdateEdelsteenFicheLabels();
		
		setDisabilitiesButtons();
		
		// zet focus op knop bevestig
		if(aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.requestFocus();
		}
		setFicheSpelerLabels();
	}
	/**
	* laat toe om een saffier te kunnen pakken of weer op de stapel te leggen 
	* als de speler te veel edelsteenfiches heeft.
	* @param MousEvent - op de afbeelding van de saffier geklikt wordt
	*/	
	@FXML
	private void pakSaffier(MouseEvent mousevent) {
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
			btnKoopKaart.setDisable(true);
		}
		
		String fiche = "saffier";
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			setLblOutput("ReturnSaffier");
			try {
				dc.kiesTeruggaveEdelsteenfiches("saffier", 1);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
				return;
			}
			if(!dc.heeftSpelerTeVeelEdelsteenfiches()) {
				setLblOutput("EndBeurt");
				dc.slaBeurtOver();
				lblHuidigSpeler.setText(dc.geefHuidigeSpeler().gebruikersnaam());
				btnBeurtOverSlaan.setDisable(false);
				resetGlowEdelsteenfiches();
			}
		}
		else {
		if (isGeldigeEdelsteenFiche(fiche))	{
			try {
				aantalEdelsteenfichesGepakt++;
				aantalSaffierenGepakt++;
				setLblOutput("SaffierGenomen");
			} catch (Exception e) {
				setLblOutput("StapelLeeg");
			}
		}			
		 else {
			 setLblOutput("GeenSaffierPakken");
		 } 
		setDisabilitiesButtons();
		}
		
		UpdateEdelsteenFicheLabels();
		
		setDisabilitiesButtons();
		
		// zet focus op knop bevestig
		if(aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.requestFocus();
		}
		setFicheSpelerLabels();
	}
	
	@FXML
	/**
	* laat toe om een onyx te kunnen pakken of weer op de stapel te leggen 
	* als de speler te veel edelsteenfiches heeft.
	* @param MousEvent - op de afbeelding van de onyx geklikt wordt
	*/
	private void pakOnyx(MouseEvent mousevent) {
		
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
			btnKoopKaart.setDisable(true);
		}
		
		String fiche = "onyx";
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			setLblOutput("ReturnOnyx");
			try {
				dc.kiesTeruggaveEdelsteenfiches("onyx", 1);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
				return;
			}
			if(!dc.heeftSpelerTeVeelEdelsteenfiches()) {
				setLblOutput("EndBeurt");
				dc.slaBeurtOver();
				lblHuidigSpeler.setText(dc.geefHuidigeSpeler().gebruikersnaam());
				btnBeurtOverSlaan.setDisable(false);
				resetGlowEdelsteenfiches();
			}
		}
		else {
		if (isGeldigeEdelsteenFiche(fiche))	{
			try {
				aantalEdelsteenfichesGepakt++;
				aantalOnyxenGepakt++;
				setLblOutput("OnyxGenomen");
			} catch (Exception e) {
				setLblOutput("StapelLeeg");
			}
		}			
		 else {
			 setLblOutput("GeenOnyxPakken");
		 } 
		setDisabilitiesButtons();
		}
		
		UpdateEdelsteenFicheLabels();
		
		setDisabilitiesButtons();
		
		// zet focus op knop bevestig
		if(aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.requestFocus();
		}
		setFicheSpelerLabels();
	}
	
	/**
	* laat toe om een smaragd te kunnen pakken of weer op de stapel te leggen 
	* als de speler te veel edelsteenfiches heeft.
	* @param MousEvent - op de afbeelding van de smaragd geklikt wordt
	*/
	@FXML
	private void pakSmaragd(MouseEvent mousevent) {
		
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
			btnKoopKaart.setDisable(true);
		}
		
		String fiche = "smaragd";
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			setLblOutput("ReturnSmaragd");
			try {
				dc.kiesTeruggaveEdelsteenfiches("smaragd", 1);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
				return;
			}
			if(!dc.heeftSpelerTeVeelEdelsteenfiches()) {
				setLblOutput("EndBeurt");
				dc.slaBeurtOver();
				lblHuidigSpeler.setText(dc.geefHuidigeSpeler().gebruikersnaam());
				btnBeurtOverSlaan.setDisable(false);
				resetGlowEdelsteenfiches();
			}
		}
		else {
		if (isGeldigeEdelsteenFiche(fiche))	{
			try {
				aantalEdelsteenfichesGepakt++;
				aantalSmaragdenGepakt++;
				setLblOutput("SmaragdGenomen");
			} catch (Exception e) {
				setLblOutput("StapelLeeg");
			}
		}			
		 else {
			 setLblOutput("GeenSmaragdPakken");
		 } 
		setDisabilitiesButtons();
		}
		
		UpdateEdelsteenFicheLabels();		
		setDisabilitiesButtons();
		
		// zet focus op knop bevestig
		if(aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.requestFocus();
		}

		setFicheSpelerLabels();
	}
	
	/**
	* laat toe om een robijn te kunnen pakken of weer op de stapel te leggen 
	* als de speler te veel edelsteenfiches heeft.
	* @param MousEvent - op de afbeelding van de robbijn geklikt wordt
	*/
	@FXML
	private void pakRobijn(MouseEvent mousevent) {
		
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
			btnKoopKaart.setDisable(true);
		}
		
		String fiche = "robijn";
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			setLblOutput("ReturnRobijn");
			try {
				dc.kiesTeruggaveEdelsteenfiches("robijn", 1);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
				return;
			}
			if(!dc.heeftSpelerTeVeelEdelsteenfiches()) {
				setLblOutput("EndBeurt");
				dc.slaBeurtOver();
				lblHuidigSpeler.setText(dc.geefHuidigeSpeler().gebruikersnaam());
				btnBeurtOverSlaan.setDisable(false);
				resetGlowEdelsteenfiches();
			}
		}
		else {
		if (isGeldigeEdelsteenFiche(fiche))	{
			try {
				aantalEdelsteenfichesGepakt++;
				aantalRobijnenGepakt++;
				setLblOutput("RobijnGenomen");
			} catch (Exception e) {
				setLblOutput("StapelLeeg");
			}
		}			
		 else {
			 setLblOutput("GeenRobijnPakken");
		 } 
		setDisabilitiesButtons();
		}
		
		UpdateEdelsteenFicheLabels();
		
		setDisabilitiesButtons();
		
		// zet focus op knop bevestig
		if(aantalEdelsteenfichesGepakt > 0) {
			btnBevestigBeurt.requestFocus();
		}
		setFicheSpelerLabels();
	}

	/**
	 * Einde spel handmatig forceren
	 * @param arg - Actie in menu met andere opties van voorstelling en debugging.
	 */
	@FXML
	private void forceerEinde(ActionEvent arg) {
		String tijd = geefTijd();
		toonEindeSpelDialoog(tijd);
	}
	
	/**
	 *  Geeft de verstreken tijd
	 */
	private String geefTijd(){
		
		String tijd = lblTimer.getText();
	    return tijd;
	    
	}
	
	/**
	 * Controleert of het spel ten einde is en voert dan de nodige acties uit. 
	 */
	private void controleerEindeSpel() {
		if(dc.eindeRonde() && dc.isEindeSpel()) {
			// stop klok
			timeline.stop();
			String tijd = geefTijd();
			// toon dialoog
			toonEindeSpelDialoog(tijd);
		}
	}
	
	/**
	 * Toont het scherm EindeSpel indien spel gedaan is 
	 */
	@FXML
	private void toonEindeSpelDialoog(String tijd) {
		var eindeSpelScherm = new EindeSpelSchermController(dc, tijd); 
    	Scene huidigeScene = this.getScene();
    	var breedte = 520;
    	var hoogte = 480;
    	var scene = new Scene(eindeSpelScherm, breedte, hoogte);
    	Stage stage = (Stage)huidigeScene.getWindow();
    	
    	stage.setMinHeight(hoogte);
    	stage.setMinWidth(breedte);
    	stage.setMaxHeight(hoogte);
    	stage.setMaxWidth(breedte);
    	stage.centerOnScreen();
    	stage.setScene(scene);
	}
	
	/**
	* bevestigd de beurt als de speler edelsteenfiches heeft gekozen en controleert op teveel edelsteenfiches genomen
	* @param ActionEvent - als op de knop "Bevestig beurt" geklikt wordt
	*/
	@FXML
	private void bevestigBeurt(ActionEvent e) {
		
		try {
			koopEdelsteenfiches();
		}catch(RuntimeException ex) {
			setLblOutput(ex.getMessage());
			
			aantalEdelsteenfichesGepakt = 0;
			aantalDiamantenGepakt = 0;
			aantalSaffierenGepakt = 0;
			aantalOnyxenGepakt = 0;
			aantalRobijnenGepakt = 0;
			aantalSmaragdenGepakt = 0;
			
			// reset alles
			setFicheSpelerLabels();
			UpdateEdelsteenFicheLabels();
			
			btnBevestigBeurt.setDisable(true);
			btnBeurtOverSlaan.setDisable(false);
			btnReset.setVisible(false);
			return;
		}
		
		if (dc.heeftSpelerTeVeelEdelsteenfiches()) {
			
			aantalEdelsteenfichesGepakt = 0;
			aantalDiamantenGepakt = 0;
			aantalSaffierenGepakt = 0;
			aantalOnyxenGepakt = 0;
			aantalRobijnenGepakt = 0;
			aantalSmaragdenGepakt = 0;
			
			setGlowEdelsteenfiches();
			resetBordersBeschikbareKaarten();
			
			btnBeurtOverSlaan.setDisable(true);
			btnBevestigBeurt.setDisable(true);
			btnKoopKaart.setDisable(true);
			btnKoopEdele.setDisable(true);
			btnReset.setVisible(false);
			setLblOutput("Teruggave");
			setFicheSpelerLabels();
			return;
		}
		
		try {
			dc.slaBeurtOver();
			this.setLblHuidigSpeler();
			this.setBordersBeschikbareKaarten();
			controleerEindeSpel();
		} catch(RuntimeException ex) {
			setLblOutput(ex.getMessage());
		}
		
		aantalEdelsteenfichesGepakt = 0;
		aantalDiamantenGepakt = 0;
		aantalSaffierenGepakt = 0;
		aantalOnyxenGepakt = 0;
		aantalRobijnenGepakt = 0;
		aantalSmaragdenGepakt = 0;
		
		setFicheSpelerLabels();
		UpdateEdelsteenFicheLabels();
		
		btnBevestigBeurt.setDisable(true);
		btnBeurtOverSlaan.setDisable(false);
		btnReset.setVisible(false);
		
		this.setLblOutput("BevestigBeurt");
	}

	/**
	 * Voert effectief het nemen van edelsteenfiches uit
	 */
	private void koopEdelsteenfiches() {
		if(aantalSmaragdenGepakt > 0) {
			dc.kiesEdelsteenfiches("smaragd", aantalSmaragdenGepakt);
		}
		if(aantalDiamantenGepakt > 0) {
			dc.kiesEdelsteenfiches("diamant", aantalDiamantenGepakt);
		}
		if(aantalSaffierenGepakt > 0) {
			dc.kiesEdelsteenfiches("saffier", aantalSaffierenGepakt);
		}
		if(aantalOnyxenGepakt > 0) {
			dc.kiesEdelsteenfiches("onyx", aantalOnyxenGepakt);
		}
		if(aantalRobijnenGepakt > 0) {
			dc.kiesEdelsteenfiches("robijn", aantalRobijnenGepakt);
		}
	}
	
	/**
	 * Zet de glow van de edelsteenfiches indien er terug gegeven moet worden 
	 */
	private void setGlowEdelsteenfiches() {
		var dropShadow = new DropShadow();
		dropShadow.setColor(Color.RED);
		dropShadow.setWidth(100);
		dropShadow.setHeight(100);
		dropShadow.setRadius(25);
		
		imageDiamant.setEffect(dropShadow);
		imageRobijn.setEffect(dropShadow);
		imageOnyx.setEffect(dropShadow);
		imageSaffier.setEffect(dropShadow);
		imageSmaragd.setEffect(dropShadow);
	}
	
	/**
	 * Verwijdert de glow van de edelsteenfiches indien er niet meer terug gegeven moet worden 
	 */
	private void resetGlowEdelsteenfiches() {
		
		imageDiamant.setEffect(null);
		imageRobijn.setEffect(null);
		imageOnyx.setEffect(null);
		imageSaffier.setEffect(null);
		imageSmaragd.setEffect(null);
	}
	
	// region ONTWIKKELINGSKAART
	
	/**
	 * Zet een glow rond alle beschikbare kaarten 
	 */
	private void setBordersBeschikbareKaarten() {
		var beschikbareKaarIds = dc.geefBeschikbareKaarten()
			.stream()
			.map(k -> k.id())
			.toList();
		
		GridPane gridPane = ((GridPane)this.getChildren().get(0));
		
		// loop over rijen
		for(int i = 2;i < 5;i++) {
			// loop over kolommen
			for(int j = 1;j < 5;j++) {
				ImageView kaart = (ImageView)getNodeFromGrid(i,j,gridPane);
				
				if(kaart == null) continue;
				
				int id = (int)kaart.getProperties().get("data-id");
				// beschikbaar => set glow
				if(beschikbareKaarIds.contains(id)) {
					var dropShadow = new DropShadow();
					dropShadow.setColor(Color.GREEN);
					dropShadow.setWidth(100);
					dropShadow.setHeight(100);
					dropShadow.setRadius(25);
					kaart.setEffect(dropShadow);
				}else { // reset
					kaart.setEffect(null);
					kaart.setStyle(null);
				}
			}
		}
	}
	
	/**
	 * Verwijdert de glow rond alle beschikbare kaarten 
	 */
	private void resetBordersBeschikbareKaarten() {
		
		var gridPane = (GridPane)this.getChildren().get(0);
		
		// loop over rijen
		for(int i = 2;i < 5;i++) {
			// loop over kolommen
			for(int j = 1;j < 5;j++) {
				ImageView kaart = (ImageView)getNodeFromGrid(i,j,gridPane);
				kaart.setEffect(null);
				kaart.setStyle(null);
			}
		}
	}
	
	/**
	 * Zoekt een Node op uit de GridPane op basis van rij en kolom en geeft deze terug
	 * @param row - rij index
	 * @param column - kolom index 
	 * @param gridPane - parent om uit te zoeken
	 */
	private Node getNodeFromGrid (int row, int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> children = gridPane.getChildren();

	    for (Node node : children) {
	    	var rowNode = GridPane.getRowIndex(node);
	    	var colNode = GridPane.getColumnIndex(node);
	        if(node != null && rowNode != null && colNode != null && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	/**
	 * Voert de logica uit als er op een kaart geklikt wordt om te kopen 
	 */
	@FXML
	private void onClickKaart(MouseEvent event) {
		
		// edelsteenfiche genomen of er moeten nog stenen teruggeven worden => voorkom click
		if(aantalEdelsteenfichesGepakt > 0 || dc.heeftSpelerTeVeelEdelsteenfiches()) {
			return;
		}
		
		var kaart = (ImageView)event.getSource();
		int id = (int)kaart.getProperties().get("data-id");
		
		var beschikbareKaarIds = dc.geefBeschikbareKaarten()
			.stream()
			.map(k -> k.id())
			.toList();
		
		// reset vorige kaart indien nieuwe kaart aangeklikt wordt
		if(selectedKaart != null) {
			selectedKaart.setStyle(null);
		}
		
		System.out.println(beschikbareKaarIds.contains(id));
		
		if(beschikbareKaarIds.contains(id)) {
			kaart.setStyle("-fx-opacity: 0.5");
			btnKoopKaart.setDisable(false);
			selectedKaart = kaart;
			btnBeurtOverSlaan.setDisable(true);
			btnReset.setVisible(false);
			
		}else {
			btnKoopKaart.setDisable(true);
			setLblOutput("KaartNietBeschikbaar");
		}
	}
	
	/**
	 * Voert de logica uit om een kaart te kopen en de gevolgen die hieraan verbonden zijn. 
	 */
	@FXML
	private void koopKaart(MouseEvent event) {
		
		var beschikbareKaarIds = dc.geefBeschikbareKaarten()
			.stream()
			.map(k -> k.id())
			.toList();
		
		int selectedKaartId = (int)selectedKaart.getProperties().get("data-id");
		
		if(beschikbareKaarIds.contains(selectedKaartId)) {
			
			try {
				dc.kiesOntwikkelingskaart(selectedKaartId);
			} catch(RuntimeException ex) {
				setLblOutput(ex.getMessage());
			}
			
			setLblOutput("KaartGekocht");
			
			btnKoopKaart.setDisable(true);
			
			// update score labels en edelsteenfiche labels
			setScoreLabels();
			setFicheSpelerLabels();
			UpdateEdelsteenFicheLabels();
			
			// switch oude met nieuwe kaart
			vervangOudeMetNieuweKaart();
			
			// reset geselecteerde kaart
			selectedKaart = null;
			
			// geen edele beschikbaar om te kopen => naar de volgende speler
			if(!controleerBeschikbareEdelen()) {
				dc.slaBeurtOver();
				setLblHuidigSpeler();
				// toon beschikbare kaarten voor volgende speler
				this.setBordersBeschikbareKaarten();
				
				controleerEindeSpel();
				
				btnBeurtOverSlaan.setDisable(false);
				btnReset.setVisible(false);
			}
			
		}else {
			setLblOutput("KaartNietBeschikbaar");
		}
	}
	
	/**
	 * Vervangt de gekochte kaart door de nieuwe omgedraaide kaart
	 */
	private void vervangOudeMetNieuweKaart() {
		
		GridPane gridPane = ((GridPane)this.getChildren().get(0));
		List<OntwikkelingskaartDTO> nieuweKaarten;
		
		switch((int)selectedKaart.getProperties().get("data-niv")) {
			case 1: nieuweKaarten = spel.niv1Omgedraaid(); break;
			case 2: nieuweKaarten = spel.niv2Omgedraaid(); break;
			case 3: nieuweKaarten = spel.niv3Omgedraaid(); break;
			default: nieuweKaarten = new ArrayList<OntwikkelingskaartDTO>();
		}
		
		// nieuwste kaart is altijd op index 3
		// omdat eerste 4 kaarten van lijst getoond worden
		// en als er kaart verwijderd wordt, is die op 3 de nieuwste
		var nieuweKaart = nieuweKaarten.get(3);
		
		var kaart = maakImageViewKaart(nieuweKaart);
		
		GridPane.setHalignment(kaart, HPos.CENTER);
		GridPane.setValignment(kaart, VPos.CENTER);
		
		// bewaar rij en kolom geselecteerde kaart
		var indexRow = GridPane.getRowIndex(selectedKaart);
		var indexColumn = GridPane.getColumnIndex(selectedKaart);
		
		// switch kaarten
		gridPane.getChildren().remove(selectedKaart);
		gridPane.add(kaart, indexColumn, indexRow);
		
		hideLegeStapels();
	}
	
	/**
	 * maakt ImageView aan en zet alle properties van ontwikkelingskaart goed 
	 * @param nieuweKaart - gegevens van de nieuwe kaart
	 * @return Imageview: visueel component waarbij alles goed staat 
	 */
	private ImageView maakImageViewKaart(OntwikkelingskaartDTO nieuweKaart) {
		var image = new Image(getClass().getResource("images/" + nieuweKaart.afbeelding()).toExternalForm(),183,143,true,true);
		var kaart = new ImageView(image);
		
		kaart.setPreserveRatio(true);
		
		kaart.getProperties().put("data-id", nieuweKaart.id());
		kaart.getProperties().put("data-niv", nieuweKaart.niveau());
		kaart.setId("kaart");
		
		kaart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	onClickKaart(event);
            }

        });
		
		return kaart;
	}
	
	// endregion ONTWIKKELINGSKAART
	
	// region EDELE

	/**
	 * Controleert of er beschikbare edelen zijn en duidt dit visueel aan indien nodig.
	 * Als er maar 1 edele beschikbaar is, wordt deze auto aangekocht
	 */
	private boolean controleerBeschikbareEdelen() {
		
		var beschikbareEdeleIds = dc.geefBeschikbareTegels()
				.stream()
				.map(k -> k.id())
				.toList();
		
		if(beschikbareEdeleIds.size() == 0) {
			return false;
		}
		
		// indien we hier geraken, weten we dat er beschikbare edelen zijn
		if(beschikbareEdeleIds.size() > 1) {
			setLblOutput("KiesEdele");
			setBordersBeschikbareEdelen();
		}else { // aantal beschikbare edelen is niet 0 en niet groter dan 1 => = 1
			
			var BeschikbaarId = beschikbareEdeleIds.get(0);
			GridPane gridPane = ((GridPane)this.getChildren().get(0));
			
			// loop over kolommen van rij 1
			for(int j = 0;j < 5;j++) {
				ImageView edele = (ImageView)getNodeFromGrid(1,j,gridPane);
				
				if(edele == null) continue;
				
				int id = (int)edele.getProperties().get("data-id");
				// beschikbaar => set glow
				if(BeschikbaarId == id) {
					// enigste beschikbare edele => zetten als geselecteerde
					selectedEdele = edele;
					
					btnKoopEdele.fireEvent(new MouseEvent(
						MouseEvent.MOUSE_CLICKED,
						0, 
						0, 
						0,
						0, 
						MouseButton.PRIMARY, 
						1, 
						true, true, true, true, true, true, true, true, true, true, true, true, null));
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Voert de logica uit als er op een edele geklikt wordt om te kopen 
	 */
	@FXML
	private void onClickEdele(MouseEvent event) {
		
		var edele = (ImageView)event.getSource();
		int id = (int)edele.getProperties().get("data-id");
		
		// om het aankopen van edele te testen
		if(event.isControlDown()) {
			selectedEdele = edele;
			koopGeselecteerdeEdele();
		}
		
		var beschikbareEdeleIds = dc.geefBeschikbareTegels()
			.stream()
			.map(k -> k.id())
			.toList();
		
		if(beschikbareEdeleIds.contains(id)) {
			edele.setStyle("-fx-opacity: 0.5");
			
			btnKoopEdele.setDisable(false);
			
			selectedEdele = edele;
		}
	}
	
	/**
	 * Voert de logica uit om een edele te kopen en de gevolgen die hieraan verbonden zijn.
	 * Deze controleert ook bepaalde zaken hiervoor. 
	 */
	@FXML
	private void koopEdele(MouseEvent event) {
		
		var beschikbareEdeleIds = dc.geefBeschikbareTegels()
			.stream()
			.map(k -> k.id())
			.toList();
		
		int selectedEdeleId = (int)selectedEdele.getProperties().get("data-id");
		
		if(beschikbareEdeleIds.contains(selectedEdeleId)) {
			
			koopGeselecteerdeEdele();
			
		}else {
			setLblOutput("EdeleNietBeschikbaar");
		}
	}
	
	/**
	 * Zet een glow rond alle beschikbare edelen 
	 */
	private void setBordersBeschikbareEdelen() {
		var beschikbareEdeleIds = dc.geefBeschikbareKaarten()
			.stream()
			.map(k -> k.id())
			.toList();
		
		GridPane gridPane = ((GridPane)this.getChildren().get(0));
		
		// loop over kolommen van rij 1
		for(int j = 0;j < 5;j++) {
			ImageView edele = (ImageView)getNodeFromGrid(1,j,gridPane);
			
			if(edele == null) continue;
			
			int id = (int)edele.getProperties().get("data-id");
			// beschikbaar => set glow
			if(beschikbareEdeleIds.contains(id)) {
				var dropShadow = new DropShadow();
				dropShadow.setColor(Color.GREEN);
				dropShadow.setWidth(50);
				dropShadow.setHeight(50);
				dropShadow.setRadius(25);
				edele.setEffect(dropShadow);
			}else { // reset
				edele.setEffect(null);
			}
		}
	}
	
	/**
	 * Voert de logica uit om een geselecteerde edele aan te kopen.
	 */
	private void koopGeselecteerdeEdele() {
		
		int selectedEdeleId = (int)selectedEdele.getProperties().get("data-id");
		
		try {
			dc.kiesTegel(selectedEdeleId);
			System.out.println(dc.geefHuidigeSpeler());
			dc.slaBeurtOver();
			setLblHuidigSpeler();
		} catch(RuntimeException ex) {
			setLblOutput(ex.getMessage());
			return;
		}
		
		setLblOutput("EdeleGekocht");
		
		btnKoopEdele.setDisable(true);
		
		// update score labels en edelsteenfiche labels
		setScoreLabels();
		setFicheSpelerLabels();
		UpdateEdelsteenFicheLabels();
		
		GridPane gridPane = ((GridPane)this.getChildren().get(0));
		gridPane.getChildren().remove(selectedEdele);
		
		// reset geselecteerde kaart
		selectedEdele = null;
		
		// toon beschikbare kaarten voor volgende speler
		this.setBordersBeschikbareKaarten();
		
		controleerEindeSpel();
		
		btnBeurtOverSlaan.setDisable(false);
		btnReset.setVisible(false);
	}
	
	// endregion EDELE
}


