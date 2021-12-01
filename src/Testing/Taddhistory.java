package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controller.SysData;
import Model.Player;

class Taddhistory {

	@Test
	void test() {
		SysData instance = SysData.getInstance();
		Date dt = new Date(2020, 11, 21);
		Player p =new Player("AC" , 2333 , dt );
		ArrayList<Player> pl = new ArrayList<Player>();
		pl.add(p);
		instance.setPlayersGames(pl);
		assertFalse(instance.addGameHistory(p));
		
	}

}
