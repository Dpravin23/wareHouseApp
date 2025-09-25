package in.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.nit.model.User;
import in.nit.repo.UserRepo;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private UserRepo ur;
	@GetMapping("/userHome")
	public String getHome()
	{
		return "Home";
	}
	//2.admin
			@GetMapping("/admin")
			public String showAdmin(Model model)
			{   model.addAttribute("inactiveUsers",ur.inactiveUserCount());
				model.addAttribute("user", new User());
				return "ADMIN";
			}

}
