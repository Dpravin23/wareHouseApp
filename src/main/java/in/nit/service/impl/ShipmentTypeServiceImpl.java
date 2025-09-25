package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.ShipmentType;
import in.nit.repo.ShipmentTypeRepository;
import in.nit.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {
	@Autowired
	private ShipmentTypeRepository repo;

	@Transactional
	public Integer saveShipmentType(ShipmentType st) {
		return repo.save(st).getId();
	}

	@Transactional
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);

	}

	@Transactional
	public void deleteShipmentType(Integer id) {
		repo.deleteById(id);

	}

	@Transactional(readOnly = true)
	public Optional<ShipmentType> getOneShipmentType(Integer id) {
		Optional<ShipmentType> opt=repo.findById(id);
		/*
		 * if(opt.isPresent()) { return opt.get(); }
		 */
		return opt;
	}

	@Transactional(readOnly = true)
	public List<ShipmentType> getALLShipmentTypes() {
		List<ShipmentType> list=repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeExist(Integer id) {
		boolean flag = repo.existsById(id);
		return flag;
	}

	@Transactional(readOnly = true)
	public boolean isShipmentTypeCodeExist(String shipmentcode) {
		int count=repo.getshipmentCodeCount(shipmentcode);
		boolean flag =(count>0 ? true:false);
		System.out.println("flag:"+flag);
		return flag;
	}
	
	@Transactional(readOnly = true)
	public boolean isShipmentTypeCodeExistForEdit(String shipmentcode, Integer id) {
		int count=repo.getshipmentCodeCountForEdit(shipmentcode,id);
		boolean flag =(count>0 ? true:false);
		System.out.println("flag:"+flag);
		System.out.println("ID="+id);
		return flag;
	}


	@Override
	public Map<Integer, String> getshipmentIdAndCode() {
		return repo.getshipmentIdAndCode().stream()
				.collect(Collectors.toMap(ob -> Integer.valueOf(ob[0].toString()), ob -> ob[1].toString()));
	}

	

}
