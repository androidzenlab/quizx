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
	protected static Logger logger = Logger.getLogger(WSController.class);

	//When no newest version available, return 0 in the "clientVersion" WS response.
	private static final int NO_NEWEST_VERSION = 0;	

	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="quizService")
	private QuizService quizService;

	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;

	/**
	 * Sample url: http://localhost:8080/quizx/ws/categories
	 */
    @RequestMapping(value = "/categories", method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody CategoryDTOList getCategories() {
    	List<CategoryDTO> categoryDtos = new ArrayList<CategoryDTO>();
    			
    	List<WebCategoryDTO> webCategoryDtos = categoryService.getCategoryDtoList();
    	for (WebCategoryDTO webDto: webCategoryDtos){
    		long categoryId = webDto.getId();
    		
    		int newestCategoryVersion = categoryVersionService.getNewestVersionFor(categoryId);
    		int newestDownloadableVersion = newestCategoryVersion - 1; 
    		
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
     * Sample url : http://localhost:8080/quizx/ws/category/{id}/{clientversion} 
     */
    @RequestMapping(value = "/category/{id}/{clientversion}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody String getVersions(@PathVariable("id")long id,
    		@PathVariable("clientversion")int clientversion) {
    	
		int newestCategoryVersion = categoryVersionService.getNewestVersionFor(id);
		int newestDownloadableVersion = newestCategoryVersion - 1; 
        
		return getVersionsBy(newestDownloadableVersion, clientversion);
    }
    
    private static String getVersionsBy(int newestDownloadableVersion, int clientversion){
    	if (newestDownloadableVersion <= clientversion || newestDownloadableVersion == 0){
    		return "{\"data\":[{\"version\":"+NO_NEWEST_VERSION+"}]}";
    	} else {
	    	StringBuffer buf = new StringBuffer("{\"data\":[");
	    	for (int i = clientversion+1; i <= newestDownloadableVersion; i++){
	    		buf.append("{\"version\":" + i + "}" + ",");
	    	}
	
	    	buf.deleteCharAt(buf.length()-1);//delete the last ","
	    	buf.append("]}");
	
	    	return buf.toString();
    	}
    }

    /**
	 * sample url: http://localhost:8080/quizx/ws/quiz/2
	 */
    @RequestMapping(value = "/quiz/{id}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody QuizDTO getQuizById(@PathVariable("id")long quizId) {
        logger.debug("Received request to show test page.");
        
        QuizDTO quizDTO = quizService.getQuizDTO(quizId);
        
        return quizDTO;
    }
    
	/**
	 * sample url: http://localhost:8080/quizx/ws/quizs/categoryId/version
	 */
    @RequestMapping(value = "/quizs/{categoryId}/{version}",
            method = RequestMethod.GET,
            headers="Accept=application/xml, application/json")
    public @ResponseBody QuizDTOList getQuizs(@PathVariable("categoryId")long categoryId,
    		@PathVariable("version")int version) {
        logger.debug("Received request to show test page.");
        
        List<QuizDTO> quizs = quizService.getQuizs(categoryId, version);
        QuizDTOList dtoList = new QuizDTOList();
        dtoList.setData(quizs);
        return dtoList;
    }
    
    /* simple testing
    public static void main(String[] args){
    	System.out.println("newestDownloadableVersion=2, clientVersion=3 : " + getVersionsBy(2, 3));
    	System.out.println("newestDownloadableVersion=3, clientVersion=3 : " + getVersionsBy(3, 3));
    	System.out.println("newestDownloadableVersion=4, clientVersion=3 : " + getVersionsBy(4, 3));
    	System.out.println("newestDownloadableVersion=5, clientVersion=3 : " + getVersionsBy(5, 3));
    }*/
}
/*
    /**
	 * Sample url: http://localhost:8080/quizx/ws/categoryImg/1 
	 *//*
	@RequestMapping(value = "/categoryImg/{id}", method = RequestMethod.GET)
	public @ResponseBody String categoryGetImg(@PathVariable("id")long categoryId)
		throws IOException 
	{
		String imagePath = categoryService.getCategoryImageUrl(categoryId);
		return imagePath;
	}
private String getAbsoluteUrlPrefix(HttpServletRequest request){
	StringBuffer buf = new StringBuffer(request.getServerName());
	int port = request.getServerPort();
	if (port > 0){
		buf.append(":" + port);
	}
	
	buf.append(request.getContextPath());
	buf.append("/");
	return buf.toString();
}*/