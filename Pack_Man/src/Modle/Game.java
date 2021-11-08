package Modle;

import java.util.Objects;

public class Game {
	public int id;
	public int live;
	public int speed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLive() {
		return live;
	}
	public void setLive(int live) {
		this.live = live;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Game(int id, int live, int speed) {
		super();
		this.id = id;
		this.live = live;
		this.speed = speed;
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
