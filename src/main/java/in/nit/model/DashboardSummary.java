package in.nit.model;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class DashboardSummary {
	@GeneratedValue
	
    private long totalPurchaseOrders;
    private long pendingGRNs;
    private long activeShipments;
    private long lowStockParts;
    private long totalUsers;
    private Map<String,Integer> purchaseOrderStats;
    private Map<String,Integer> goodRecieveStats;
    private Map<String,Integer> mostUsedPart;
    
    // Getters and setters
}