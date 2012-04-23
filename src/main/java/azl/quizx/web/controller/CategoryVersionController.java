package azl.quizx.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import azl.quizx.dto.WebCategoryDTO;
import azl.quizx.dto.WebCategoryVersionDTO;
import azl.quizx.service.CategoryService;
import azl.quizx.service.CategoryVersionService;

@Controller
public class CategoryVersionController {
	protected static Logger logger = Logger.getLogger(CategoryVersionController.class);

	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="categoryVersionService")
	private CategoryVersionService categoryVersionService;

	@RequestMapping(value = "/admin/categoryVersion.htm", method = RequestMethod.GET)
	public String categoryVersionAdminGet(@ModelAttribute("categoryVersion")WebCategoryVersionDTO	dto, Model model) 
	{
		long categoryId = dto.getCategoryId();
		List<WebCategoryDTO> categories = categoryService.getCategoryDtoList();
		if (categoryId == 0){
			//from the admin screen link, set a valid categoryId
			if (categories.size() > 0){
				dto.setCategoryId(categories.get(0).getId());
				categoryVersionService.setNewestVersions(dto);
			}
		} else {
			dto.setCategoryId(categoryId);
			categoryVersionService.setNewestVersions(dto);
		}
		
		model.addAttribute("categories", categories);
		return "/admin/categoryVersion";		
	}

	@RequestMapping(value = "/admin/categoryVersion.htm", method = RequestMethod.POST, params = "!cancel")
	public String categoryVersionAdminPost(@ModelAttribute("categoryVersion")WebCategoryVersionDTO 
			dto,	Model model) {
		dto.increaseVersion();
		categoryVersionService.persist(dto.getCategoryId(), dto.getNewestCategoryVersion());
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/categoryVersion";
	}

	@RequestMapping(value = "/admin/categoryVersion.htm", method = RequestMethod.POST, params = "cancel")
	public String categoryVersionPostCancel(){
		return "/admin/adminHome";
	}
}