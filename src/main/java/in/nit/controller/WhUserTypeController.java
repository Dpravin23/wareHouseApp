package in.nit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.WhUserType;
import in.nit.service.IWhUserTypeService;
import in.nit.util.EmailUtil;
import in.nit.view.WhUserTypeExcelView;
@Controller
@RequestMapping("/whuserType")
public class WhUserTypeController {

	@Autowired
	private IWhUserTypeService service;
	@Autowired
	private EmailUtil emailUtil;
	//1.Display Register page
	@GetMapping("/register")
	public String showRegister(Model model) {
		//form backing object
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}
	//2.on click submit read form data & save data
	@PostMapping("/save")
	public  String Save(@ModelAttribute WhUserType whUserType,Model model)
	{
	 Integer id=service.saveWhUserType(whUserType);
	
	 String msg="WhUserType '"+id+"' saved ";
	 //send email on save
	 if(id!=0) {
		 new Thread(new Runnable() {
			public void run() {
				boolean flag= emailUtil.send(whUserType.getUserMail(),
						"Welcome", "Hello User:"+whUserType.getUserCode()
						+",You are type:"+whUserType.getUserType());
				System.out.println("email sent"+flag);
			}
		}).start();
			
		 
			/*
			 * if(flag) msg+=",Email sent!!!"; else msg+=",email not sent!!!";
			 */	        	  
	 }
	 model.addAttribute("message",msg);
	 //form backing object
	 model.addAttribute("whusertype",new WhUserType());
	 return "WhUserTypeRegister";	
	}
	//3.display all record at UI
	@GetMapping("/all")
	public String showAll(Model model)
	{
	List<WhUserType> list=service.getALLWhUserTypes();
	model.addAttribute("list",list);
	return "WhUserTypeData";
	}
	//4.removeById
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model)
	{
		String msg=null;
		if(service.isWhUserTypeExist(id)) 
		{
		 service.deleteWhUserType(id);
		 msg="WhUserType'"+id+"' deleted";
		}
		else
		{
			msg= "WhUserType '"+id+"' not exist!!!";
		}
		 model.addAttribute("message", msg);
		 //fetch other data and display
		 List<WhUserType> list=service.getALLWhUserTypes();
		 model.addAttribute("list",list);
		 return "WhUserTypeData";
		
	}
	
	//5.Show edit page
	/**
	 * if edit send to register page
	 */
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model)
	{
		String page=null;
		Optional<WhUserType>opt=service.getOneWhUserType(id);
		if(opt.isPresent())
		{
			WhUserType st=opt.get();
			model.addAttribute("whUserType", st);
			page="WhTypeUserEdit";
		}
		else
		{
			page="redirect:../all";
		}
		return page;
	}
	//6 update on click update
	   @PostMapping("/update")
        public String update(@ModelAttribute WhUserType whUserType,Model model)
        { 
        	service.updateWhUserType(whUserType);
        	String msg="ShipmentTypr'"+whUserType.getId()+"'updatd!";
        	model.addAttribute("message",msg);
        	model.addAttribute("whUserType", new WhUserType());
    		
        	
        	//show other rows with updation
        	 List<WhUserType> list=service.getALLWhUserTypes();
    		 model.addAttribute("list",list);
    		 return "WhUserTypeData";
    	   
        }
	   @GetMapping("/view/{id}")
	   public String showView(@PathVariable Integer id,Model model)
	   {   String page=null;
	       Optional<WhUserType> opt= service.getOneWhUserType(id);
	       if(opt.isPresent())
	       {
	    	   WhUserType wt=opt.get();
	    	   model.addAttribute("ob",wt);
	    	   return "WhUSerTypeDataView";
	       }else {
	   		page="redirect:../all";
	   	}
	   	return page;
	   }
	   //7.Export data to excel file
	   @GetMapping("/excel")
	   public ModelAndView exportToExcel() {
		   ModelAndView m=new ModelAndView();
		   m.setView(new WhUserTypeExcelView()) ;
		   
		   List<WhUserType> list=service.getALLWhUserTypes();
		   m.addObject("obs", list);
		return m;
		   
	   }
}
