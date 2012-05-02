package azl.quizx.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import azl.quizx.domain.Category;
import azl.quizx.dto.WebCategoryDTO;
import azl.quizx.util.Constants;

@Service("categoryService")
@Transactional
public class CategoryService {
	private static Logger logger = Logger.getLogger(CategoryService.class);
	
	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${webapp.filepath}")
    private String webAppFilePath;

	private String getImageDir(){
		//file name including absolute path
		StringBuffer imageDir = new StringBuffer(webAppFilePath);
		imageDir.append(Constants.CATEGORY_IMG_DIR);
		return imageDir.toString();
	}
	
	private void createCategoryImageFile(WebCategoryDTO dto, String imageFileName) throws FileNotFoundException, IOException {
		//file name including absolute path
		String imageFile = getImageDir() + imageFileName;
		
		OutputStream out = new FileOutputStream(imageFile);
		out.write(dto.getUploadImage().getBytes());
		out.flush();
		out.close();
	}
	
	private void deleteImageFile(Category category){
		String imageFile = getImageDir()+ category.getImageFileName();
		
		File file = new File(imageFile);
		file.delete();	
	}
	
	//category name is converted to lower case so the name is case insensitive unique i.e.
	//you can not insert a category name ="Cat" when you already got a name="cat".
	public void persist(WebCategoryDTO dto) throws FileNotFoundException, IOException {
		//to avoid the file name overwrite each other.
		String imageFileName = dto.getLowcaseName()+"_"+dto.getImageFileName();
		createCategoryImageFile(dto, imageFileName);

		Category category = new Category();		
		category.setName(dto.getLowcaseName());
		category.setImageFileName(imageFileName);
		entityManager.persist(category);
		
		//create a corresponding categoryVersion for a newly created category.
		categoryVersionService.persist(category.getId(), 1);
	}

	@SuppressWarnings("unchecked")
	public List<WebCategoryDTO> getCategoryDtoList() {
		logger.debug("Retrieving all category DTO");	
		
		Query query = entityManager.createQuery("from Category");		
		//query.setHint( "org.hibernate.cacheable", true); 
		List<Category> categorys = query.getResultList();
		List<WebCategoryDTO> categoryDtoList = new ArrayList<WebCategoryDTO>();
		
		for (Category c : categorys) {
			WebCategoryDTO dto = new WebCategoryDTO();
			dto.setId(c.getId());
			dto.setName(c.getName());
			dto.setImageUrl(Constants.CATEGORY_IMG_DIR + c.getImageFileName());
			categoryDtoList.add(dto);
		}
		
		return categoryDtoList;		
	}

	public void removeCategory(long id){
		Category category = getCategory(id);
		deleteImageFile(category);
		entityManager.remove(category);
	}	

	public void editCategory(WebCategoryDTO dto)throws FileNotFoundException, IOException{
		Category category = getCategory(dto.getId());
		
		/**
		 * image file name after the Edit i.e. one of the following 3 cases:
		 * 1. "newCategoryName_newUploadedFileName" when both categoryName and upload image changes
		 * 2. "newCategoryName_oldUploadedFileName" when only categoryName changes
		 * 3. "oldCategoryName_newUploadedFileName" when only upload image changes
		 */
		String imageFileName;
		
		if (!dto.getUploadImage().isEmpty()){
			//this covers situation 1 and 3
			deleteImageFile(category);
			
			imageFileName = dto.getLowcaseName()+"_"+dto.getImageFileName();
			createCategoryImageFile(dto, imageFileName);
		} else {
			//this covers situation 2
			String oldFileName = category.getImageFileName();
			int pos = oldFileName.indexOf("_"); 
			String oldUploadedFileName = oldFileName.substring(pos);//include "_"
			
			imageFileName = dto.getLowcaseName()+oldUploadedFileName;
			File oldFile = new File(getImageDir() + oldFileName);
			oldFile.renameTo(new File(getImageDir() + imageFileName));
		}

		category.setImageFileName(imageFileName);
		category.setName(dto.getLowcaseName());
		entityManager.merge(category);
	}
	
	public WebCategoryDTO getCategoryDTO(long categoryId) {
		Category category = getCategory(categoryId);
		WebCategoryDTO dto = new WebCategoryDTO(); 
		BeanUtils.copyProperties(category, dto);
		return dto;
	}
	
	Category getCategory(long id){
		return (Category) entityManager.createQuery("FROM Category c WHERE c.id = :id").
				setParameter("id", id).getSingleResult();		
	}
	
	Category getCategoryBy(String categoryName){
		try {
			//category name is always lower case.
			return (Category) entityManager.createQuery("FROM Category c WHERE c.name = :name").
				setParameter("name", categoryName.trim().toLowerCase()).getSingleResult();
		} catch (NoResultException ex){
			return null;
		}
	}	
	
	//relative url path
	public String getCategoryImageUrl(long id){
		Category category = getCategory(id);
		return Constants.CATEGORY_IMG_DIR + category.getImageFileName();
	}
}