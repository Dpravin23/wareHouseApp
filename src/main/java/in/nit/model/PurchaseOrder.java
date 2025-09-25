package in.nit.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

@Data 
@Entity
@Table(name="Purchase_Order_tab")
public class PurchaseOrder {
	
		 @Id
		 @GeneratedValue
		 @Column(name="po_id_col")
		 private Integer id;
		 @Column(name="po_code_col")
		 private String purchaseCode; 
		 @Column(name="ref_num_col")
		 private String refNumber;
		 @Column(name="quality_check_col")
		 private String qualityCheck;
		 @Column(name="poStatus_col")
		 private String poStatus;
		 @Column(name="desc_col")
		 private String description;
		@ManyToOne
		@JoinColumn(name="shipment_type_id_fk")
		private ShipmentType shipmentType;//has-a relation
		@ManyToOne
		@JoinColumn(name="whuser_id_fk")
		private WhUserType whUserType;//has-a relation
		
		@OneToMany(mappedBy = "po",fetch=FetchType.EAGER)
		private List<PurchaseDtl> dtls;
		
		@OneToMany(mappedBy = "purchaseorder",fetch=FetchType.EAGER) 
		private List<Part> part;
		
	

}
