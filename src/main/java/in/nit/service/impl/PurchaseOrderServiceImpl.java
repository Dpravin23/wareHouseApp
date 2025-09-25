package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;
import in.nit.repo.PurchaseDtlRepo;
import in.nit.repo.PurchaseOrderRepository;
import in.nit.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
     @Autowired
	private PurchaseOrderRepository repo;
     @Autowired
     private PurchaseDtlRepo dtlrepo;
     
	public Integer savePurchaseOrder(PurchaseOrder po) {
		return repo.save(po).getId();
	}

	
	public void updatePurchaseOrder(PurchaseOrder po) {
		repo.save(po);

	}

	
	public void deletePurchaseOrder(Integer id) {
		repo.deleteById(id);

	}

	@Transactional(readOnly = true)
	public Optional<PurchaseOrder> getOnePurchaseOrder(Integer id) {
		Optional<PurchaseOrder> opt=repo.findById(id);
		return opt;
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrder> getALLPurchaseOrders() {
		List<PurchaseOrder> list=repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	public boolean isPurchaseOrderExist(Integer id) {
		boolean flag=repo.existsById(id);
		return flag;
	}


	@Override
	public boolean isPurchaseOrderCodeExist(String purchaseCode) {
		// TODO Auto-generated method stub
		return false;
	}

 ///screen 2 oprns
	@Override
	@Transactional
	public Integer addPartToPO(PurchaseDtl dtl) {
		// TODO Auto-generated method stub
		return dtlrepo.save(dtl).getId();
	}


	@Override
	public List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId) {
		// TODO Auto-generated method stub
		return dtlrepo.getPurchaseDtlWithPoId(purchaseId);
	}


	@Override
	@Transactional
	public void deletePurchaseDtl(Integer id) {
		dtlrepo.deleteById(id);
		
	}


	@Override
	@Transactional
	public void updatePurchaseOrderStatus(String poStatus, Integer id) {
		repo.updatePurchaseOrderStatus(poStatus, id);
		
	}


	@Override
	@Transactional
	public Integer getPurchaseDtlCount(Integer purchaseId) {
		
		return dtlrepo.getPurchaseDtlCount(purchaseId);
	}


	
	public Map<Integer, String> getPoIdAndCodeByStatus(String status) {
		return repo.getPoIdAndCodeByStatus(status)
		.stream()
		.collect(Collectors.toMap(ob -> Integer.valueOf(ob[0].toString()), ob -> ob[1].toString()));
				
		
	}
	public Map<Integer, Integer> getMostUsedPartIdandCount() {
		return dtlrepo.getMostUsedPartIdandCount();
				/*.stream()
				.collect(Collectors.toMap(
						ob -> ((Number) ob[0]).intValue(),
						ob -> ((Number) ob[1]).intValue()
						));
		*/
		
				
		
	}

	

	/*
	 * @Transactional(readOnly = true) public boolean
	 * isPurchaseOrderCodeExist(String purchaseCode) { int
	 * count=repo.getshipmentCodeCount(purchaseCode); boolean flag =(count>1 ?
	 * true:false); System.out.println("flag:"+flag); return flag; }
	 */

}
