package azl.quizx.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * For web admin purpose, this DTO can not be used to generate web service content since
 * the "CommonsMultipartFile" field causes troubles. 
 */
public class WebQuizDTO extends QuizDTO{
	private boolean insertSuccessful;
	private CommonsMultipartFile bulkQuiz;

	
	//side effect when using "categoryId" as String
	public long getCategoryID(){
		return Long.parseLong(this.getCategoryId());
	}

	public CommonsMultipartFile getBulkQuiz() {
		return bulkQuiz;
	}

	public void setBulkQuiz(CommonsMultipartFile bulkQuiz) {
		this.bulkQuiz = bulkQuiz;
	}

	public boolean isInsertSuccessful() {
		return insertSuccessful;
	}

	public void setInsertSuccessful(boolean insertSuccessful) {
		this.insertSuccessful = insertSuccessful;
	}	

	public void reset(){
		super.reset();
		bulkQuiz = null;
		insertSuccessful = false;
	}
	
	public String insertSuccessfulMsg(){
		StringBuffer buf = new StringBuffer();
		buf.append("Category Name  : <b>" + getCategoryName() + "</b><br/>"); 
		buf.append("Question       : <b>" + getQuestion() + "</b><br/>");
		buf.append("Correct Answer : <span style=\"color:red;\">" + getCorrectAnswer() + "</span><br/>");
		buf.append("Answer 2       : " + getAnswer2() + "<br/>");
		buf.append("Answer 3       : " + getAnswer3() + "<br/>");
		buf.append("Answer 4       : " + getAnswer4() + "<br/>");
		buf.append("Status       : " + getStatus() + "<br/>");
		
		return buf.toString();								
	}
}