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

import in.nit.model.Grn;
import in.nit.model.GrnDtl;
import in.nit.model.OrderMethod;
import in.nit.model.PurchaseDtl;
import in.nit.service.IGrnService;
import in.nit.service.IPurchaseOrderService;

@Controller
@RequestMapping("/grn")
public class GrnController {
	@Autowired
	private IGrnService service;
	
	@Autowired
	private IPurchaseOrderService poService;
	
	private void addDropDownUi(Model model) {
		model.addAttribute("pos", poService.getPoIdAndCodeByStatus("INVOICED"));	
		}
	@GetMapping("/register")
	public String showReg(Model model)
	{  model.addAttribute("grn",new Grn());
	   addDropDownUi( model);
		return "GrnReg";
	}
	@PostMapping("/save")
	public String saveGrn(@ModelAttribute Grn Grn,Model model){
		Integer grnId=service.saveGrn(Grn);
		model.addAttribute("message","GRN SAVE WITH:"+grnId);
		model.addAttribute("grn",new Grn());
		if(grnId!=null)
		{
			Integer poId= Grn.getPo().getId();
			poService.updatePurchaseOrderStatus("RECEIVED", poId);
			convertPurhseDtlToGrnDtl( grnId, poId);
		}
		addDropDownUi( model);
		return "GrnReg";
		
	}
	/*
	 * @GetMapping("/edit/{id}") public String grnEdit(@PathVariable Integer id,Grn
	 * grn, Model model) { String page=null; Optional<Grn>
	 * opt=service.getOneGrn(id); if(opt.isPresent()) {
	 * model.addAttribute("grn",opt.get()); if(id!=null) { Integer poId=
	 * opt.get().getPo().getId(); //poService.updatePurchaseOrderStatus("RECEIVED",
	 * poId); convertPurhseDtlToGrnDtl( id, poId); } addDropDownUi( model); page=
	 * "GrnEdit"; }else { page="redirect:../all"; } return page; }
	 */

	/*
	 * @PostMapping("/update") public String updateGrn(@ModelAttribute Grn Grn,Model
	 * model){ service.updateGrn(Grn);
	 * model.addAttribute("message","GRN '"+Grn.getId()+"' UPDATED");
	 * model.addAttribute("grn",service.getALLGrns()); if(Grn.getId()!=null) {
	 * Integer poId= Grn.getPo().getId();
	 * poService.updatePurchaseOrderStatus("RECEIVED", poId);
	 * convertPurhseDtlToGrnDtl(Grn.getId(), poId); } addDropDownUi( model); return
	 * "GrnData";
	 * 
	 * }
	 */
	@GetMapping("/all")
	public String showAll(Model model)
	{   List<Grn> list=service.getALLGrns();
	    model.addAttribute("list", list);
		return "GrnData";
	}
	
	private void convertPurhseDtlToGrnDtl(Integer grnId,Integer poId) {
		
		//1.Get Purchse dtls list using poId
		List<PurchaseDtl> poDtlsList=poService.getPurchaseDtlWithPoId(poId);
		//2.covert one podtl object to one grnDtlObject
		for(PurchaseDtl poDtl:poDtlsList)
		{
			GrnDtl grnDtl= new GrnDtl();
			grnDtl.setPartCode(poDtl.getPart().getPartCode());
			grnDtl.setBaseCost(poDtl.getPart().getPartCost());
			grnDtl.setQty(poDtl.getQty());
			grnDtl.setLineValue(grnDtl.getQty()*grnDtl.getBaseCost());
			
			//link with GRn object 
			Grn grn=new Grn();
			grn.setId(grnId);
			grnDtl.setGrn(grn);
			
			//save in db
			service.saveGrnDtl(grnDtl);
		}
	}
	

	//Display all grn details here
	@GetMapping("/viewDtls/{id}")
	public String showGrnDtls(@PathVariable Integer id,Model model) {
		List<GrnDtl> dtls =service.getAllDtlsByGrnId(id);
		model.addAttribute("dtls", dtls);
		return "GrnDtlsView";
	}
	//accept or reject part and come back to same page
	@GetMapping("/status")
	public String updateDtlStatus(@RequestParam Integer grnId,
		                        	@RequestParam Integer dtlId,
			                         @RequestParam String status) {
		
		service.updateStatusByGrnDtlId(status, dtlId);
		return "redirect:viewDtls/"+grnId;
	}
	
	/*
	 * @GetMapping("/delete/{id}") public String delete(@PathVariable Integer
	 * id,Model model) { String message=null; if(service.isGrnExist(id)) {
	 * service.deleteGrn(id); message="Grn '" +id+ "' deleted"; } else {
	 * message="Grn '" +id+"' not exist!!!"; } model.addAttribute("message",
	 * message); //get updated model.addAttribute("list",service.getALLGrns());
	 * return "GrnData"; }
	 */
}
