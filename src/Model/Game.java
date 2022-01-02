package Model;

import java.util.Objects;

import Utils.GameState;
import Utils.GameStateWomen;
import Utils.Level;

public class Game {
	public int id;
	public String playerName;
	public int live;
	public int score;
	public Level level;
	public GameState gameState;
	public GameStateWomen gameStatewomen;
	
	
	/**
	 * @param id
	 * @param live
	 * @param speed
	 * @param gameState
	 */
	public Game(int id, int live, GameState gameState, int score, Level level, String Playername) {
		super();
		this.id = id;
		this.live = live;
		this.gameState = gameState;
		this.score=score;
		this.level=level;
		this.playerName=Playername;
	}
	public Game(int id, int live, GameStateWomen gameStatewomen, int score, Level level, String Playername) {
		super();
		this.id = id;
		this.live = live;
		this.gameStatewomen = gameStatewomen;
		this.score=score;
		this.level=level;
		this.playerName=Playername;
	}
	
	
	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
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

	public void setGameStateWomen(GameStateWomen gameStatewomen) {
		this.gameStatewomen = gameStatewomen;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, live );
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
