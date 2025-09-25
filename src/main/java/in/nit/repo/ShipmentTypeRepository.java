package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.ShipmentType;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {
	@Query("select count(st.shipmentCode) from ShipmentType st where st.shipmentCode=:shipmentcode")
	public Integer getshipmentCodeCount(String shipmentcode);
	
	
	@Query("select count(st.shipmentCode) from ShipmentType st where st.shipmentCode=:shipmentcode and st.id!=:id")
	public Integer getshipmentCodeCountForEdit(String shipmentcode,Integer id);
	
	@Query("select st.id ,st.shipmentCode From ShipmentType st Where st.enableShipment='Yes'")
	public List<Object[]> getshipmentIdAndCode();
	@Query("select count(*)  from ShipmentType WHERE enableShipment='Yes'" )
	public long shipmentCountByStatus(String Status);
	
   
}
