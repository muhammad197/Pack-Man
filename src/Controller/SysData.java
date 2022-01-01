package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Model.Answer;
import Model.Game;
import Model.Player;
import Model.Question;
import Utils.Level;

public class SysData {
	
	public static String CurrentPlayer;
	public static String CurrentPlayer2;
	private static SysData instance;
	public static int questionID;
	public ArrayList<Player> playersGames;
	public ArrayList<Question> questions;
	
	/**
	 * 
	 * @return sysDATA instance
	 */
	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 * 
	 * @return questionID
	 */
	public int getQuestionID() {
		return questionID;
	}

	/**
	 * set questionID
	 */
	public void setQuestionID(int questionID) {
		SysData.questionID = questionID;
	}

	/**
	 * 
	 * @return playersGames
	 */
	public ArrayList<Player> getPlayersGames() {
		return playersGames;
	}

	/**
	 * 
	 * @param players
	 */
	public void setPlayersGames(ArrayList<Player> players) {
		this.playersGames = players;
	}
	
	/**
	 * 
	 * @return questions
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	/**
	 * 
	 * @param questions
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	    /**
	     * adds a new player
	     * @param player
	     * @return true if success, false else
	     */
		public boolean addPlayer(Player player) {
			 for (Player P : playersGames) {
				if (P.equals(player)) {
					System.out.println("This Player Already Exists");
					return false;
				}
			 }

			playersGames.add(player);
			return true;
			
		}
		

		/**
		 * load Questions from JSON file
		 * @return
		 */
		 public ArrayList<Question> loadQuestions() {
				readQuestionFromJson();
				return questions;
			}
		 
		 
		 /**
		  * remove question and then refresh the JSON file
		  * @param q
		  * @return
		  */
		 public boolean removeQuestion(Question q) {
				if (q != null) {
					questions.remove(questions.indexOf(q));
					AddQuestionToJSON();
					readQuestionFromJson();
					return true;
				}
				return false;
			}
		 
		 
		 /**
		  * edit a question by replacing the old question with the new one
		  * @param old
		  * @param newq
		  * @return
		  */
		 public boolean editQuestion(Question old, Question newq) {
				if (old != null && newq != null) {
					if (removeQuestion(old))
						if (addQueastion(newq))
							return true;
				}
				return false;
			}

		 
		 
