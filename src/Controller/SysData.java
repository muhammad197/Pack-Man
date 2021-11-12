package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	public ArrayList<Game> games;
	
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
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
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
		//load Questions from json file
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
					int k = 1;
					Object obj = new JSONParser().parse(new FileReader("Questions.json"));
					JSONObject jo = (JSONObject) obj;
					JSONArray arr = (JSONArray) jo.get("questions");

					for (Object questionObj : arr) {
						JSONObject jsonQObjt = (JSONObject) questionObj;
						String context = (String) jsonQObjt.get("question");
						int correct_ans = Integer.parseInt((String) jsonQObjt.get("correct_ans"));
						JSONArray answersarr = (JSONArray) jsonQObjt.get("answers");
						ArrayList<Answer> arrlista = new ArrayList<Answer>();
						Iterator<?> itr = answersarr.iterator();
						int i = 1;
						while (itr.hasNext()) {
							String content = itr.next().toString();
							if (i == correct_ans) {
								Answer an = new Answer(i, content, true);
								arrlista.add(an);
							} else {
								Answer an = new Answer(i, content, false);
								arrlista.add(an);
							}
							i++;
						}
						Level difficulty = Level.returnDifficulty(Integer.parseInt((String)jsonQObjt.get("level")));
			

						Question q = new Question(context, k, arrlista, correct_ans, difficulty);
						k++;
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
		 
		@SuppressWarnings({ "deprecation", "unchecked" })
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
