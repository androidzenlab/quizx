package azl.quizx.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import azl.quizx.dto.CategoryDTO;
import azl.quizx.dto.CategoryDTOList;
import azl.quizx.dto.DownloadableVersionDTO;
import azl.quizx.dto.DownloadableVersionDTOList;
import azl.quizx.dto.WebCategoryDTO;
import azl.quizx.dto.QuizDTO;
import azl.quizx.dto.QuizDTOList;
import azl.quizx.service.CategoryService;
import azl.quizx.service.CategoryVersionService;
import azl.quizx.service.QuizService;

/**
 * This is the integration points the mobile application interface with.
 * Currently all the urls are not Spring security protected.
 */
@Controller
public class WSController {
	private static Logger logger = Logger.getLogger(WSController.class);

	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="quizService")
	private QuizService quizService;

	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;

	/**
	 * Query all categories except the category with "newestDownloadableVersion = 0".
	 * 
	 * Sample url: http://localhost:8080/quizx/ws/categories
	 */
    @RequestMapping(value = "/categories", method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody CategoryDTOList getCategories() {
    	logger.info("WS: query all categories.");
    	
    	List<CategoryDTO> categoryDtos = new ArrayList<CategoryDTO>();
    			
    	List<WebCategoryDTO> webCategoryDtos = categoryService.getCategoryDtoList();
    	for (WebCategoryDTO webDto: webCategoryDtos){
    		long categoryId = webDto.getId();
    		
    		int newestCategoryVersion = categoryVersionService.getNewestVersionFor(categoryId);
    		int newestDownloadableVersion = newestCategoryVersion - 1; 
    		if (newestDownloadableVersion == 0){
    			continue;//no category returned when the "newestDownloadableVersion" is 0 
    		}
    		
    		CategoryDTO dto = new CategoryDTO();
    		dto.setId(categoryId);
    		dto.setName(webDto.getName());
    		dto.setNewestVersionNumber(newestDownloadableVersion);
    		dto.setImageUrl(webDto.getImageUrl());
    		categoryDtos.add(dto);
    	}
    	
    	CategoryDTOList result = new CategoryDTOList();
    	result.setData(categoryDtos);
        return result;
    }
    
    /**
     * Query downloadable version for a category identified by the category id.
     * When no newest version available, return "version = 0".
     * 
     * Sample url : http://localhost:8080/quizx/ws/category/{id}/{clientversion} 
     */
    @RequestMapping(value = "/category/{id}/{clientversion}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody DownloadableVersionDTOList getVersions(@PathVariable("id")long id,
    		@PathVariable("clientversion")int clientversion) {
		List<DownloadableVersionDTO> list = new ArrayList<DownloadableVersionDTO>();
		
    	int newestCategoryVersion = categoryVersionService.getNewestVersionFor(id);
		int newestDownloadableVersion = newestCategoryVersion - 1; 
        
    	if (newestDownloadableVersion <= clientversion || newestDownloadableVersion == 0){
    		list.add(new DownloadableVersionDTO());
    	} else {
	    	for (int i = clientversion+1; i <= newestDownloadableVersion; i++){
	    		DownloadableVersionDTO dto = new DownloadableVersionDTO();
	    		dto.setVersion(i);
	    		list.add(dto);
	    	}
    	}

    	DownloadableVersionDTOList dtoList = new DownloadableVersionDTOList();
		dtoList.setData(list);
    	return dtoList;
    }

    /**
     * Query a quiz identified by the quiz id.
	 * sample url: http://localhost:8080/quizx/ws/quiz/2
	 */
    @RequestMapping(value = "/quiz/{id}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody QuizDTO getQuizById(@PathVariable("id")long quizId) {
    	return quizService.getQuizDTO(quizId);
    }
    
	/**
	 * Query all quizs under a category with a particular version.
	 *  
	 * sample url: http://localhost:8080/quizx/ws/quizs/categoryId/version
	 */
    @RequestMapping(value = "/quizs/{categoryId}/{version}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody QuizDTOList getQuizs(@PathVariable("categoryId")long categoryId,
    		@PathVariable("version")int version) {
        List<QuizDTO> quizs = quizService.getQuizs(categoryId, version);
        QuizDTOList dtoList = new QuizDTOList();
        dtoList.setData(quizs);
        return dtoList;
    }
}