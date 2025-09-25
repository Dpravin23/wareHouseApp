package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.ShipmentType;

public interface IShipmentTypeService {
	
	Integer saveShipmentType(ShipmentType st);
	void updateShipmentType(ShipmentType s);
	void deleteShipmentType(Integer id);
	
	Optional<ShipmentType> getOneShipmentType(Integer id);
	List<ShipmentType> getALLShipmentTypes();
	boolean isShipmentTypeExist(Integer id);
    boolean isShipmentTypeCodeExist(String shipmentcode);
    boolean isShipmentTypeCodeExistForEdit(String shipmentcode,Integer id);

    
    public Map<Integer,String> getshipmentIdAndCode();
}
