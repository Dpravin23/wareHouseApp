package in.nit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.OrderMethod;
import in.nit.repo.OrderMethodRepository;
import in.nit.service.IOrderMethodService;
@Service
public class OrderMethodServiceimpl implements IOrderMethodService {
    
	@Autowired
	private OrderMethodRepository repo;
	
	@Transactional
	public Integer SaveOrderMehod(OrderMethod om) {
		return repo.save(om).getId();
	}

	@Override
	@Transactional
	public void updateOrderMethod(OrderMethod om) {
         		repo.save(om);
	}

	@Override
	@Transactional
	public void deleteOrderMethod(Integer id) {
		repo.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OrderMethod> getOneOrderMethod(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderMethod> getAllOrderMethods() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isOrderMethodExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public List<OrderMethod> getAllOrderMethods(Specification<OrderMethod> spec) {
		return repo.findAll(spec);
		
	}

}
