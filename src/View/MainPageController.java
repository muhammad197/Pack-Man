package View;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import Controller.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import View.ViewLogic;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainPageController implements Initializable {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView MainPageBackground;

    @FXML
    private ImageView QMbtn;

    @FXML
    private Button insbtn;

    @FXML
    private Button ldrbtn;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button qusbtn;

    @FXML
    private ImageView rulesicon;

    @FXML
    private ImageView scoreboardbtn;

    @FXML
    private Button startbtn;

    @FXML
    private ImageView startgamebutton;

    @FXML
	private void QMClicked(ActionEvent event) {
    	Sound.playSound(Sound.class.getResource("../resources/click.mp3"), 80);
		closeWindow();
		ViewLogic.questionsManagmentWindow();
		//TODO
	}

	@FXML
	private void InstructionClicked(ActionEvent event) {
		Sound.playSound(Sound.class.getResource("../resources/click.mp3"), 80);
		closeWindow();
		ViewLogic.howToPlayWindow();
	}

	@FXML
	private void leaderBoardClicked(ActionEvent event) {
		Sound.playSound(Sound.class.getResource("../resources/click.mp3"), 80);
		closeWindow();
		ViewLogic.leaderBoardWindow();
	}

	@FXML
	private void playClicked(ActionEvent event) {
		Sound.playSound(Sound.class.getResource("../resources/click.mp3"), 80);
		closeWindow();
		ViewLogic.StartgameWindow();
		
	}

	

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	protected void closeWindow() {
		((Stage) pane.getScene().getWindow()).close();
	}



}
