package in.nit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="purchase_dtl_tab")
public class PurchaseDtl {
	
	@Id
	@GeneratedValue
	@Column(name="pur_dtl_id_col")
	private Integer id;
	
	@Transient
	private Integer slno;
	@Column(name="pur_dtl_qty_col")
	private Integer qty;
	@ManyToOne
	@JoinColumn(name="part_id_fk")
	private Part part;// has-a
	
	/* every purchsedtl must be linked 
	 * with its purchseOrder parent(screen1) 
	 * */
	@ManyToOne
	@JoinColumn(name="po_id_fk")
	private PurchaseOrder po;//has -a

}
