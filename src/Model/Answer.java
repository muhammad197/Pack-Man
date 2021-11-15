package Model;

import java.util.Objects;

public class Answer {
	public int AnswerID;
	public String content;
	public boolean isCorrect;
	
	

	/**
	 * @param answerID- of the answer
	 * @param content- the text of the answer
	 * @param isCorrect- if the answer is correct then the value is 1
	 */
	public Answer(int answerID, String content, boolean isCorrect) {
		super();
		AnswerID = answerID;
		this.content = content;
		this.isCorrect = isCorrect;
	}

	
	/**
	 * @return the answerID
	 */
	public int getAnswerID() {
		return AnswerID;
	}


	/**
	 * @param answerID the answerID to set
	 */
	public void setAnswerID(int answerID) {
		AnswerID = answerID;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the isCorrect
	 */
	public boolean isCorrect() {
		return isCorrect;
	}


	/**
	 * @param isCorrect the isCorrect to set
	 */
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
