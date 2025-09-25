package in.nit.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.PurchaseDtl;

public interface PurchaseDtlRepo extends JpaRepository<PurchaseDtl, Integer> {
	
	@Query("SELECT PDTL FROM PurchaseDtl PDTL INNER JOIN PDTL.po as PO where PO.id=:purchaseId")
	public List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId);
	
	@Query("SELECT count(PDTL.id) FROM PurchaseDtl PDTL INNER JOIN PDTL.po as PO where PO.id=:purchaseId")
	public Integer getPurchaseDtlCount(Integer purchaseId);
	@Query("SELECT count(*) from PurchaseDtl where qty<50")
	public long countQuantityLessThan();
	@Query("SELECT pdtl.part.id, COUNT(*) AS usage_count FROM PurchaseDtl pdtl GROUP BY pdtl.part.id ORDER BY usage_count DESC" )
	public Map<Integer,Integer> getMostUsedPartIdandCount();

}
