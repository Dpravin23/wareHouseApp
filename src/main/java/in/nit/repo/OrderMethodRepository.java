package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer>,JpaSpecificationExecutor<OrderMethod> {
	/*
	 * @Query("select orderType from OrderMethod") public List<String>
	 * findAllmethodTypes();
	 */
    @Query("SELECT DISTINCT om.orderType FROM OrderMethod om")
    public List<String> findAllMethodTypes();
    
    @Query("SELECT count(orderType) from OrderMethod where orderType=:orderType")
    Integer countByMethodType(String orderType);
}
