package Modle;

import java.util.ArrayList;
import java.util.Objects;

public class Player{
	public String nickname;
	public ArrayList<Game> games;
	public int GameHighScore;
	public String password;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	public int getGameHighScore() {
		return GameHighScore;
	}
	public void setGameHighScore(int gameHighScore) {
		GameHighScore = gameHighScore;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		return Objects.hash(GameHighScore, games, nickname, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return GameHighScore == other.GameHighScore && Objects.equals(games, other.games)
				&& Objects.equals(nickname, other.nickname) && Objects.equals(password, other.password);
	}
	public Player(String nickname, ArrayList<Game> games, int gameHighScore, String password) {
		super();
		this.nickname = nickname;
		this.games = games;
		GameHighScore = gameHighScore;
		this.password = password;
	}
	
	//adds a new game to a specific player
	public void addPlayer(Player player,Game game) {
		
	}

}

