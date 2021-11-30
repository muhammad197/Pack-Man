package Model;

import java.util.Objects;

import Utils.GameState;

public class Game {
	public int id;
	public int live;
	public int speed;
	public int score;
	public GameState gameState;
	
	
	/**
	 * @param id
	 * @param live
	 * @param speed
	 * @param gameState
	 */
	public Game(int id, int live, int speed, GameState gameState, int score) {
		super();
		this.id = id;
		this.live = live;
		this.speed = speed;
		this.gameState = gameState;
		this.score=score;
	}
	
	
	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the live
	 */
	public int getLive() {
		return live;
	}


	/**
	 * @param live the live to set
	 */
	public void setLive(int live) {
		this.live = live;
	}


	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}


	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}


	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}


	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, live, speed);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return id == other.id && live == other.live && speed == other.speed;
	}
	
	 //opens the game
	public void openGame() {
		
	}
	
	//starts the game
	public void startGame() {
		
	}
	//pause the game
	public void pauseGame() {
		
	}
	//finish game
	 public void finishGame() {
		 
	 }
	 //level up
	 public void levelUp() {
		 
	 }
	 //updates the number of lives
	 public void updateLive() {
		 
	 }
     //update score
	 public void eatedPoint(){
		 
	 }
	 //check points
	 public void checkPoint(){
		 
	 }
	 
	    //pick random question
		 public void pickQuestion() {
			 
		 }
		 //update score
		 public void updateScore() {
			 
		 }
		 //make the ghosts stop for a while
		 public void pauseGhost() {
			 
		 }
	     //makes the ghosts move again
		 public void ghostPlay(){
			 
		 }
		 //update the passage ways
		 public void updatePassageWay(){
			 
		 }


}
