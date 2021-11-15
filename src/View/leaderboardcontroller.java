package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Controller.SysData;
import Model.Game;
import Model.Player;
import Model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class leaderboardcontroller implements Initializable {


    @FXML
    private TableView<Player> leaderboardTable;

    @FXML
    private TableColumn<Player, Date> DateForBoard;

    @FXML
    private Button backbtn;

    @FXML
    private TableColumn<Player, String> usernameForBoard;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<Player, Integer> ScoreForBoard;

	private ArrayList<Player> Players;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameForBoard.setCellValueFactory(new PropertyValueFactory<>("nickname"));
		DateForBoard.setCellValueFactory(new PropertyValueFactory<>("GameHighScoreDate"));
		ScoreForBoard.setCellValueFactory(new PropertyValueFactory<>("GameHighScore")); 
		setHistoryTable();		
	}
	
	private void setHistoryTable() {
		Players = SysData.getInstance().getPlayersGames();
		ObservableList<Player> qs = FXCollections.observableArrayList(Players);
		leaderboardTable.setItems(qs);
		leaderboardTable.refresh();		
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
