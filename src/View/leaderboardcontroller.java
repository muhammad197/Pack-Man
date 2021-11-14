package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class leaderboardcontroller implements Initializable {

    @FXML
    private Button backbtn;
    
    @FXML
	private AnchorPane pane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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

}
