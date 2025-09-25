package in.nit.service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nit.model.UomType;

public interface IUomTypeService {

	Integer saveUomType(UomType ut);
	void updateUomType(UomType u);
	void deleteUomType(Integer id);
	
	Optional<UomType> getOneUomType(Integer id);

	 List<UomType> getALLUomTypes(); 
	boolean isUomTypeExist(Integer id);
	
	//List of object array into map Map<Integer,String>
	Map<Integer,String> getUomIDModel();
	
	Page<UomType> getAllUoms(Pageable pageable);
	
}
