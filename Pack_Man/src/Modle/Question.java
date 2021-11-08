package Modle;

import java.util.Objects;

public class Question {
	public String question;
	public int id;
	public Color color;
	public int TrueAnswer;
	public int Answer2;
	public int Answer3;
	public int Answer4;
	public Level level;
	public Question(String question, int id, Color color, int trueAnswer, int answer2, int answer3, int answer4,
			Level level) {
		super();
		this.question = question;
		this.id = id;
		this.color = color;
		TrueAnswer = trueAnswer;
		Answer2 = answer2;
		Answer3 = answer3;
		Answer4 = answer4;
		this.level = level;
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
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getTrueAnswer() {
		return TrueAnswer;
	}
	public void setTrueAnswer(int trueAnswer) {
		TrueAnswer = trueAnswer;
	}
	public int getAnswer2() {
		return Answer2;
	}
	public void setAnswer2(int answer2) {
		Answer2 = answer2;
	}
	public int getAnswer3() {
		return Answer3;
	}
	public void setAnswer3(int answer3) {
		Answer3 = answer3;
	}
	public int getAnswer4() {
		return Answer4;
	}
	public void setAnswer4(int answer4) {
		Answer4 = answer4;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Answer2, Answer3, Answer4, TrueAnswer, color, id, level, question);
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
		return Answer2 == other.Answer2 && Answer3 == other.Answer3 && Answer4 == other.Answer4
				&& TrueAnswer == other.TrueAnswer && color == other.color && id == other.id && level == other.level
				&& Objects.equals(question, other.question);
	}
	
	 //mix the answers
	 public void randomAnswers(){
		 
	 }
	
}
