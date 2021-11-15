package View;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Howtoplaycontroller implements Initializable {

	    @FXML
	    private Button bkbtn;
	    
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
	    void BackClicked(ActionEvent event) {
	    	closeWindow();
			ViewLogic.mainPageWindow();
	    }
	
		

}

