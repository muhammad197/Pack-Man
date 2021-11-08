package Controller;

import java.util.ArrayList;

import Modle.Game;
import Modle.Player;
import Modle.Question;

public class SysData {
	public ArrayList<Player> players;
	public ArrayList<Question> questions;
	public ArrayList<Game> games;
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	public SysData(ArrayList<Player> players, ArrayList<Question> questions, ArrayList<Game> games) {
		super();
		this.players = players;
		this.questions = questions;
		this.games = games;
	}
	
	    //adds a new player
		public void addPlayer(Player player) {
			
		}
		
		//starts the game
		public void startGame() {
			
		}
		//load the map
		public void loadData() {
			
		}
		//load Q
		 public void loadQuestions() {
			 
		 }
		 //add a new q
		 public void addQueastion() {
			 
		 }
		 //remove q
		 public void removeQuestion() {
			 
		 }
	     //update Q
		 public void updateQuestion(){
			 
		 }
		 //pick a random q
		 public void randomQuestion(){
			 
		 }
}
