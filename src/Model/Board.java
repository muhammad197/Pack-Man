package Model;
import java.util.ArrayList;
import java.util.Objects;

import Model.Ghost;

public class Board {
	private String wallspassageWay;
	private ArrayList<Ghost> ghosts;
	private PeckPoints peckPoints; 
	private ArrayList<PeckPoints> bombPoints; 
	private ArrayList<PeckPoints> questions; 
	private Player player;
	private PackMan packMan;
	/**
	 * @param matrixBoard: how the board will like, the numbers: 
	 * 0- are the peck points
	 * 1- are the walls
	 * 2- are the bomb points
	 * 3- will be the questions ( we will added the number 3 randomly )
	 * 4- the packMn
	 * 5- are the ghosts
	 */
	private static int[][] matrixBoard= {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			                      {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1},
			                      {1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1,0,1},
			                      {1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1},
			                      {1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,1},
			                      {1,0,1,0,1,0,1,1,0,0,1,0,0,1,1,0,1,0,1,0,1},
			                      {1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
			                      {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
			                      {1,0,0,1,1,1,0,1,1,5,5,5,1,1,0,1,1,1,0,0,1},
			                      {1,1,0,0,0,0,0,1,1,0,5,0,1,1,0,0,0,0,0,1,1},
			                      {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1},
			                      {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1},
			                      {1,1,1,1,0,1,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1},
			                      {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
			                      {1,0,0,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,0,0,1},
			                      {1,1,1,0,1,0,0,0,1,0,1,0,1,0,3,0,1,0,1,1,1},
			                      {1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
			                      {1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,3,1},
			                      {1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1},
			                      {1,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,1},
			                      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			                      };
	/**
	 * @param wallspassageWay
	 * @param ghosts
	 * @param peckPoints
	 * @param bombPoints
	 * @param questions
	 * @param player
	 * @param packMan
	 */
	public Board(String wallspassageWay, ArrayList<Ghost> ghosts, PeckPoints peckPoints,
			BombPoints  bombPoints, Question questions, Player player, PackMan packMan) {
		super();
		this.wallspassageWay = wallspassageWay;
		this.ghosts = new ArrayList<Ghost>();
		this.peckPoints = peckPoints;
		this.bombPoints = BombPoints.getBombPoints();
		this.questions = Question.getPointsQuestions() ;
		this.player = player;
		this.packMan = packMan;
	}
/**	
	public static String[][]  creatBoard()
	{
		String[][] board;
		for(int i=0; i<21; i++)
			for(int j=0; j<21; j++)
				if(matrixBoard[i][j]==1)
					
			
	}
	*/
	
}
