package azl.quizx.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import javax.validation.ValidationException;

import azl.quizx.domain.Quiz;

/**
 * This class process bulk quiz data in text file with a fixed format (case insensitive) i.e.
 * line1: categoryName	xxx	
 * line2: question	correctAnswer	answer2	answer3	answer4
 * line3: question/answers
 * .....
 * 
 * The file is "tab" delimited, and the "CategoryVersion" must be an integer equals
 * or greated than 0.
 * 
 * If there are validation errors at header line1 and line2, then the class won't validate
 * the rest quiz lines, otherwise the class will validate all quiz lines.
 */
class QuizBulkDataHandler {
	private LineNumberReader reader;
	private StringBuffer     validationErrors;
	private QuizBulkService  bulkService;
	
	QuizBulkDataHandler(InputStream bulkData, QuizBulkService bulkService){
		reader = new LineNumberReader(new InputStreamReader(bulkData));
		validationErrors = new StringBuffer();
		this.bulkService = bulkService;
	}
	
	void process(){
		try{
			validateHeader1(reader.readLine());
			validateHeader2(reader.readLine());
			String aLine = reader.readLine();
			while (aLine != null){
				validateQuiz(aLine, reader.getLineNumber());
				aLine = reader.readLine();
			}
		}catch(IOException ioEx){
			validationErrors.append("Error to read the file on line " + reader.getLineNumber() + " <br/>");
			validationErrors.append("Error: " + ioEx.getMessage());
		}catch(ValidationException vEx){
			validationErrors.append("Validation error on line " + reader.getLineNumber() + " <br/>");
			validationErrors.append("The error is : " + vEx.getMessage() + " <br/>");
		}
	}
	
	private void validateHeader1(String header1) throws ValidationException{
		if (header1 == null){			
			throw new ValidationException("The header line 1 should not be null.");
		}
		StringTokenizer tokenizer = new StringTokenizer(header1, "\t");
		int totalTokens = tokenizer.countTokens();
		if (totalTokens != 2){
			throw new ValidationException("The header line 1 should contain 2 tab delimited "+
					"tokens with format \"categoryName	xxx\"");
		}
		String token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("categoryName")){
			throw new ValidationException("the first token should be \"categoryName\"");
		}
		token = tokenizer.nextToken().trim();
		if (token.length() == 0){
			throw new ValidationException("the second token can not be empty.");
		}
		bulkService.acceptCategoryName(token);
	}

	private void validateHeader2(String header2) throws ValidationException{
		if (header2 == null){			
			throw new ValidationException("The header line 2 should not be null.");
		}
		StringTokenizer tokenizer = new StringTokenizer(header2, "\t");
		int totalTokens = tokenizer.countTokens();
		if (totalTokens != 5){
			throw new ValidationException("The header line 2 should have 5 tab delimited tokens.");
		}
		String token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("question")){
			throw new ValidationException("the first token should be \"question\"");
		}
		token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("correctAnswer")){
			throw new ValidationException("the second token should be \"correctAnswer\"");
		}
		token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("answer2")){
			throw new ValidationException("the first token should be \"answer2\"");
		}
		token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("answer3")){
			throw new ValidationException("the first token should be \"answer3\"");
		}
		token = tokenizer.nextToken().trim();
		if (!token.equalsIgnoreCase("answer4")){
			throw new ValidationException("the first token should be \"answer4\"");
		}
	}
	
	private void validateQuiz(String quizLine, int lineNum){
		StringTokenizer tokenizer = new StringTokenizer(quizLine, "\t");
		int totalTokens = tokenizer.countTokens();
		if (totalTokens != 5){
			validationErrors.append("<em>Line Number " + lineNum + 
					"</em>: a quiz should have 5 tab delimited tokens.<br/>");
			return;
		}

		StringBuffer errorOfThisLine = new StringBuffer();
		String question = tokenizer.nextToken().trim();
		if (question.length() == 0){
			errorOfThisLine.append("\"question\" can not be empty,");
		}
		
		String correctAnswer = tokenizer.nextToken().trim();
		if (correctAnswer.length() == 0){
			errorOfThisLine.append("\"correctAnaswer\" can not be empty,");
		}
		
		String answer2 = tokenizer.nextToken().trim();
		if (answer2.length() == 0){
			errorOfThisLine.append("\"answer2\" can not be empty,");
		}
		
		String answer3 = tokenizer.nextToken().trim();
		if (answer3.length() == 0){
			errorOfThisLine.append("\"answer3\" can not be empty,");
		}
		
		String answer4 = tokenizer.nextToken().trim();
		if (answer4.length() == 0){
			errorOfThisLine.append("\"answer4\" can not be empty");
		}
		
		if (errorOfThisLine.length() > 0) {
			validationErrors.append("<em>Line Number " + lineNum + "</em>: " + errorOfThisLine.toString() + " <br/>");
		}
		
		if (validationErrors.length() == 0){
			//this only happen when no error for the
			//2 header lines and all previous quiz lines.
			Quiz quiz = new Quiz();
			quiz.setQuestion(question);
			quiz.setCorrectAnswer(correctAnswer);
			quiz.setAnswer2(answer2);
			quiz.setAnswer3(answer3);
			quiz.setAnswer4(answer4);
			
			bulkService.acceptQuiz(quiz);
		}
	}
	
	String validationErrors(){
		return validationErrors.toString();
	}
}