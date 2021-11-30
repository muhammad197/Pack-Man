package View;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Controller.SysData;
import Model.Answer;
import Model.Question;
import Utils.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

public class QuestionsManagmentController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Question, Integer> questionid;

    
    


    @FXML
    private TableColumn<Answer, String> iscorrect;

    @FXML
    private TableColumn<Answer, String> AnswersContent;

    @FXML
    private TableColumn<Answer, Integer> answerID;

    @FXML
    private TableView<Question> questionsTable;

    @FXML
    private ImageView deleteIcon;

   

    @FXML
    private TableColumn<Question, Level> questiondiff;

    @FXML
    private Label questionLabel;

    @FXML
    private TableView<Answer> answersTable;

    @FXML
    private ImageView ManageQuestionsTitle;

    @FXML
    private ImageView background;

    @FXML
    private ImageView editIcon;

    @FXML
    private AnchorPane pane;
    
    @FXML
    private Button bckbtn;
    
    

    @FXML
    private TableColumn<Question, String> questionscontent;
    @FXML
    private ImageView addIcon;

	private ArrayList<Question> questions;

	private Question question;
	
	
	
	protected void closeWindow() {
		((Stage) pane.getScene().getWindow()).close();
	}
	
	@FXML
	private void BackClicked(ActionEvent event) {
		closeWindow();
		ViewLogic.mainPageWindow();
		//TODO
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	    // when delete icon is pressed, call the delete question method
	    deleteIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	deleteQuestion();
	            event.consume();
	        }
	   });
	    
	    // when edit icon is pressed, call the edit question method
	    editIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	try {
	            	Question q = questionsTable.getSelectionModel().getSelectedItem();

					editQuestion(q);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            event.consume();
	        }
	   });
	    
	    // when add icon is pressed, call the add question method
	    addIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	try {
					addQuestion();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            event.consume();
	        }
	   });
	    
	    // fill columns with data in questions table 
		questionLabel.setWrapText(true);
		questionid.setCellValueFactory(new PropertyValueFactory<>("id"));
		questionscontent.setCellValueFactory(new PropertyValueFactory<>("question"));
		questiondiff.setCellValueFactory(new PropertyValueFactory<>("level")); 
		// same thing for answers table
		answerID.setCellValueFactory(new PropertyValueFactory<>("AnswerID")); 
		AnswersContent.setCellValueFactory(new PropertyValueFactory<>("content"));
		
		setQuestionsTable();
	}

	// delete a question
    public void deleteQuestion() {
    	Question q = questionsTable.getSelectionModel().getSelectedItem();
		if (q != null)
		{Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Delete Question");
		alert.setHeaderText("Are you sure you want to delete this question?");
		alert.setContentText("''"+q.getQuestion()+"''");
		ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get().getButtonData() == ButtonData.YES) {
			try {
				SysData.getInstance().removeQuestion(q);
				setQuestionsTable();
				setSelectedQuestion();
			} catch (Exception e) {
			}
		}
	}
    
    }
    

    //add new question
    public void addQuestion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        EditQuestionController eq= new EditQuestionController(); 
        fxmlLoader.setController(eq);
        
        URL url = getClass().getResource("EditQuestion.fxml");
       
        fxmlLoader.setLocation(getClass().getResource("EditQuestion.fxml"));
        AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream()); 
        pane.getChildren().clear();
        pane.getChildren().add(page);



    }

    //when edit question is pressed, open the edit question with it's data
    public void editQuestion(Question q) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        EditQuestionController eq= new EditQuestionController(q); 
        fxmlLoader.setController(eq);
        
        URL url = getClass().getResource("EditQuestion.fxml");
        
        fxmlLoader.setLocation(getClass().getResource("EditQuestion.fxml"));
        AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream()); 
        pane.getChildren().clear();
        pane.getChildren().add(page);

     


    }
    // show selected question details
	@FXML
	public void setSelectedQuestion() {
		question = questionsTable.getSelectionModel().getSelectedItem();
		if (question != null) {
			questionLabel.setText(question.getQuestion());
			ArrayList<Answer> answersArray = question.getAnswers();
			ObservableList<Answer> answers = FXCollections.observableArrayList(answersArray);
			answersTable.setItems(answers);
		}
		else {
			questionLabel.setText("");
			answersTable.getItems().clear();
		}
		answersTable.refresh();
	}
	// set question in table
	private void setQuestionsTable() {
		
		questions = SysData.getInstance().getQuestions();
		ObservableList<Question> qs = FXCollections.observableArrayList(questions);
		questionsTable.setItems(qs);
		questionsTable.refresh();
	}
	
	
	


}