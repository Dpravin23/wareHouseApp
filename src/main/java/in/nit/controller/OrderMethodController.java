package in.nit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.nit.model.OrderMethod;
import in.nit.service.IOrderMethodService;
import in.nit.spec.OrderMethodSpec;



@Controller
@RequestMapping("/ordermethod")
public class OrderMethodController {

@Autowired	
private IOrderMethodService service;	

//1. show reg page
@GetMapping("/register")
public String showReg(Model model)
{
	model.addAttribute("orderMethod",new OrderMethod());
	return "OrderMethodRegister";
}
//2.save ordermethod
@PostMapping("/save")
public String saveOrder(@ModelAttribute OrderMethod ordermethod ,Model model)
{
	Integer id= service.SaveOrderMehod(ordermethod);
	String message="order method '"+id+"' saved";
	model.addAttribute("message", message);
	//clear form
	model.addAttribute("orderMethod",new OrderMethod());
	return "OrderMethodRegister";
}
//3.ShowAll
@GetMapping("/all")
public String getall(@ModelAttribute OrderMethod orderMethod,Model model)
{   
	Specification<OrderMethod> spec=new OrderMethodSpec(orderMethod);
	model.addAttribute("list",service.getAllOrderMethods(spec));
	model.addAttribute("orderMethod", orderMethod);//formbacking
	return "OrderMethodData";
}
//4.Edit
@GetMapping("/edit/{id}")
public String editOrderMethod(@PathVariable Integer id, Model model) {
	String page=null;
	Optional<OrderMethod> opt=service.getOneOrderMethod(id);
	if(opt.isPresent())
	{
		model.addAttribute("orderMethod",opt.get());
		page= "OrderMethodEdit";
	}else {
		page="redirect:../all";
	}
	return page;
}
//5.update
@PostMapping("/update")
public String update(@ModelAttribute OrderMethod orderMethod,Model model)
 {
	service.updateOrderMethod(orderMethod); 
	model.addAttribute("message", "order method'"+orderMethod.getId()+"' updated");
	model.addAttribute("list",service.getAllOrderMethods());
	return "OrderMethodData";
}
//6.Delete
@GetMapping("/delete/{id}")
public String delete(@PathVariable Integer id,Model model) {
    
	String message=null;
	if(service.isOrderMethodExist(id))
	{
	service.deleteOrderMethod(id);
	message="ordermehtod '" +id+ "' deleted";
	}else
	{
		message="ordermehtod '" +id+ "' not exist!!!";
	}
	model.addAttribute("message", message);
	//get updated data
	model.addAttribute("list",service.getAllOrderMethods());
	return "OrderMethodData";
	}
//7.ShowView
@GetMapping("/view/{id}")
public String showView(@PathVariable Integer id,Model model)
{
	String page=null;
	Optional<OrderMethod> opt=service.getOneOrderMethod(id);
	if(opt.isPresent())
	{
		OrderMethod om=opt.get();
		model.addAttribute("ob", om);
		return "OrderMethodView"; 
	}else {
		page="redirect:../all";
	}
	return page;
}
}




