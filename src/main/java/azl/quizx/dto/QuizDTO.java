package azl.quizx.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import azl.quizx.domain.Quiz;

/**
 * Purely for generating web service content. 
 */
@XmlRootElement(name="quiz")
public class QuizDTO {
	private long id;
	
	@NotBlank
	private String question;
	@NotBlank
	private String correctAnswer;
	@NotBlank
	private String answer2;
	@NotBlank
	private String answer3;
	@NotBlank
	private String answer4;
	
	@NotBlank
	private String categoryId;//can not use long since this is a dropdown with first element as ""
	
	//@Min(0)
	private int categoryVersion;
	private String categoryName;
	private Quiz.Status status;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}
	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public String getAnswer4() {
		return answer4;
	}
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Quiz.Status getStatus() {
		return status;
	}

	public void setStatus(Quiz.Status status) {
		this.status = status;
	}

	protected void reset(){
		id = 0l;
		question = null;
		correctAnswer = null;
		answer2 = null;
		answer3 = null;
		answer4 = null;
		categoryId = "";
		categoryVersion = 0;
		categoryName = null;
	}
}