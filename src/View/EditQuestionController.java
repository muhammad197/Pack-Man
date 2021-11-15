package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Answer;
import Model.Question;
import Utils.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditQuestionController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ans3;

    @FXML
    private ImageView titlePhoto;

    @FXML
    private ComboBox<Level> levelCombo;

    @FXML
    private CheckBox checkans4;

    @FXML
    private CheckBox checkans3;

    @FXML
    private TextField ans4;

    @FXML
    private CheckBox checkans2;

    @FXML
    private CheckBox checkans1;

    @FXML
    private TextArea questionInput;

    @FXML
    private TextField answ1;

    @FXML
    private TextField answ2;

    @FXML
    private Button saveBtn;
    
    public Question QuestionToEdit;
    

    @FXML
    void SaveQuestion(ActionEvent event) {
    	String quest = questionInput.getText().trim();
		String answer1 = answ1.getText().trim();
		String answer2 = answ2.getText().trim();
		String answer3 = ans3.getText().trim();
		String answer4 = ans4.getText().trim();
		boolean ans1Correct = checkans1.isSelected();
		boolean ans2Correct = checkans2.isSelected();
		boolean ans3Correct = checkans3.isSelected();
		boolean ans4Correct = checkans4.isSelected();
		Level level = levelCombo.getSelectionModel().getSelectedItem();

    }
    
   
    
    
	public EditQuestionController(Question questionToEdit) {
		super();
		QuestionToEdit = questionToEdit;
	}




	public EditQuestionController() {
	}




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		levelCombo.getItems().setAll(Level.values());
			if(QuestionToEdit == null)
			{
		        Image image = new Image(getClass().getResourceAsStream("../resources/addQuestion_title.png"));
		        titlePhoto.setImage(image);

				questionInput.setText("");
				answ1.setText("");
				checkans1.setSelected(false);
				answ2.setText("");
				checkans2.setSelected(false);
				ans3.setText("");
				checkans3.setSelected(false);
				ans4.setText("");
				checkans4.setSelected(false);
			}
			else {
				Image image = new Image(getClass().getResourceAsStream("../resources/editQuestion_title.png"));
		        titlePhoto.setImage(image);

			questionInput.setText(QuestionToEdit.getQuestion());
			answ1.setText(QuestionToEdit.getAnswers().get(0).getContent());
			checkans1.setSelected(QuestionToEdit.getAnswers().get(0).isCorrect());
			answ2.setText(QuestionToEdit.getAnswers().get(1).getContent());
			checkans2.setSelected(QuestionToEdit.getAnswers().get(1).isCorrect());
			ans3.setText(QuestionToEdit.getAnswers().get(2).getContent());
			checkans3.setSelected(QuestionToEdit.getAnswers().get(2).isCorrect());
			ans4.setText(QuestionToEdit.getAnswers().get(3).getContent());
			checkans4.setSelected(QuestionToEdit.getAnswers().get(3).isCorrect());
			levelCombo.getSelectionModel().select(QuestionToEdit.getLevel());
			}			

	}
	

}