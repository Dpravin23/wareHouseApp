package in.nit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import in.nit.model.DashboardSummary;
import in.nit.model.Part;
import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;
import in.nit.repo.*;

@Service
public class DashboardService {

    private final GrnDtlRepo grnDtlRepo;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private UOMTypeRepository uomTypeRepo;

    @Autowired
    private GrnRepo grnRepository;

    @Autowired
    private ShipmentTypeRepository shipmentRepository;

    @Autowired
    private PartRepository partRepository;
    @Autowired
    private PurchaseDtlRepo pdtRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private OrderMethodRepository orderMethodRepository;

    DashboardService(GrnDtlRepo grnDtlRepo) {
        this.grnDtlRepo = grnDtlRepo;
    }

    public DashboardSummary getSummary() {
        DashboardSummary summary = new DashboardSummary();
         
        summary.setTotalPurchaseOrders(purchaseOrderRepository.count());
        summary.setPendingGRNs(grnRepository.countByStatus("PENDING"));
        summary.setActiveShipments(shipmentRepository.shipmentCountByStatus("Yes"));
        summary.setLowStockParts(pdtRepo.countQuantityLessThan()); // example threshold
        summary.setTotalUsers(userRepository.count());

		
		  Map<String, Integer> poStats = new HashMap<>();
		  List<String> poStatuses =purchaseOrderRepository.allPoStatus();
		  for (String poStatus : poStatuses) {
		  Integer count = purchaseOrderRepository.countPoStatus(poStatus);
		  poStats.put(poStatus, count); } 
		  summary.setPurchaseOrderStats(poStats);
		  
			
			  Map<String, Integer> grnStats = new HashMap<>();
			  List<String> grns =grnDtlRepo.allGrnStatus(); 
			  for (String grnStatus : grns) 
			  { Integer count = grnDtlRepo.countGrnStatus(grnStatus);
			  grnStats.put(grnStatus, count);
			  }
			  summary.setGoodRecieveStats(grnStats);
			 
		  Map<String,Integer> pdtlstat =new HashMap<>();
		 
			    List<PurchaseDtl> usages = pdtRepo.findAll();
			   pdtlstat= usages.stream()
			        .collect(Collectors.groupingBy(
			        		usage->usage.getPart().getPartCode(),
			        		 Collectors.summingInt(PurchaseDtl::getQty)
			        	    ));
			
		 summary.setMostUsedPart(pdtlstat);
		  

        return summary;
    }
}

