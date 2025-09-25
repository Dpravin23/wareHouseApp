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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.ShipmentType;
import in.nit.service.IShipmentTypeService;
import in.nit.view.ShimenttypeExcelView;
import jakarta.jms.Session;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/shipmenttype")
public class ShipmentTypeController {
	
	
	@Autowired
	private IShipmentTypeService service;
	//1.Display Register page
	@GetMapping("/register")
	public String showRegister(Model model) {
		//form backing object
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}
	//2.onclick submit read form data & save data
	@PostMapping("/save")
	public  String Save(@ModelAttribute ShipmentType shipmentType,Model model)
	{
	 Integer id=service.saveShipmentType(shipmentType);
	 String msg="shipmentType '"+id+"' saved ";
	 model.addAttribute("message",msg);
	 model.addAttribute("shipmentType", new ShipmentType());
	 return "ShipmentTypeRegister";	
	}
	//3.display all record at UI
	@GetMapping("/all")
	public String showAll(Model model)
	{
	List<ShipmentType> list=service.getALLShipmentTypes();
	model.addAttribute("list",list);
	return "ShipmentTypeData";
	}
	//4.removeById
	@GetMapping("/delete/{id}")
	public String removeById(@PathVariable Integer id,Model model)
	{
		String msg=null;
		if(service.isShipmentTypeExist(id)) 
		{
		 service.deleteShipmentType(id);
		 msg="ShipmentType'"+id+"' deleted";
		}
		else
		{
			msg= "shipmenttype '"+id+"' not exist!!!";
		}
		 model.addAttribute("message", msg);
		 //fetch other data and display
		 List<ShipmentType> list=service.getALLShipmentTypes();
		 model.addAttribute("list",list);
		 return "ShipmentTypeData";
		
	}
	
	//5.Show edit page
	/**
	 * if edit send to register page
	 */
	@GetMapping("edit/{id}")
	public String showEdit(@PathVariable Integer id ,Model model)
	{
		String page=null;
		Optional<ShipmentType>opt=service.getOneShipmentType(id);
		if(opt.isPresent())
		{
			ShipmentType st=opt.get();
			model.addAttribute("shipmentType", st);
			page="ShipmenttypeEdit";
		}
		else
		{
			page="redirect:../all";
		}
		return page;
	}
	//6 update on click update
	   @PostMapping("/update")
        public String update(@ModelAttribute ShipmentType shipmentType,Model model)
        { 
        	service.updateShipmentType(shipmentType);
        	String msg="ShipmentTypr'"+shipmentType.getId()+"'updatd!";
        	model.addAttribute("message",msg);
        	model.addAttribute("shipmentType", new ShipmentType());
    		
        	
        	//show other rows with updation
        	 List<ShipmentType> list=service.getALLShipmentTypes();
    		 model.addAttribute("list",list);
    		 return "ShipmentTypeData";
    	   
        }
	   //7.Export data to excel file
	   @GetMapping("/excel")
	   public ModelAndView exportToExcel() {
		   ModelAndView m=new ModelAndView();
		   m.setView(new ShimenttypeExcelView());
		   
		   List<ShipmentType> list=service.getALLShipmentTypes();
		   m.addObject("obs", list);
		return m;
		   
	   }
	   //ajax validation
	   @GetMapping("/validateshipmentcode")
	   public @ResponseBody String  validateShipmentCode(@RequestParam String shipmentcode,
			   @RequestParam Integer id)
	   {
		  String message="";
		  if(id==0 && service.isShipmentTypeCodeExist(shipmentcode))
		  {System.out.println("ID="+id);
			  message="shipment code'"+shipmentcode+"' already exist";
	      }else if (service.isShipmentTypeCodeExistForEdit(shipmentcode,id))
		  {
	    	   System.out.println("ID="+id);
			  message="shipment code'"+shipmentcode+"' already exist";
	      }
		   return message;
	   }

}
