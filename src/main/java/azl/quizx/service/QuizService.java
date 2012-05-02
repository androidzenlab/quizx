package azl.quizx.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import azl.quizx.domain.Category;
import azl.quizx.domain.Quiz;
import azl.quizx.dto.QuizDTO;
import azl.quizx.dto.WebQuizDTO;

@Service("quizService")
@Transactional
public class QuizService {
	private static Logger logger = Logger.getLogger(QuizService.class);
	
	@Resource(name="categoryService")
	private CategoryService categoryService;

	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;

	@PersistenceContext
	private EntityManager entityManager;
	

	public String persist(WebQuizDTO dto){
		dto.setStatus(Quiz.Status.NEW);
		
		Quiz quiz = new Quiz();
		setQuizFromDto(dto, quiz);		
		entityManager.persist(quiz);
		logger.info("Saved a new quiz " + quiz.toString());
		return quiz.getCategory().getName();
	}	
	
	public void editQuiz(WebQuizDTO dto){
		Quiz quiz = getQuiz(dto.getId());
		setQuizFromDto(dto, quiz);
		
		//set quiz to UPDATE for each edit and version to 0
		quiz.setStatus(Quiz.Status.UPDATE);
		quiz.setCategoryVersion(0);
		
		entityManager.merge(quiz);
	}

	private void setQuizFromDto(WebQuizDTO dto, Quiz quiz){
		BeanUtils.copyProperties(dto, quiz, new String[]{"id"});
		Category cate = categoryService.getCategory(dto.getCategoryID());
		quiz.setCategory(cate);
	}
	
	@SuppressWarnings("unchecked")
	public List<QuizDTO> getQuizs(long categoryId, int categoryVersion) {
		if (categoryId < 1){
			return new ArrayList<QuizDTO>();
		}
		StringBuffer queryString = new StringBuffer("select q from Quiz q where q.category.id = " + 
				categoryId);
		if (categoryVersion > -1){
			queryString.append(" and q.categoryVersion="+categoryVersion);
		}
		Query query = entityManager.createQuery(queryString.toString());
		List<Quiz> quizs = query.getResultList();
		return getFromQuizs(quizs);
	}

	private List<QuizDTO> getFromQuizs(List<Quiz> quizs){
		List<QuizDTO> quizDtoList = new ArrayList<QuizDTO>();
		
		for (Quiz q : quizs) {
			QuizDTO dto = new QuizDTO();
			BeanUtils.copyProperties(q, dto);
			dto.setCategoryName(q.getCategory().getName());
			dto.setCategoryId(""+q.getCategory().getId());
			quizDtoList.add(dto);
		}
		
		return quizDtoList;		
	}
	
	public void deleteQuiz(long id){
		Quiz quiz = getQuiz(id);

		//set quiz to DISCARD for each "delete" and version to 0, then edit the quiz
		quiz.setStatus(Quiz.Status.DISCARD);
		quiz.setCategoryVersion(0);
		
		entityManager.merge(quiz);
	}
		
	public QuizDTO getQuizDTO(long id) {
		Quiz quiz = getQuiz(id);
		Category category = quiz.getCategory();
		QuizDTO dto = new QuizDTO(); 
		BeanUtils.copyProperties(quiz, dto);
		dto.setCategoryId(""+category.getId());
		dto.setCategoryName(category.getName());
		return dto;
	}
	
	private Quiz getQuiz(long id){
		return (Quiz) entityManager.createQuery("FROM Quiz c WHERE c.id = :id").
				setParameter("id", id).getSingleResult();		
	}
	
	/**
	 * Modify all non-versioned (i.e. categoryVersion == 0) quizs under "categoryId"
	 * to newest category version.
	 */
	public int modifyQuizsVersion(long categoryId){
		int newestVersion = categoryVersionService.getNewestVersionFor(categoryId);
		Query query = entityManager.createNativeQuery("update quiz set categoryVersion ="+
				newestVersion + " where category_id = " + categoryId + 
				" and categoryVersion = 0");
		return query.executeUpdate();
	}
}