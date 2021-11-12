package Model;

import java.util.Objects;

public class Answer {
	public int AnswerID;
	public String content;
	public boolean isCorrect;
	
	public Answer(int answerID, String content, boolean isCorrect) {
		super();
		AnswerID = answerID;
		this.content = content;
		this.isCorrect = isCorrect;
	}
	
	public int getAnswerID() {
		return AnswerID;
	}
	public void setAnswerID(int answerID) {
		AnswerID = answerID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public int hashCode() {
		return Objects.hash(AnswerID, content, isCorrect);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		return AnswerID == other.AnswerID && Objects.equals(content, other.content) && isCorrect == other.isCorrect;
	}
	

}
