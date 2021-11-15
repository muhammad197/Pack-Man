package Controller;

import java.util.ArrayList;

import Model.Answer;
import Model.Color;
import Model.Player;
import Model.Question;
import Utils.Level;
import View.ViewLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
	/*	SysData.getInstance().loadQuestions();
        Answer a= new Answer(1, "Convert the interface of a class into another interface clients expect. ", true);
        Answer b= new Answer(2, "Convert the interface of a class into another interface clients expect. ", false);
        Answer c= new Answer(3, "Convert the interface of a class into another interface clients expect. ", false);
        Answer d= new Answer(4, "Convert the interface of a class into another interface clients expect. ", false);

        ArrayList<Answer> answers= new ArrayList<>();

        answers.add(a);
        answers.add(b);
        answers.add(c);
        answers.add(d);
        
        Question q= new Question("Which of the following is true about Factory method ?",10,answers, 1, Level.easy);

        SysData.getInstance().addQueastion(q);

        SysData.getInstance().readHistoryJSON(); 
        SysData.getInstance().players.add(new Player("kory", 1000));
        SysData.getInstance().writeHistoryJSON();

        System.out.println(SysData.getInstance().getPlayers());

        

        

        System.out.println(SysData.getInstance().getQuestions());
        System.out.println(SysData.getInstance().randomQuestion());
        */
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		ViewLogic.initUI(); 
	        
	        
		
	}

}
