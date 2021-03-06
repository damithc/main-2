//@@author A0112204E
package ui.Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	/**
	 * This is the main class that launches the application
	 * It maintains the stage/window of the application and closes the window when
	 * a particular key event is detected
	 * It does nothing else other than launching the application
	 * 
	 */
    
    private static final String APP_NAME = "CataList";
    private static final String GUI_PATH = "/ui/View/MainGUI.fxml";
    private static final String STYLESHEET_PATH = "/ui/Application/Stylesheets/MainGUI.css";
    private static final String ICON_PATH = "/ui/Application/Stylesheets/Background/catalist_icon.png";
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(GUI_PATH));
        
        stage.getIcons().add(new Image(ICON_PATH));
        stage.setTitle(APP_NAME);   
      
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
        stage.setScene(scene);
        stage.requestFocus();
        stage.show();
        
        quitProgram(scene, stage);
    }
    
    private void quitProgram(Scene scene, Stage stage) {	
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
            	stage.close();
            	Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }  
}