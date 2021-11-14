package View;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddQuestionController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddButton;

    @FXML
    private TextArea QuestionTextArea;

    @FXML
    private ImageView addQTitle;

    @FXML
    private TextField answer1Textfield;

    @FXML
    private Label answer1label;

    @FXML
    private TextField answer2Textfield;

    @FXML
    private TextField answer3Textfield;

    @FXML
    private Label answer3label;

    @FXML
    private Label answer4Label;

    @FXML
    private TextField answer4Textfield;

    @FXML
    private Label answerw2label;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<?> difficultyComboBox;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label enterAnswersLabel;

    @FXML
    private Label enterQLabel;

    @FXML
    private ImageView yeloowBackground;

    @FXML
    void initialize() {
        assert AddButton != null : "fx:id=\"AddButton\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert QuestionTextArea != null : "fx:id=\"QuestionTextArea\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert addQTitle != null : "fx:id=\"addQTitle\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer1Textfield != null : "fx:id=\"answer1Textfield\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer1label != null : "fx:id=\"answer1label\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer2Textfield != null : "fx:id=\"answer2Textfield\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer3Textfield != null : "fx:id=\"answer3Textfield\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer3label != null : "fx:id=\"answer3label\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer4Label != null : "fx:id=\"answer4Label\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answer4Textfield != null : "fx:id=\"answer4Textfield\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert answerw2label != null : "fx:id=\"answerw2label\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert difficultyComboBox != null : "fx:id=\"difficultyComboBox\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert difficultyLabel != null : "fx:id=\"difficultyLabel\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert enterAnswersLabel != null : "fx:id=\"enterAnswersLabel\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert enterQLabel != null : "fx:id=\"enterQLabel\" was not injected: check your FXML file 'AddQuestion.fxml'.";
        assert yeloowBackground != null : "fx:id=\"yeloowBackground\" was not injected: check your FXML file 'AddQuestion.fxml'.";

    }
	public AddQuestionController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
