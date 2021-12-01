package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Board;

class Tfillboard {

	@Test
	void test() {
		Board b =new Board();
		assertNotNull(b.getInstance());
	}

}
