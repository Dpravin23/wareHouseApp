package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import in.nit.model.UomType;
import in.nit.repo.UOMTypeRepository;
import in.nit.service.IUomTypeService;

@Service
public class UomTypeServiceImpl implements IUomTypeService{
	@Autowired
	private UOMTypeRepository repo; 
	
	@Transactional
	public Integer saveUomType(UomType ut) {
		return repo.save(ut).getId();
	}

	@Transactional
	public void updateUomType(UomType ut) {
		repo.save(ut);
		
	}

	@Transactional
	public void deleteUomType(Integer id) {
		repo.deleteById(id);
		
	}

	@Transactional(readOnly=true)
	public Optional<UomType> getOneUomType(Integer id) {
		Optional<UomType> opt=repo.findById(id);
		return opt;
	}

	@Transactional(readOnly=true)
	public  List<UomType> getALLUomTypes() {
		List<UomType> list=repo.findAll();
		return list;
	}

	@Transactional(readOnly=true)
	public boolean isUomTypeExist(Integer id) {
		boolean flag=repo.existsById(id);
		return flag;
	}

	@Transactional(readOnly=true)
	public Map<Integer, String> getUomIDModel() {
		//using stream api
		Map<Integer, String> map=repo.getUomIdAndModel()
		.stream()
		.filter(array->array!=null)
		.collect(Collectors.toMap(
				array->Integer.valueOf(array[0].toString()),
				array->array[1].toString()));
		//using for loop
		/*
		 * Map<Integer, String> map=new LinkedHashMap<>(); List<Object[]>
		 * list=repo.getUomIdAndModel(); for(Object[] ob:list) {
		 * map.put(Integer.valueOf(ob[0].toString()),ob[1].toString()); }
		 */
		return map;
	}

	@Override
	public Page<UomType> getAllUoms(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(pageable);
	}

}