package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controller.SysData;
import Model.Answer;
import Model.Question;
import Utils.Level;

class Tedittingquesdiff {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		ArrayList<Answer> A = new ArrayList<Answer>();
		instance.getQuestions();
		Question qold = new Question(6 ,"test", Level.easy, A);
		Question qold1 = new Question(2 ,"for not null array", Level.easy, A);
		Question qnew = new Question(6 ,"test", Level.hard, A);
		ArrayList<Question> que = new ArrayList<Question>();
		que.add(qold);
		que.add(qold1);
		instance.setQuestions(que);
		assertTrue(instance.editQuestion(qold, qnew));
	}

}
