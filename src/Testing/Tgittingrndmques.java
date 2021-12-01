package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controller.SysData;
import Model.Question;


class Tgittingrndmques {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		instance.loadQuestions();
		ArrayList<Question> Qtest = new ArrayList<Question>();
		Qtest.add(instance.randomQuestion());
		assertFalse(Qtest.isEmpty());
		
	}

}
