package azl.quizx.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ValidationException;

import azl.quizx.domain.Category;
import azl.quizx.domain.CategoryVersion;
import azl.quizx.dto.WebCategoryVersionDTO;

@Service("categoryVersionService")
@Transactional
public class CategoryVersionService {
	private static Logger logger = Logger.getLogger(CategoryVersionService.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource(name="categoryService")
	private CategoryService categoryService;

	/**
	 * Set the "newestCategoryVersion" and "newestQuizVersion". 
	 */
	public void setNewestVersions(WebCategoryVersionDTO dto){
		long  categoryId = dto.getCategoryId();
		Query query = 
				entityManager.createNativeQuery("select max(categoryVersion) from categoryVersion "+
						"where category_id=" + categoryId);
		Object categoryMaxVersion = query.getSingleResult();
		if (categoryMaxVersion != null){
			dto.setNewestCategoryVersion((Integer)categoryMaxVersion);
		} else {
			dto.setNewestCategoryVersion(0);
		}
		
		query = entityManager.createNativeQuery("select max(categoryVersion) from quiz where category_id="
				+ categoryId);
		Object quizMaxVersion = query.getSingleResult();
		if (quizMaxVersion != null){
			dto.setNewestQuizVersion((Integer)quizMaxVersion);
		} else {
			dto.setNewestQuizVersion(0);
		}
	}
	
	public void persist(long categoryId, int version){
		if (version < 1){
			throw new ValidationException("The category version should not be less than 1.");
		}
		Category category = categoryService.getCategory(categoryId);
		
		CategoryVersion categoryVersion = new CategoryVersion();
		categoryVersion.setCategory(category);
		categoryVersion.setCategoryVersion(version);
		entityManager.persist(categoryVersion);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getVersionsFor(long categoryId){
		if (categoryId < 1){
			//categoryId always starts from 1.
			return new ArrayList<Integer>();
		}
		Query query = 
			entityManager.createNativeQuery("select categoryVersion from categoryVersion "+
				"where category_id=" + categoryId);
		return query.getResultList();
	}

	public int getNewestVersionFor(long categoryId){
		Query query = 
				entityManager.createNativeQuery("select max(categoryVersion) from categoryVersion "+
						"where category_id=" + categoryId);
		return (Integer)query.getSingleResult();
	}
}