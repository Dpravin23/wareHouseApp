package in.nit.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import in.nit.model.User;
import in.nit.service.EmailService;
import in.nit.service.IUserService;
import in.nit.service.OtpService;

@Controller
@RequestMapping("/user")
@SessionAttributes("userOb")
public class UserController {
	protected  final String Employee="";
	protected  final String Admin="";
	@Autowired
	private IUserService service;
	@Autowired
	private EmailService emailService;
	@Autowired
	private OtpService otp;
	
	//1.register
	@GetMapping("/register")
	public String showReg(Model model)
	{
		model.addAttribute("user", new User());
		return "UserRegister";
	}
	
	//2 save user
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User user,Model model) {
		/*
		 * String generatedOtp=otp.generateOtp(user.getEmail());
		 * user.setOtp(generatedOtp); emailService.sendOtpEmail(user.getEmail(),
		 * user.getOtp());//no need to set otp  to user
		 */
		
		Integer id=service.saveUser(user);
		String message="user'"+user.getUserName()+"' saved with id:'"+id+"'";
		model.addAttribute("message", message);
		model.addAttribute("user", new User());
		//System.out.println("sent with otp"+generatedOtp);
		return "UserRegister";
	}
	//3.show all
	@GetMapping("/all")
	public String showAll(Model model) 
	{	
		List<User> list=service.getALLUsers();
		model.addAttribute("list",list);
		return "UserData";
	}
	//4 Edit user
	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model)
	{
		String page=null;
		Optional<User>opt=service.getOneUser(id);
		if(opt.isPresent())
		{
			User ut=opt.get();
			model.addAttribute("user", ut);
			page="UserEdit";
		}
		else
		{
			page="redirect:../all";
		}
		return page;
	}
	
	//5.Delete user
	@GetMapping("/delete/{id}")
	public String removeById(@PageableDefault(page=0,size=3) Pageable pageable,@PathVariable Integer id,Model model)
	{
		String msg=null;
		try {
		if(service.isUserExist(id)) 
		{
		 service.deleteUser(id);
		 msg="User'"+id+"' deleted";
		}
		else
		{
			msg= "User '"+id+"' not exist!!!";
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 model.addAttribute("message", msg);
		 //fetch other data and display
		 List<User> page=service.getALLUsers();
		 model.addAttribute("list",page);
		 return "UserData";
		
	}
	//6.update user
	 @PostMapping("/update")
     public String update(@ModelAttribute User user,Model model)
     { 
     	service.updateUser(user);
     	String msg="User'"+user.getId()+"'updatd!";
     	model.addAttribute("message",msg);
     	model.addAttribute("user", new User());
 		
     	
     	//show other rows with updation
     	 List<User> list=service.getALLUsers();
 		 model.addAttribute("list",list);
 		 return "UserData";
 	   
     }

		/*
		 * @GetMapping("/activate/{id}") public String activateUser(@PathVariable
		 * Integer id) { service.updateUserStatus(true, id); return "redirect:../all"; }
		 * 
		 * @GetMapping("/inactivate/{id}") public String deActivateUser(@PathVariable
		 * Integer id) { service.updateUserStatus(false, id); return "redirect:../all";
		 * }
		 */
	 @GetMapping("/ustatus")
	 public String updateUserStatus(@RequestParam boolean status,@RequestParam Integer id) {
		
		 service.updateUserStatus(status, id);
		 return "redirect:all";
	 }
	  @GetMapping("/login")
     public String userLogin()
     {
    	 return "UserLogin";
     }
	  
	  @GetMapping("/setup")
	 public String doSetup(Principal p,Model model)
	 
	 {   
	     String Url="";
		  String email=p.getName();
	     User user=service.findByEmail(email).get();
	     model.addAttribute("userOb", user); 
	     System.out.println("seupuser done"+user);
	     if(user.getUserRoles().contains("ADMIN"))
	     {
		  Url= "redirect:../home/admin";}else if(user.getUserRoles().contains("EMPLOYEE")) {
			  Url ="redirect:../wareHouseDashboard";}
			/*
			 * else if(user.getUserRoles().contains("ADMIN") &&
			 * user.getUserRoles().contains("EMPLOYEE")) {Url ="redirect:../home/userHome";}
			 */
					  
					  return Url;
	 }
}
