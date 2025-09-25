package in.nit.controller;

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

import in.nit.model.UomType;
import in.nit.service.IUomTypeService;


@Controller
@RequestMapping("/uomtype")
public class UomTypeController {
	@Autowired
	private IUomTypeService service;
	//1.Display Register page
	@GetMapping("/register")
	public String showUomRegister(Model model) {
		//form backing object
		model.addAttribute("uomType", new UomType());
		return "UomTypeRegister";
	}
	//2.onclick submit read form data & save data
	@PostMapping("/save")
	public  String Save(@ModelAttribute UomType uomType,Model model)
	{
	 Integer id=service.saveUomType(uomType);
	 String msg="UomType '"+id+"' saved ";
	 model.addAttribute("message",msg);
	 model.addAttribute("uomType", new UomType());
	 return "UomTypeRegister";	
	}
	//3.display all record at UI
	//all?(page=1&size=3)---->pageable value
	@GetMapping("/all")
	public String showAll(@PageableDefault(page=0,size=3) Pageable pageable, Model model)
	{
	Page<UomType> page=service.getAllUoms(pageable);
	model.addAttribute("page",page);
	return "UomTypeData";
	}

	/*
	 * public String showAll(Model model) { List<UomType>
	 * list=service.getALLUomTypes(); model.addAttribute("list",list); return
	 * "UomTypeData"; }
	 */
	//4.removeById
	@GetMapping("/delete/{id}")
	public String removeById(@PageableDefault(page=0,size=3) Pageable pageable,@PathVariable Integer id,Model model)
	{
		String msg=null;
		try {
		if(service.isUomTypeExist(id)) 
		{
		 service.deleteUomType(id);
		 msg="UomType'"+id+"' deleted";
		}
		else
		{
			msg= "UomType '"+id+"' not exist!!!";
		}
		}catch(Exception e)
		{
			msg="UomType is used by other ,can not be deleted";
		}
		 model.addAttribute("message", msg);
		 //fetch other data and display
		 Page<UomType> page=service.getAllUoms(pageable);
		 model.addAttribute("page",page);
		 return "UomTypeData";
		
	}
	
	//5.Show edit page
	/**
	 * if edit send to register page
	 */
	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model)
	{
		String page=null;
		Optional<UomType>opt=service.getOneUomType(id);
		if(opt.isPresent())
		{
			UomType ut=opt.get();
			model.addAttribute("uomType", ut);
			page="UomTypeEdit";
		}
		else
		{
			page="redirect:../all";
		}
		return page;
	}
	//6 update on click update
	   @PostMapping("/update")
        public String update(@ModelAttribute UomType uomType,Model model)
        { 
        	service.updateUomType(uomType);
        	String msg="UomType'"+uomType.getId()+"'updated!";
        	model.addAttribute("message",msg);
        	model.addAttribute("uomType", new UomType());
    		
        	
        	//show other rows with updation
        	 List<UomType> list=service.getALLUomTypes();
    		 model.addAttribute("list",list);
    		 return "UomTypeData";
    	   
    		 
    		 
        }

}
