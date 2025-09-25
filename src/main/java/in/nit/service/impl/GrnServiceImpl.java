package in.nit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.Grn;
import in.nit.model.GrnDtl;
import in.nit.repo.GrnDtlRepo;
import in.nit.repo.GrnRepo;
import in.nit.service.IGrnService;
@Service
public class GrnServiceImpl implements IGrnService {
   @Autowired
	private GrnRepo repo;
   
   @Autowired
  	private GrnDtlRepo dtlRepo;

@Override
public Integer saveGrn(Grn g) {
	// TODO Auto-generated method stub
	return repo.save(g).getId();
}

@Override
public void updateGrn(Grn g) {
	// TODO Auto-generated method stub
	repo.save(g);
}

@Override
public void deleteGrn(Integer id) {
	// TODO Auto-generated method stub
	repo.deleteById(id);
}

@Override
public Optional<Grn> getOneGrn(Integer id) {
	// TODO Auto-generated method stub
	return repo.findById(id);
}

@Override
public List<Grn> getALLGrns() {
	// TODO Auto-generated method stub
	return repo.findAll();
}

@Override
public boolean isGrnExist(Integer id) {
	// TODO Auto-generated method stub
	return repo.existsById(id);
}

public Integer saveGrnDtl(GrnDtl dtl) {
	// TODO Auto-generated method stub
	return dtlRepo.save(dtl).getId();
}

@Override
public List<GrnDtl> getAllDtlsByGrnId(Integer grnId) {
	// TODO Auto-generated method stub
	return dtlRepo.getAllDtlsByGrnId(grnId);
}

@Override
@Transactional
public void updateStatusByGrnDtlId(String status, Integer Id) {
	dtlRepo.updateStatusByGrnDtlId(status, Id);
	
}
   
   
   
}
