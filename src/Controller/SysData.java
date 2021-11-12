package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Model.Answer;
import Model.Game;
import Model.Player;
import Model.Question;
import Utils.Level;

public class SysData {
	
	private static SysData instance;
	public ArrayList<Player> players;
	public ArrayList<Question> questions;
	
	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}
	
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
	
	    //adds a new player
		public boolean addPlayer(Player player) {
			 for (Player P : players) {
				if (P.equals(player)) {
					System.out.println("This Player Already Exists");
					return false;
				}
			 }

			players.add(player);
			return true;
			
		}
		

		//load Questions from JSON file
		 public ArrayList<Question> loadQuestions() {
				readQuestionFromJson();
				return questions;
			}
		 
		 
		 // remove question and then refresh the JSON file
		 public boolean removeQuestion(Question q) {
				if (q != null) {
					questions.remove(questions.indexOf(q));
					AddQuestionToJSON();
					readQuestionFromJson();
					return true;
				}
				return false;
			}
		 
		 // edit a question by replacing the old question with the new one
		 public boolean editQuestion(Question old, Question newq) {
				if (old != null && newq != null) {
					if (removeQuestion(old))
						if (addQueastion(newq))
							return true;
				}
				return false;
			}

		 
		 
		 //read questions from json
		 private void readQuestionFromJson() {
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
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();

				}
		}
		
		 //add a new question
		 public boolean addQueastion(Question q) {

			 for (Question question : questions) {

					if (question.equals(q)) {
						System.out.println("This Question Already Exists");
						return false;
					}

					questions.add(q);
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
						
						Map m = new LinkedHashMap(5);
						m.put("question", q.getQuestion());
						ArrayList<String> answerscontent= new ArrayList<>();
						ArrayList<Answer> questionAnswers= q.getAnswers();
						for (Answer a : questionAnswers){
							answerscontent.add(a.getContent());
						}
						JSONArray jsonArrayAnswers = new JSONArray(answerscontent);
						m.put("answers", jsonArrayAnswers);
						m.put("correct_ans", "" + q.getTrueAnswer());
						m.put("level", "" + q.getLevel().getNum());
						m.put("team", q.getTeam());
						jsonArray.add(m);
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
		
	 	//read history from JSON File
		public void readHistoryJSON() {
			players = new ArrayList<Player>();
			try {
				if (players.isEmpty())
					for (int i = 0; i < players.size(); i++) {
						players.remove(i);
					}

				Object historyObj = new JSONParser().parse(new FileReader("history.json"));
				JSONObject JsonObject = (JSONObject) historyObj;
				JSONArray JsonGamesArray = (JSONArray) JsonObject.get("games");
				
				for (Object Obj : JsonGamesArray) {
					JSONObject jsonObj = (JSONObject) Obj;
					String playername = (String) jsonObj.get("player");
					int highscore = Integer.parseInt((String) jsonObj.get("highscore"));
					Player p = new Player(playername, highscore);
					players.add(p);
				}
				
				// sort the games due to high scores
				Collections.sort(players, new Comparator<Player>() {
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

		//write history to json file
		public void writeHistoryJSON() {
			try {
				JSONObject JsonObject = new JSONObject();
				JSONArray JsonArray = new JSONArray();

				for (Player p : players) {
					Map m = new LinkedHashMap(2);
					m.put("player", p.getNickname());
					m.put("highscore", "" + p.getGameHighScore());
					JsonArray.add(m);
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
	
		

		
		 //pick a random question to show it on board
		 public Question randomQuestion(){
			 int minimum= 0;
			 int maximum= questions.size();
			 int randomNum = minimum + (int)(Math.random() * maximum);
			 return questions.get(randomNum);
		 }
}
