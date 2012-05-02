package azl.quizx.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);

	@RequestMapping(value = "/admin/adminHome.htm", method = RequestMethod.GET)
	public String adminHome(Model model) {
		return "/admin/adminHome";
	}
	
	@RequestMapping(value = "/error/exception.htm", method = RequestMethod.GET)
	public String errorPage(Exception ex, RedirectAttributes attrs, WebRequest request, Model model) {
		logger.error(ex.getMessage());
		
		return "/error/exception";
	}

	/*this needs to go to other controllers*/
	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public String home(Model model) {
    	model.addAttribute("homeHighlighted", "highlighted");
		return "home";
	}
	@RequestMapping(value = "/quiz.htm", method = RequestMethod.GET)
	public String quiz(Model model) {
    	model.addAttribute("quizHighlighted", "highlighted");
		return "quiz";
	}
}