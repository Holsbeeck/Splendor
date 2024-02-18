package main;

import gui.StartSchermController;
import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartUp extends Application {
	
	@Override
    public void start(Stage stage)
    {
		DomeinController dc = new DomeinController(); 
        
		StartSchermController start = new StartSchermController(dc);
        Scene scene = new Scene(start, 900, 700);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("Splendor");
        //stage.setMaximized(true);
        stage.show();
    }
	
	public static void main(String... args){
		
		//maak een DC-object DomeinController
		//dc = new DomeinController(); 
		//maak een applicatie-object met als argument de
		//dc SplendorApplicatie sa = new SplendorApplicatie(dc);
		//voer de applicatie-methode met de flow vh programma uit
		//sa.startSplendorSpel(); }
		//voer de applicatie-methode met de flow vh programma uit
		
		
		launch(args);
	}
}
