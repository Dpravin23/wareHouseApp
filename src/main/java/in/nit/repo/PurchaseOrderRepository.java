package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
	@Modifying     //non-select operation+query
	@Query("UPDATE PurchaseOrder SET poStatus=:poStatus WHERE id=:id")
	public void updatePurchaseOrderStatus(String poStatus,Integer id);
    @Query("SELECT PO.id,PO.purchaseCode from PurchaseOrder PO where PO.poStatus=:status")
	public List<Object[]> getPoIdAndCodeByStatus(String status);
	
	@Query("SELECT poStatus from PurchaseOrder") 
	public List<String> allPoStatus();
	
	@Query("SELECT count(poStatus) from PurchaseOrder where poStatus=:poStatus") 
	public Integer countPoStatus(String poStatus);
	 
	
}
