package in.nit.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="grn_dtl_tab")
public class GrnDtl {
	@Id
	@Column(name="grn_dtl_id_col")
	@GeneratedValue
	private Integer id;
	@Column(name="grn_dtl_part_code_col")
	private String partCode;
	@Column(name="grn_dtl_cost_col")
	private Double baseCost;
	@Column(name="grn_dtl_qty_col")
	private Integer qty;
	@Column(name="grn_dtl_linevalue_col")
	private Double lineValue;
	@Column(name="grn_dtl_grnStatus_col")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="grn_id_fk")
	private Grn grn;

}
