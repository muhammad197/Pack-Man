package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Controller.SysData;

class Tdeletehistory {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		instance.getGamesHistory();
		instance.deleteGameHistory();
		assertTrue(instance.getGamesHistory().isEmpty());
	}

}
