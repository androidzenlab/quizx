package azl.quizx.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import azl.quizx.domain.Category;
import azl.quizx.domain.Quiz;

/**
 * default categortVersion = 0 for bulk insert quizs.
 */
@Service("quizBulkService")
@Transactional
public class QuizBulkService {
	private static Logger logger = Logger.getLogger(QuizBulkService.class);
	
	@Resource(name="categoryService")
	private CategoryService categoryService;

	@PersistenceContext
	private EntityManager entityManager;
	
	private QuizBulkDataHandler  dataHandler;
	private String categoryName;
	private List<Quiz> quizs;
	
	/**
	 * When all validation passed, this method will insert all quizs into database and
	 * return an empty string, otherwise return validation errors without any database
	 * changes.
	 * 
	 * @return Validation errors or empty if all validation passed. 
	 */
	public String insertQuizs(InputStream in){
		quizs = new ArrayList<Quiz>();
		categoryName = null;
		//categoryVersion = -1;
		dataHandler = new QuizBulkDataHandler(in, this);
		dataHandler.process();

		String validationErrors = dataHandler.validationErrors();		
		if (validationErrors.length() == 0){
			insertIntoDB();
		}
		
		return validationErrors.toString();
	}

	private void insertIntoDB(){
		Category cate = categoryService.getCategoryBy(categoryName);
		for (Quiz quiz: quizs){
			quiz.setStatus(Quiz.Status.NEW);
			quiz.setCategory(cate);
			quiz.setCategoryVersion(0);
			entityManager.persist(quiz);
			logger.info("Quiz : " + quiz.toString() + " has been inserted.");
		}
	}
	
	void acceptCategoryName(String cateName) throws ValidationException {
		//check if the "categoryName" is already in the db
		Category cate = categoryService.getCategoryBy(cateName);
		if (cate == null){
			throw new ValidationException("The category name <em>\"" + cateName + "\"</em> in the csv "+
					"file does not exist in the db.");
		}

		categoryName = cateName;
	}

	void acceptQuiz(Quiz quiz){
		quizs.add(quiz);
	}
}