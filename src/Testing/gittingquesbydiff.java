package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controller.SysData;
import Model.Question;
import Utils.Level;

class gittingquesbydiff {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		instance.getQuestions();
		Question hardQtest = instance.randomQuestion(Level.hard);
		Question medQtest = instance.randomQuestion(Level.medium);
		Question easyQtest = instance.randomQuestion(Level.easy);
		assertNotNull(hardQtest);
		assertNotNull(medQtest);
		assertNotNull(easyQtest);
	}

}
