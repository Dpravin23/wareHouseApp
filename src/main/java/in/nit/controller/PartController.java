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
import in.nit.model.Part;
import in.nit.service.IPartService;
import in.nit.service.IUomTypeService;


@Controller
@RequestMapping("/part")
public class PartController {
	@Autowired
	private IPartService service;
	
	//Integration to part module
	@Autowired
	private IUomTypeService uomsSrvice;

	/*
	 * call this method inside other controller method where return page is register
	 * or edit
	 */
	private void addDropDownUi(Model model)
	{
		model.addAttribute("uoms",uomsSrvice.getUomIDModel());	
	}
	//1.Display Register page
	@GetMapping("/register")
	public String showPartRegister(Model model) {
		//form backing object
		model.addAttribute("part", new Part());
		addDropDownUi(model);
		return "PartRegister";
	}
	//2.on click submit read form data & save data
	@PostMapping("/save")
	public  String Save(@ModelAttribute Part part,Model model)
	{
	 Integer id=service.SavePart(part);
	 String msg="Part '"+id+"' saved ";
	 model.addAttribute("message",msg);
	 model.addAttribute("part",new Part());
	 addDropDownUi(model);
	 return "PartRegister";	
	}
	//3.display all record at UI
	@GetMapping("/all")
	public String showAll(Model model)
	{
	List<Part> list=service.getAllParts();
	model.addAttribute("list",list);
	return "PartData";
	}
	//4.removeById
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model)
	{
		String msg=null;
		if(service.isPartExist(id)) 
		{
		 service.deletePart(id);
		 msg="part'"+id+"' deleted";
		}
		else
		{
			msg= "Part '"+id+"' not exist!!!";
		}
		 model.addAttribute("message", msg);
		 //fetch other data and display
		 List<Part> list=service.getAllParts();
		 model.addAttribute("list",list);
		 return "PartData";
		
	}
	
	//5.Show edit page
	/**
	 * if edit send to register page
	 */
	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model)
	{
		String page=null;
		Optional<Part>opt=service.getOnePart(id);
		if(opt.isPresent())
		{
			Part pt=opt.get();
			model.addAttribute("part", pt);
			addDropDownUi(model);
			page="PartEdit";
		}
		else
		{
			page="redirect:../all";
		}
		return page;
	}
	//6 update on click update
	   @PostMapping("/update")
        public String update(@ModelAttribute Part part,Model model)
        { 
        	service.updatePart(part);
        	String msg="part'"+part.getId()+"'updated!";
        	model.addAttribute("message",msg);
        	model.addAttribute("part", new Part());
    		
        	
        	//show other rows with updation
        	 List<Part> list=service.getAllParts();
    		 model.addAttribute("list",list);
    		 return "partData";
    	   
    		 
    		 
        }


}
