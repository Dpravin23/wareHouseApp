package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;



public interface IPurchaseOrderService {
	
	Integer savePurchaseOrder(PurchaseOrder po);
	void updatePurchaseOrder(PurchaseOrder po);
	void deletePurchaseOrder(Integer id);
	
	Optional<PurchaseOrder> getOnePurchaseOrder(Integer id);
	List<PurchaseOrder> getALLPurchaseOrders();
	boolean isPurchaseOrderExist(Integer id);
    boolean isPurchaseOrderCodeExist(String purchaseCode);
    
    //screen 2 oprs
   Integer addPartToPO(PurchaseDtl dtl);
   List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId);
   void deletePurchaseDtl(Integer id);
   void updatePurchaseOrderStatus(String poStatus,Integer id);
   Integer getPurchaseDtlCount(Integer purchaseId);
   Map<Integer,String> getPoIdAndCodeByStatus(String status);
   Map<Integer,Integer> getMostUsedPartIdandCount();
 
}
