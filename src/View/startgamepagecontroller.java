package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.Sound;
import Controller.SysData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class startgamepagecontroller implements Initializable{

	
	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="userid1"
	    private TextField userid1; // Value injected by FXMLLoader

	    @FXML // fx:id="gobtn1"
	    private Button gobtn1; // Value injected by FXMLLoader

	    @FXML // fx:id="userid2"
	    private TextField userid2; // Value injected by FXMLLoader

	    @FXML // fx:id="backbtn"
	    private Button backbtn; // Value injected by FXMLLoader

	    @FXML // fx:id="gobtn"
	    private Button gobtn; // Value injected by FXMLLoader

	    @FXML // fx:id="pane"
	    private AnchorPane pane; // Value injected by FXMLLoader

	    @FXML // fx:id="error"
	    private Label error; // Value injected by FXMLLoader

	    @FXML // fx:id="userid"
	    private TextField userid; // Value injected by FXMLLoader

	 
/*
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert userid1 != null : "fx:id=\"userid1\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert gobtn1 != null : "fx:id=\"gobtn1\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert userid2 != null : "fx:id=\"userid2\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert backbtn != null : "fx:id=\"backbtn\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert gobtn != null : "fx:id=\"gobtn\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert error != null : "fx:id=\"error\" was not injected: check your FXML file 'startgamepage.fxml'.";
	        assert userid != null : "fx:id=\"userid\" was not injected: check your FXML file 'startgamepage.fxml'.";

	    }
	    	*/


			    @Override
				public void initialize(URL location, ResourceBundle resources) {
					// TODO Auto-generated method stub

				}
				
				protected void closeWindow() {
					((Stage) pane.getScene().getWindow()).close();
				}
				
				@FXML
				private void BackClicked(ActionEvent event) {
					closeWindow();
					ViewLogic.mainPageWindow();
					//TODO
				}
				
				@FXML
				private void GoClicked(ActionEvent event) throws IOException {
					if(userid.getText()=="") {
						Sound.playSound(Sound.class.getResource("../resources/erorr.mp3"), 80);
						error.setText("Please enter your nickname");
						
					}
					else {
					closeWindow();
					SysData.CurrentPlayer= userid.getText().trim();
					ViewLogic.gameWindow();}
/*					Stage stage = new Stage();

					FXMLLoader loader = new FXMLLoader(startgamepagecontroller.class.getResource("Board.fxml"));
			        BoardControl name= new BoardControl(userid.getText().trim()); 
			        loader.setController(name);
					Parent root = loader.load();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					
					stage.setResizable(false);
					
					stage.setTitle("PackMan");
					
					
					stage.showAndWait();
					*/
					
				}
				
				@FXML
				private void Go2Clicked(ActionEvent event) throws IOException {
					if(userid1.getText()=="" && userid2.getText()=="") {
						Sound.playSound(Sound.class.getResource("../resources/erorr.mp3"), 80);
						error.setText("Please enter users nicknames");	
					} else if(userid1.getText()=="" && userid2.getText()!="") {
						Sound.playSound(Sound.class.getResource("../resources/erorr.mp3"), 80);
						error.setText("Please enter nickname for player 1");	
					} else if(userid1.getText()!="" && userid2.getText()=="") {
						Sound.playSound(Sound.class.getResource("../resources/erorr.mp3"), 80);
						error.setText("Please enter nickname for player 2");	
					}
					else {
					closeWindow();
					SysData.CurrentPlayer= userid.getText().trim();
					ViewLogic.gameWindow_2players();}
					
				}
}