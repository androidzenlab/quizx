package azl.quizx.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import azl.quizx.dto.WebCategoryDTO;
import azl.quizx.service.CategoryService;

@Controller
public class CategoryController {
	protected static Logger logger = Logger.getLogger(CategoryController.class);

	@Resource(name="categoryService")
	private CategoryService categoryService;

	
	@RequestMapping(value = "/admin/category.htm", method = RequestMethod.GET)
	public String categoryAdminGet(Model model) {
		model.addAttribute("category", new WebCategoryDTO());
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/categoryAdd";
	}

	@RequestMapping(value = "/admin/category.htm", method = RequestMethod.POST, params = "add")
	public String categoryAdminPost(@ModelAttribute("category")@Valid WebCategoryDTO category, 
			BindingResult result, Model model) throws FileNotFoundException, IOException {
		if (category.getUploadImage().isEmpty()) {
			//new FieldError(String objectName, String field, String defaultMessage)
			FieldError imageFieldError = new FieldError("category", "uploadImage", 
					"upload image file can not be empty");
			result.addError(imageFieldError);
		}
		
		if (!result.hasErrors()) {
			categoryService.persist(category);
			model.addAttribute("category", new WebCategoryDTO());
		}
		model.addAttribute("categories", categoryService.getCategoryDtoList());
		return "/admin/categoryAdd";
	}

	@RequestMapping(value = "/admin/category.htm", method = RequestMethod.POST, params = "cancelAdd")
	public String categoryAdminPostCancelAdd() {
		return "/admin/adminHome";
	}
	
	@RequestMapping(value = "/admin/categoryEdit.htm", method = RequestMethod.GET)
	public String categoryEdit(@RequestParam("id")long categoryId, Model model)
	{
		WebCategoryDTO cateDto = categoryService.getCategoryDTO(categoryId);
		model.addAttribute("category", cateDto);
		return "/admin/categoryEdit";
	}

	@RequestMapping(value = "/admin/categoryEdit.htm", method = RequestMethod.POST, params = "!cancel")
	public String categoryEditPost(@ModelAttribute("category")@Valid WebCategoryDTO dto, 
			BindingResult result, Model model)throws FileNotFoundException, IOException {
		
		if (!result.hasErrors()) {
			categoryService.editCategory(dto);
			return "redirect:/admin/category.htm";
		} else {
			return "/admin/categoryEdit";
		}
	}

	@RequestMapping(value = "/admin/categoryEdit.htm", method = RequestMethod.POST, params = "cancel")
	public String categoryEditPostCancel(){
		return "redirect:/admin/category.htm";
	}
			
	@RequestMapping(value = "/admin/categoryDelete.htm", method = RequestMethod.GET)
	public String categoryDelete(@RequestParam("id")long categoryId)
	{
		categoryService.removeCategory(categoryId);
		
		return "redirect:/admin/category.htm";
	}
}