		 /**
		  * read questions from json
		  */
		 void readQuestionFromJson() {
			 	questions = new ArrayList<Question>();
				try {
					if (questions.isEmpty())
						for (int i = 0; i < questions.size(); i++) {
							questions.remove(i);
						}
					int questionID = 1;
					Object obj = new JSONParser().parse(new FileReader("Questions.json"));
					JSONObject jo = (JSONObject) obj;
					JSONArray arr = (JSONArray) jo.get("questions");

					for (Object questionObj : arr) {
						JSONObject jsonQObjt = (JSONObject) questionObj;
						String question = (String) jsonQObjt.get("question");
						int true_answer = Integer.parseInt((String) jsonQObjt.get("correct_ans"));
						JSONArray AnswersObjects = (JSONArray) jsonQObjt.get("answers");
						ArrayList<Answer> answersList = new ArrayList<Answer>();
						Iterator<?> itr = AnswersObjects.iterator();
						int i = 1;
						while (itr.hasNext()) {
							String content = itr.next().toString();
							if (i == true_answer) {
								Answer answer = new Answer(i, content, true);
								answersList.add(answer);
							} else {
								Answer answer = new Answer(i, content, false);
								answersList.add(answer);
							}
							i++;
						}
						Level level = Level.returnDifficulty(Integer.parseInt((String)jsonQObjt.get("level")));
						Question q = new Question(question, questionID, answersList, true_answer, level);
						questionID++;
						questions.add(q);
						SysData.questionID++;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();

				}
		}
		
		

		/**
		 * add a new question
		 * @param q
		 * @return
		 */
		 public boolean addQueastion(Question q) {

			 for (Question question : questions) {

					if (question.equals(q)) {
						System.out.println("This Question Already Exists ");
						return false;
					}
					questions.add(q);
					setQuestionID(questionID+1);
					try {

						AddQuestionToJSON();

						return true;
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
			 }
			 return false;
		 }
		 
		 private void AddQuestionToJSON() {
			 try {

					JSONObject jsonObject = new JSONObject();
					JSONArray jsonArray = new JSONArray();

					for (Question q : questions) {
						
						Map map = new LinkedHashMap(5);
						map.put("question", q.getQuestion());
						ArrayList<String> answerscontent= new ArrayList<>();
						ArrayList<Answer> questionAnswers= q.getAnswers();
						for (Answer a : questionAnswers){
							answerscontent.add(a.getContent());
						}
						JSONArray jsonArrayAnswers = new JSONArray(answerscontent);
						map.put("answers", jsonArrayAnswers);
						map.put("correct_ans", "" + q.getTrueAnswer());
						map.put("level", "" + q.getLevel().getNum());
						map.put("team", q.getTeam());
						jsonArray.add(map);
					}
					jsonObject.put("questions", jsonArray);
					PrintWriter pw = new PrintWriter("Questions.json");
					pw.write(jsonObject.toJSONString());
					pw.flush();
					pw.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();

				}
			
		}
		  	
	 	/**
	 	 * read history from JSON File
	 	 */
		public void readHistoryJSON() {
			playersGames = new ArrayList<Player>();
			try {
				if (playersGames.isEmpty())
					for (int i = 0; i < playersGames.size(); i++) {
						playersGames.remove(i);
					}

				Object historyObj = new JSONParser().parse(new FileReader("history.json"));
				JSONObject JsonObject = (JSONObject) historyObj;
				JSONArray JsonGamesArray = (JSONArray) JsonObject.get("games");
				 
				for (Object Obj : JsonGamesArray) {
					JSONObject jsonObj = (JSONObject) Obj;
					String playername = (String) jsonObj.get("player");
					int highscore = Integer.parseInt((String) jsonObj.get("highscore"));
					SimpleDateFormat formatter =new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
					Date date = formatter.parse((String) jsonObj.get("date"));
					Player p = new Player(playername, highscore,date);
					playersGames.add(p);
				}
				
				// sort the games due to high scores
				Collections.sort(playersGames, new Comparator<Player>() {
					@Override
					public int compare(Player p1, Player p2) {
						if(p1.getGameHighScore() < p2.getGameHighScore())
							return 1;
						else if (p1.getGameHighScore() == p2.getGameHighScore())
							return 0;
						else return -1;
					}
				});
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * write history to json file
		 */
		public void writeHistoryJSON() {
			try {
				JSONObject JsonObject = new JSONObject();
				JSONArray JsonArray = new JSONArray();

				for (Player p : playersGames) {
					Map map = new LinkedHashMap(3);
					map.put("player", p.getNickname());
					map.put("highscore", "" + p.getGameHighScore());
					Date date = p.getGameHighScoreDate();
					map.put("date", date.toString());
					JsonArray.add(map);
				}
				JsonObject.put("games", JsonArray);
				PrintWriter pw = new PrintWriter("history.json");
				pw.write(JsonObject.toJSONString());
				pw.flush();
				pw.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		
		/**
		 * get games history
		 * @return
		 */
		public ArrayList<Player> getGamesHistory() {
			readHistoryJSON();
			return playersGames;
		}
		
		/**
		 * add game to history
		 * @param player
		 * @return
		 */
		public boolean addGameHistory(Player player) {
			if (player != null) {
				playersGames.add(player);
				writeHistoryJSON();
				readHistoryJSON();
			}
			return false;
		}
		/**
		 * delete game from history
		 */
		public void deleteGameHistory() {
			for (int i = playersGames.size(); i > 0; i--)
				playersGames.remove(i - 1);
			writeHistoryJSON();
		}
	
		 /**
		  * get the first three winners. the array "players" is sorted due highscores.
		  * @return
		  */
		 public ArrayList<Player> First3Winners(){
			 ArrayList<Player> PlayersToReturn= new ArrayList<>();
			 for(int i=0;i <3;i++)
				 PlayersToReturn.add(playersGames.get(i));
			 return PlayersToReturn;
		 }

		
		 /**
		  * pick a random question to show it on board
		  * @param level
		  * @return
		  */
		 public Question randomQuestion(Level level){
			 ArrayList<Question> SameLevelQues=new ArrayList<>();
			 for(Question q: questions)
				 if(q.getLevel()==level)
					 SameLevelQues.add(q);
					 
			 int minimum= 0;
			 int maximum= SameLevelQues.size();
			 
			 int randomNum = minimum + (int)(Math.random() * maximum);
			 return SameLevelQues.get(randomNum);
		 }
}