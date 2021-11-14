package View;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

public class QuestionsManagmentController implements Initializable {
    
	
	public QuestionsManagmentController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> AnswersTable;

    @FXML
    private ImageView ManageQuestionsTitle;

    @FXML
    private ImageView addIcon;

    @FXML
    private ImageView background;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView editIcon;

    @FXML
    private Label questionLabel;

    @FXML
    private TableColumn<?, ?> questionsTable;

    @FXML
    void initialize() {
        assert AnswersTable != null : "fx:id=\"AnswersTable\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert ManageQuestionsTitle != null : "fx:id=\"ManageQuestionsTitle\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert addIcon != null : "fx:id=\"addIcon\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert background != null : "fx:id=\"background\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert deleteIcon != null : "fx:id=\"deleteIcon\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert editIcon != null : "fx:id=\"editIcon\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert questionLabel != null : "fx:id=\"questionLabel\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";
        assert questionsTable != null : "fx:id=\"questionsTable\" was not injected: check your FXML file 'QuestionsManagment.fxml'.";

    }

}
