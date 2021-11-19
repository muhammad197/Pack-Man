package Model;
import java.util.ArrayList;
import java.util.Objects;

import Controller.SysData;
import Model.Ghost;

public class Board {
	
	private static Board instanceBoard;
	private String wallspassageWay;
	private ArrayList<Ghost> ghosts;
	private PeckPoints peckPoints; 
	private ArrayList<PeckPoints> bombPoints; 
	private ArrayList<PeckPoints> questions; 
	private Player player;
	private PackMan packMan;
	
	public static Board getInstance() {
		if (instanceBoard == null)
			instanceBoard = new Board();
		return instanceBoard;
	}
	/**
	 * @param matrixBoard: how the board will like, the numbers: 
	 * 0- are the peck points
	 * 1- are the walls
	 * 2- are the bomb points
	 * 3- will be the questions ( we will added the number 3 randomly )
	 * 4- the packMn
	 * 5- blue ghost
	 * 6- red ghost
	 * 7- pink ghost
	 * 8- orange ghost
	 */
	public static int[][] matrixBoard_level1= 
		                                 {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
										 {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1},
			                             {1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,1,1,0,0,1,0,0,1,1,0,1,0,1,0,1},
			                             {1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
			                             {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
			                             {1,0,0,1,1,1,0,1,1,5,6,7,1,1,0,1,1,1,0,0,1},
			                             {1,1,0,0,0,0,0,1,1,0,8,0,1,1,0,0,0,0,0,1,1},
			                             {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1},
			                             {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1},
			                             {1,1,1,1,0,1,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1},
			                             {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
			                             {1,0,0,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,0,0,1},
			                             {1,1,1,0,1,0,0,0,1,0,1,0,1,0,3,0,1,0,1,1,1},
			                             {1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
			                             {1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,3,1},
			                             {1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1},
			                             {1,2,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,2,1},
			                             {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			                            };

	/**
	 * @param matrixBoard: how the board will like, the numbers: 
	 * 0- are the peck points
	 * 1- are the walls
	 * 2- are the bomb points
	 * 3- will be the questions ( we will added the number 3 randomly )
	 * 4- the packMn
	 * 5- blue ghost
	 * 6- red ghost
	 * 7- pink ghost
	 * 8- orange ghost
	 * 9- transport
	 */
	public static int[][] matrixBoard_level2= 
		                                 {{1,1,1,9,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
										 {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1},
			                             {1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,1},
			                             {1,0,1,0,1,0,1,1,0,0,1,0,0,1,1,0,1,0,1,0,1},
			                             {1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
			                             {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
			                             {1,0,0,1,1,1,0,1,1,5,6,7,1,1,0,1,1,1,0,0,1},
			                             {1,1,0,0,0,0,0,1,1,0,8,0,1,1,0,0,0,0,0,1,1},
			                             {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1},
			                             {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,9},
			                             {1,1,1,1,0,1,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1},
			                             {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
			                             {1,0,0,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,0,0,1},
			                             {1,1,1,0,1,0,0,0,1,0,1,0,1,0,3,0,1,0,1,1,1},
			                             {1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
			                             {1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,3,1},
			                             {1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1},
			                             {1,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,1},
			                             {1,1,1,1,1,1,1,1,1,1,1,1,1,9,1,1,1,1,1,1,1}
			                            };
	
	
}
