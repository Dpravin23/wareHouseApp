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
import org.springframework.web.servlet.ModelAndView;

import in.nit.model.Part;
import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;
import in.nit.service.IPartService;
import in.nit.service.IPurchaseOrderService;
import in.nit.service.IShipmentTypeService;
import in.nit.service.IWhUserTypeService;
import in.nit.view.VendorInvoicePdf;



@Controller
@RequestMapping("/purchaseOrder")

public class PurchaseOrderController {
	 @Autowired
	 private IPurchaseOrderService service;
	 @Autowired
	 private IShipmentTypeService shipmentTypeService;
	 @Autowired
	 private IWhUserTypeService whUserTypeservice;
	 @Autowired
	 private IPartService partService;
	 
	 
	 /*
		 * call this method inside other controller method where return page is register
		 * or edit
		 */
		private void addDropDownUi(Model model)
		{
			model.addAttribute("shipmentTypes",shipmentTypeService.getshipmentIdAndCode());	
			model.addAttribute("whUserTypes",whUserTypeservice.getwhUseridAndType("vendor"));
		}
		
	
		@GetMapping("/register")
	 public String RegPurchaseOrder(Model model)
	 {
		model.addAttribute("purchaseOrder",new PurchaseOrder());
		addDropDownUi(model);
		return "PurchaseOrderRegister";
		 
	 }
	 @PostMapping("/save")
	 public String SavePO(@ModelAttribute PurchaseOrder purchaseorder,Model model) {
		 Integer id=service.savePurchaseOrder(purchaseorder);
		 String msg="Purhase Order '"+id+"' saved ";
		 model.addAttribute("message",msg);
		 return "PurchaseOrderRegister";	
		 
	 }
	 @GetMapping("/all")
	 public String showAll(Model model) {
			List<PurchaseOrder> list=service.getALLPurchaseOrders();
			model.addAttribute("list",list);
			return "PurchaseOrderData";

	 }
	 @GetMapping("/delete/{id}")
	 public String deletePo(@PathVariable Integer id,Model model) {
		  String msg=null;
			if(service.isPurchaseOrderExist(id)) 
			{
				Optional<PurchaseOrder>opt=service.getOnePurchaseOrder(id);
				{ if(opt.get().getPoStatus().contains("OPEN"))
					{
						service.deletePurchaseOrder(id);
						 msg="purchase order'"+id+"' deleted";
					}else
					{
						 msg="purchase order'"+id+"' linked with another entity";
					}
				}
			 
			}
			else
			{
				msg= "PurchaseOrder '"+id+"' not exist!!!";
			}
			 model.addAttribute("message", msg);
			 //fetch other data and display
			 List<PurchaseOrder> list=service.getALLPurchaseOrders();
			 model.addAttribute("list",list);
			 return "PurchaseOrderData";
			

	 }
	 //*****************************************************************//
	 //**              PURCHASE ORDER DETAILS SCREEN                  **//
	 //*****************************************************************//
	 
	 /*
		 * call this method inside other controller method where return page is register
		 * or edit
		 */
		private void addDropDownUiForPartDtls(Model model)
		{
			model.addAttribute("parts",partService.getPartIdAndCode());
		}
	 //1.show dtl page
	 @GetMapping("/dtls/{id}")
	 public String showDtls(@PathVariable Integer id,//po id
			 Model model)
	 {   String page=null;
		 Optional<PurchaseOrder> po=service.getOnePurchaseOrder(id);
		 if(po.isPresent())
		 {
			 model.addAttribute("po", po.get());
			 addDropDownUiForPartDtls(model);//it will show parts dropdown
			 //form backing object for add part+Linked with PO
			 PurchaseDtl purchaseDtl=new PurchaseDtl();
			 purchaseDtl.setPo(po.get());
			 model.addAttribute("purchaseDtl", purchaseDtl);
			 //display all added parts as html table
			 model.addAttribute("dtlList", service.getPurchaseDtlWithPoId(po.get().getId()));
			 page="PurchaseDtls";
		 }else
	 {
		 page ="redirect:../all";
		 
	 }
		 return page;
	 }
  //2.on click add button
	 /*read purchase detail object and save 
	  * in db and redirect to /dtls/id->shwdtl()
	  * */
	 @PostMapping("/addPart")
	 public String addPart(@ModelAttribute PurchaseDtl purchaseDtl)
	 {
		 service.addPartToPO(purchaseDtl);
		 Integer poId=purchaseDtl.getPo().getId();
		 service.updatePurchaseOrderStatus("PICKING",poId );
		 return "redirect:dtls/"+poId;
	 }
	 //3.delete part :on click remove delete one dtl from purchasedtl table and redirect to showdtls page
	 @GetMapping("/removePart")
	 public String deletPart(@RequestParam Integer dtlId,
			                 @RequestParam Integer poId ) {
		 service.deletePurchaseDtl(dtlId);
		 Integer dtlCount=service.getPurchaseDtlCount(poId);
		 if(dtlCount == 0)
		 {
			 service.updatePurchaseOrderStatus("OPEN", poId);
		 }
		 return "redirect:dtls/"+poId;
	 }
	 //4.when click on confirm button chnage status from picking to ordered
	 @GetMapping("/confirmOrder/{id}")
	 public String placeOrder(@PathVariable Integer id) {
		 Integer dtlCount=service.getPurchaseDtlCount(id);
		 if(dtlCount>0) {
			 service.updatePurchaseOrderStatus("ORDERED", id);
		 }
		 return "redirect:../dtls/"+id;//poID
		 
	 }
	//5.when click on genarate invoice button chnage status from ordered to invoiced
		 @GetMapping("/invoiceOrder/{id}")
		 public String invoiceOrder(@PathVariable Integer id) {
			 Integer dtlCount=service.getPurchaseDtlCount(id);
			 if(dtlCount>0) {
				 service.updatePurchaseOrderStatus("INVOICED", id);
			 }
			 return "redirect:../all";//poID
			 
		 }
		 //6.print invoice when clicked create pdf
		 @GetMapping("/printInvoice/{id}")
		 public ModelAndView printOrder(@PathVariable Integer id) {
            ModelAndView m = new ModelAndView();
            m.setView(new VendorInvoicePdf());
            m.addObject("po",service.getOnePurchaseOrder(id).get());
            return m;
		 }
	  
}
