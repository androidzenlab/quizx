package azl.quizx.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import azl.quizx.dto.WebCategoryDTO;
import azl.quizx.dto.QuizDTO;
import azl.quizx.dto.WebQuizDTO;
import azl.quizx.dto.WebQuizSearchDTO;
import azl.quizx.service.CategoryService;
import azl.quizx.service.CategoryVersionService;
import azl.quizx.service.QuizBulkService;
import azl.quizx.service.QuizService;
import azl.quizx.web.validator.QuizDtoValidator;

@Controller
@SessionAttributes(value = {"quizSearchDto"}, types = {WebQuizSearchDTO.class})
public class QuizController {
	private static Logger logger = Logger.getLogger(QuizController.class);

	@Resource(name="quizService")
	private QuizService quizService;
	
	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="quizBulkService")
	private QuizBulkService quizBulkService;
	
	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;
	
	//create the session object, it is really required for the search/edit/delete page.
	@ModelAttribute("quizSearchDto")
	public WebQuizSearchDTO createSessionAttr() {
		return new WebQuizSearchDTO();
	}
	
	@RequestMapping(value = "/admin/quiz.htm", method = RequestMethod.GET)
	public String quizAdmin(@ModelAttribute("quiz")WebQuizDTO quiz, Model model) {
		quiz.reset();
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/quizAdd";
	}

	@RequestMapping(value = "/admin/quiz.htm", method = RequestMethod.POST, params = "!cancel")
	public String quizAdminPost(@ModelAttribute("quiz")@Valid WebQuizDTO quiz, 
			BindingResult result, Model model) {
		if (!result.hasErrors()) {
			String categoryName = quizService.persist(quiz);
			quiz.setCategoryName(categoryName);
			model.addAttribute("sucessfulMsg", quiz.insertSuccessfulMsg());
			quiz.reset();
			quiz.setInsertSuccessful(true);
		}
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/quizAdd";
	}

	@RequestMapping(value = "/admin/quiz.htm", method = RequestMethod.POST, params = "cancel")
	public String quizAdminPostCancel(){
		return "/admin/adminHome";
	}

	@RequestMapping(value = "/admin/quizBulk.htm", method = RequestMethod.GET)
	public String quizBulk(@ModelAttribute("quiz")WebQuizDTO quiz){
		quiz.reset();
		return "/admin/quizBulkAdd";
	}	
	
	@RequestMapping(value = "/admin/quizBulk.htm", method = RequestMethod.POST, params = "!cancel")
	public String quizBulkPost(@ModelAttribute("quiz")WebQuizDTO quiz, 
			BindingResult result, Model model) throws IOException {
		QuizDtoValidator.validateUploadQuizFile(quiz, "quiz", result);
		
		if (!result.hasErrors()) {
			String validationErrors = quizBulkService.insertQuizs(quiz.getBulkQuiz().getInputStream());
			if (validationErrors.length() == 0){
				//no error which means the db insert is successful
				quiz.reset();
				quiz.setInsertSuccessful(true);
				
				return "/admin/quizBulkAdd";
			} else {
				model.addAttribute("validationErrors",validationErrors);
				return "/admin/quizBulkError";	
			}			
		} else {
			return "/admin/quizBulkAdd";
		}
	}
	
	@RequestMapping(value = "/admin/quizBulk.htm", method = RequestMethod.POST, params = "cancel")
	public String quizBulkPostCancel(){
		return "/admin/adminHome";
	}

	@RequestMapping(value = "/admin/quizSearch.htm", method = RequestMethod.GET)
	public String quizSearch(@ModelAttribute("quizSearchDto")WebQuizSearchDTO dto, Model model){
		List<WebCategoryDTO> categories = categoryService.getCategoryDtoList();
		if (dto.getCategoryId() == 0){
			//from the admin screen link, set a valid categoryId
			if (categories.size() > 0){
				dto.setCategoryId(categories.get(0).getId());
			}
		}
		long categoryId = dto.getCategoryId();			
		if (dto.isFromEditDeletePage()){
			List<QuizDTO> quizs = quizService.getQuizs(categoryId, dto.getCategoryVersion());
			model.addAttribute("quizs", quizs);
			dto.setFromEditDeletePage(false);
		}
		model.addAttribute("versions", categoryVersionService.getVersionsFor(categoryId));
		model.addAttribute("categories", categories);
		return "/admin/quizSearch";
	}	

	@RequestMapping(value = "/admin/quizSearch.htm", method = RequestMethod.POST, params = "search")
	public String quizSearchPost(@ModelAttribute("quizSearchDto")WebQuizSearchDTO dto, Model model)
	{
		//WebQuizSearchDTO searchDto = (WebQuizSearchDTO)session.getAttribute("quizSearchDto");
		long categoryId = dto.getCategoryId();
		List<QuizDTO> quizs = quizService.getQuizs(categoryId, dto.getCategoryVersion());
			model.addAttribute("quizs", quizs);	
		
		model.addAttribute("versions", categoryVersionService.getVersionsFor(categoryId));
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		
		return "/admin/quizSearch";
	}	
	
	@RequestMapping(value = "/admin/quizSearch.htm", method = RequestMethod.POST, params = "bulkModify")
	public String quizBulkModifyPost(@ModelAttribute("quizSearchDto")WebQuizSearchDTO dto, Model model)
	{
		int numberOfUpdates = quizService.modifyQuizsVersion(dto.getCategoryId());
		model.addAttribute("versions", categoryVersionService.getVersionsFor(dto.getCategoryId()));
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		model.addAttribute("modifiedQuizs", numberOfUpdates);
		return "/admin/quizSearch";
	}
	
	@RequestMapping(value = "/admin/quizSearch.htm", method = RequestMethod.POST, params = "cancel")
	public String quizSearchPostCancel(){
		return "/admin/adminHome";
	}

	@RequestMapping(value = "/admin/quizEdit.htm", method = RequestMethod.GET)
	public String quizEdit(@RequestParam("id")long quizId,Model model)
	{
		QuizDTO quizDto = quizService.getQuizDTO(quizId);
		model.addAttribute("quiz", quizDto);
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/quizEdit";
	}

	@RequestMapping(value = "/admin/quizEdit.htm", method = RequestMethod.POST, params = "!cancel")
	public String quizEditPost(@ModelAttribute("quiz")@Valid WebQuizDTO quiz,			
			BindingResult result, HttpSession session, Model model) {
		
		if (!result.hasErrors()) {
			quizService.editQuiz(quiz);
			setFromEditDeletePage(session);
			return "redirect:/admin/quizSearch.htm";
		} else {
			model.addAttribute("categories", categoryService.getCategoryDtoList());
			return "/admin/quizEdit";
		}
	}

	@RequestMapping(value = "/admin/quizEdit.htm", method = RequestMethod.POST, params = "cancel")
	public String quizEditPostCancel(HttpSession session){
		setFromEditDeletePage(session);
		return "redirect:/admin/quizSearch.htm";
	}
			
	@RequestMapping(value = "/admin/quizDelete.htm", method = RequestMethod.GET)
	public String quizDelete(@RequestParam("id")long quizId, 
			@ModelAttribute("quiz")WebQuizDTO quiz, HttpSession session)
	{
		quizService.deleteQuiz(quizId);
		logger.info("Deleted quiz, id = " + quizId);
		setFromEditDeletePage(session);
		return "redirect:/admin/quizSearch.htm";
	}
	
	private void setFromEditDeletePage(HttpSession session){
		WebQuizSearchDTO searchDto = (WebQuizSearchDTO)session.getAttribute("quizSearchDto");
		searchDto.setFromEditDeletePage(true);
	}
	
/*	@RequestMapping(value = "/admin/categoryClick.htm", method = RequestMethod.GET)
	public @ResponseBody String categoryVersionAjax(@RequestParam("categoryId")String categoryId) {
		return "1,2,3,4,5";
		//return categoryVersionService.getVersionsFor(Long.parseLong(categoryId));
	}*/
}