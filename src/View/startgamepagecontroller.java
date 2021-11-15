package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class startgamepagecontroller implements Initializable{

			    @FXML
			    private Button gobtn;
			    
			    @FXML
			    private Button backbtn;

			    @FXML
			    private TextField userid;
			    
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
				private void GoClicked(ActionEvent event) {
					closeWindow();
					ViewLogic.gameWindow();
					//TODO
				}

}