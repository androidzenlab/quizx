package azl.quizx.web.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import azl.quizx.dto.WebQuizDTO;

/**
 * This provides extra validation on the top of Hibernate Annotation Validations inside
 * the QuizDTO class.
 */
public class QuizDtoValidator {
	/**
	 * @param quiz
	 * @param modelName  The model name passed from controller to the view e.g. "quiz".
	 * @param result
	 */
	public static void validateUploadQuizFile(WebQuizDTO quiz, String modelName,
			BindingResult result){
		if (quiz.getBulkQuiz().isEmpty()) {
			FieldError quizFileEmptyError = new FieldError(modelName, "bulkQuiz", 
					"upload quiz file can not be empty");
			result.addError(quizFileEmptyError);
		}
	}

	/**
	 * When "categoryVersion = -1", all the quizs for a category will be searched, otherwise
	 * the "categoryVersion" for the category is searched.  
	 */
	/*
	public static void validateSearch(WebQuizSearchDTO quiz, String modelName,
			BindingResult result){
		if (quiz.getCategoryId().isEmpty()) {
			FieldError categoryEmptyError = new FieldError(modelName, "categoryId", 
					"Category can not be empty");
			result.addError(categoryEmptyError);
		}
		
		if (quiz.getCategoryVersion() < -1){
			FieldError categoryVersionError = new FieldError(modelName, "categoryVersion", 
					"Category version for search should not be less than -1");
			result.addError(categoryVersionError);			
		}
	}*/
}