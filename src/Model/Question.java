package Model;

import java.util.ArrayList;
import java.util.Objects;

import Utils.Level;

public class Question {
	public String question;
	public int id;
	public ArrayList<Answer> answers;
	public int TrueAnswer;
	public Level level;
	public String team;

	 public Question(String question, int id, ArrayList<Answer> answers, int trueAnswer, Level level) {
		super();
		this.question = question;
		this.id = id;
		this.answers = answers;
		TrueAnswer = trueAnswer;
		this.level = level;
		this.team = "Panther";
	}



	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public int getTrueAnswer() {
		return TrueAnswer;
	}

	public void setTrueAnswer(int trueAnswer) {
		TrueAnswer = trueAnswer;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	//mix the answers
	 public void randomAnswers(){
		 
	 }
	 
	 
	// check if answer already exists
	public boolean addAnswer(Answer answer, boolean isCorrect) {
		return true;
	}
	
	// delete an answer
	public boolean deleteAnswer(int answerid) {
		for(Answer answerCheck : answers) {
			if(answerCheck.AnswerID == answerid) {
				answers.remove(answers.indexOf(answerCheck));
			return true;
			}
		}
		
		return false;
	}



	@Override
	public int hashCode() {
		return Objects.hash(TrueAnswer, answers, level, question);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return TrueAnswer == other.TrueAnswer && Objects.equals(answers, other.answers) && level == other.level
				&& Objects.equals(question, other.question);
	}



	@Override
	public String toString() {
		return "Question [question=" + question + ", id=" + id + ", answers=" + answers + ", TrueAnswer=" + TrueAnswer
				+ ", level=" + level + ", team=" + team + "]";
	}



	


	
		
}
