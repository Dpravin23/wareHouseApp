package in.nit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.Grn;

public interface GrnRepo extends JpaRepository<Grn, Integer> {
    @Query("SELECT count(*) from PurchaseOrder po WHERE po.poStatus='RECEIVED'"  )
	public long countByStatus(String status);
}
