package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controller.SysData;
import Model.Answer;
import Model.Question;
import Utils.Level;


class TDeleteques {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		ArrayList<Answer> A = new ArrayList<Answer>();
		Question q = new Question(10 ,"test 2", Level.hard, A);
		ArrayList<Question> que = instance.loadQuestions();
		que.add(q);
		instance.setQuestions(que);
		assertTrue(instance.removeQuestion(q));
	}

}
