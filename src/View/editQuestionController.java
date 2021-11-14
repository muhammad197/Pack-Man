package View;

import java.net.URL;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class editQuestionController implements Initializable {
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox answer1CheckBox;

    @FXML
    private Label answer1label;

    @FXML
    private TextField answer1textfield;

    @FXML
    private CheckBox answer2CheckBox;

    @FXML
    private Label answer2label;

    @FXML
    private TextField answer2textfield;

    @FXML
    private CheckBox answer3CheckBox;

    @FXML
    private Label answer3label;

    @FXML
    private TextField answer3textfield;

    @FXML
    private CheckBox answer4CheckBox;

    @FXML
    private Label answer4label;

    @FXML
    private TextField answer4textfield;

    @FXML
    private Button cancelbutton;

    @FXML
    private ComboBox<?> difficultyComboBox;

    @FXML
    private Label difficultyLabel;

    @FXML
    private ImageView editQuestionTitle;

    @FXML
    private Label enterQLabel;

    @FXML
    private TextArea qtextArea;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView yellowBackground;

    @FXML
    void initialize() {
        assert answer1CheckBox != null : "fx:id=\"answer1CheckBox\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer1label != null : "fx:id=\"answer1label\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer1textfield != null : "fx:id=\"answer1textfield\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer2CheckBox != null : "fx:id=\"answer2CheckBox\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer2label != null : "fx:id=\"answer2label\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer2textfield != null : "fx:id=\"answer2textfield\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer3CheckBox != null : "fx:id=\"answer3CheckBox\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer3label != null : "fx:id=\"answer3label\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer3textfield != null : "fx:id=\"answer3textfield\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer4CheckBox != null : "fx:id=\"answer4CheckBox\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer4label != null : "fx:id=\"answer4label\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert answer4textfield != null : "fx:id=\"answer4textfield\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert cancelbutton != null : "fx:id=\"cancelbutton\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert difficultyComboBox != null : "fx:id=\"difficultyComboBox\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert difficultyLabel != null : "fx:id=\"difficultyLabel\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert editQuestionTitle != null : "fx:id=\"editQuestionTitle\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert enterQLabel != null : "fx:id=\"enterQLabel\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert qtextArea != null : "fx:id=\"qtextArea\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'EditQuestion.fxml'.";
        assert yellowBackground != null : "fx:id=\"yellowBackground\" was not injected: check your FXML file 'EditQuestion.fxml'.";

    }

	public editQuestionController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
