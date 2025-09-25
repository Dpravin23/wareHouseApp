package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.WhUserType;
import in.nit.repo.WhUserTypeRepository;
import in.nit.service.IWhUserTypeService;

@Service
public class WhUserTypeImpl implements IWhUserTypeService {
	@Autowired
	private WhUserTypeRepository repo;
	

	@Transactional
	public Integer saveWhUserType(WhUserType wt) {
		// TODO Auto-generated method stub
		return repo.save(wt).getId();
	}

	@Transactional
	public void updateWhUserType(WhUserType wt) {
		repo.save(wt);
         
	}

	@Transactional
	public void deleteWhUserType(Integer id) {
		repo.deleteById(id);

	}

	@Transactional
	public Optional<WhUserType> getOneWhUserType(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Transactional
	public List<WhUserType> getALLWhUserTypes() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Transactional
	public boolean isWhUserTypeExist(Integer id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

	@Transactional
	public Map<Integer, String> getwhUseridAndType(String userType) {
	return	repo.getwhUseridAndType(userType).stream()
		.collect(Collectors.toMap(ob -> Integer.valueOf(ob[0].toString()), ob -> ob[1].toString()));
	}

}
