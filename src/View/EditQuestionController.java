package View;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import Controller.SysData;

import Model.Answer;
import Model.Question;
import Utils.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private AnchorPane pane;

    @FXML
    private TextArea questionInput;

    @FXML
    private TextField answ1;

    @FXML
    private TextField answ2;

    @FXML
    private Button saveBtn;
    
    @FXML
    private Button backbutton;
    
    public Question QuestionToEdit;
    
    @FXML
    private Label errorpopup;
    
    

    

    @FXML
    void SaveQuestion(ActionEvent event) {
    	String quest = questionInput.getText().trim();
		String answer1 = answ1.getText().trim();
		String answer2 = answ2.getText().trim();
		String answer3 = ans3.getText().trim();
		String answer4 = ans4.getText().trim();
		boolean answer1Correct = checkans1.isSelected();
		boolean answer2Correct = checkans2.isSelected();
		boolean answer3Correct = checkans3.isSelected();
		boolean answer4Correct = checkans4.isSelected();
		Level level = levelCombo.getSelectionModel().getSelectedItem();
		int correctAnswerID;
		Question newQuestion= QuestionToEdit;
		
		if (!quest.isEmpty()) {
			if (!answer1.isEmpty()) {
				if (!answer2.isEmpty()) {
					if (!answer3.isEmpty()) {
						if (!answer4.isEmpty()) {
							if (answer1Correct || answer2Correct || answer3Correct || answer4Correct) {
								if (level != null) {
                                     if (answer1Correct) {
                                    	 correctAnswerID=1;
                                     }
                                     else if (answer2Correct) {
                                    	 correctAnswerID=2;
                                     }
                                     else if(answer3Correct){
                                    	 correctAnswerID=3;
                                     }
                                     else correctAnswerID=4;
                                    
                                    
									//TODO
         							ArrayList<Answer> answers = new ArrayList<>(4);
									answers.add(new Answer( 1,answer1, answer1Correct));
									answers.add(new Answer( 2,answer2, answer2Correct));
									answers.add(new Answer( 3,answer3, answer3Correct));
									answers.add(new Answer( 4,answer4, answer4Correct));
									
									
									if (QuestionToEdit != null) {
										
										newQuestion.setAnswers(answers);
										newQuestion.setLevel(level);
										newQuestion.setQuestion(quest);// update question
										SysData.getInstance().editQuestion(QuestionToEdit, newQuestion);
										errorpopup.setText("Question updated successfully");
										
									}
									else { // new question
										newQuestion= new Question(quest,SysData.getInstance().getQuestionID(),answers, correctAnswerID, level) ;
										if(	SysData.getInstance().addQueastion(newQuestion))
											errorpopup.setText("Question added successfully.");
										else System.out.println("hh");
									}

								} else
									errorpopup.setText("Please select a difficulty level");
							} else
								errorpopup.setText("Please select the correct answer");
						} else
							errorpopup.setText("Please enter answer No.4");
					} else
						errorpopup.setText("Please enter answer No.3");							
				} else
					errorpopup.setText("Please enter answer No.2");
			} else
				errorpopup.setText("Please enter answer No.1");
		} else
			errorpopup.setText("Please enter a question");
	}


    
    protected void closeWindow() {
		((Stage) pane.getScene().getWindow()).close();
	}
    
   
    @FXML
	private void BackClicked(ActionEvent event) {
		closeWindow();
		ViewLogic.questionsManagmentWindow();
		//TODO
	}
    
	public EditQuestionController(Question questionToEdit) {
		super();
		QuestionToEdit = questionToEdit;
	}




	public EditQuestionController() {
	}


	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		levelCombo.getItems().setAll(Level.values());
			if(QuestionToEdit == null)
			{
		        Image image = new Image(getClass().getResourceAsStream("../resources/addQt.png"));
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
				Image image = new Image(getClass().getResourceAsStream("../resources/editQ.png"));
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