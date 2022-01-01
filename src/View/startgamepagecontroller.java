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

			    @FXML
			    private Button gobtn;
			    
			    @FXML
			    private Button gobtn1;
			    
			    @FXML
			    private Button backbtn;

			    @FXML
			    private TextField userid;
			    
			    @FXML
			    private TextField userid1;
			    
			    @FXML
			    private TextField userid2;
			    
			    @FXML
			    private Label error;
			    
			    @FXML
				private AnchorPane pane;

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