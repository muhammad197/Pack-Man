package Model;
import java.util.ArrayList;
import java.util.Objects;

import Model.Ghost;

public class Board {
	private String wallspassageWay;
	private ArrayList<Ghost> ghosts;
	private ArrayList<GameObject> peckPoints; // update type to location
	private BombPoints bombPoints; // array to update
	private Question questions; // array to update
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
	private int[][] matrixBoard= {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			                      {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1},
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
			                      {1,1,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,1,1},
			                      {1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1},
			                      {1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
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
	public Board(String wallspassageWay, ArrayList<Ghost> ghosts, ArrayList<GameObject> peckPoints,
			BombPoints bombPoints, Question questions, Player player, PackMan packMan) {
		super();
		this.wallspassageWay = wallspassageWay;
		this.ghosts = new ArrayList<Ghost>();
		this.peckPoints = peckPoints;
		this.bombPoints = bombPoints;
		this.questions = questions;
		this.player = player;
		this.packMan = packMan;
	}
	/**
	 * @return the wallspassageWay
	 */
	public String getWallspassageWay() {
		return wallspassageWay;
	}
	/**
	 * @param wallspassageWay the wallspassageWay to set
	 */
	public void setWallspassageWay(String wallspassageWay) {
		this.wallspassageWay = wallspassageWay;
	}
	/**
	 * @return the ghosts
	 */
	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}
	/**
	 * @param ghosts the ghosts to set
	 */
	public void setGhosts(ArrayList<Ghost> ghosts) {
		this.ghosts = ghosts;
	}
	/**
	 * @return the peckPoints
	 */
	public ArrayList<GameObject> getPeckPoints() {
		return peckPoints;
	}
	/**
	 * @param peckPoints the peckPoints to set
	 */
	public void setPeckPoints(ArrayList<GameObject> peckPoints) {
		this.peckPoints = peckPoints;
	}
	/**
	 * @return the bombPoints
	 */
	public BombPoints getBombPoints() {
		return bombPoints;
	}
	/**
	 * @param bombPoints the bombPoints to set
	 */
	public void setBombPoints(BombPoints bombPoints) {
		this.bombPoints = bombPoints;
	}
	/**
	 * @return the questions
	 */
	public Question getQuestions() {
		return questions;
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(Question questions) {
		this.questions = questions;
	}
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * @return the packMan
	 */
	public PackMan getPackMan() {
		return packMan;
	}
	/**
	 * @param packMan the packMan to set
	 */
	public void setPackMan(PackMan packMan) {
		this.packMan = packMan;
	}
	
	

	
	
}
