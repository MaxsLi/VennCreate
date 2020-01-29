package controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuSceneController {
	
	@FXML
	private Button createNewBttn;
	
	@FXML
	private Button getExistingBttn;
	
	//Reference to the Main Application
	private MainApp mainApp;
	
	
	 /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public MenuSceneController() {
		this.setMainApp(new MainApp());
	}
	
	@FXML
	private void createNew() throws IOException {
	
	     mainApp.switchScene("shapeScene");
	}
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. 
     */
	@FXML
	private void initalize() {
    	
	}
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

   
    }
	

}
