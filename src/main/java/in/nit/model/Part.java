package in.nit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Part_tab")
public class Part {
	@Id
	@GeneratedValue(generator = "part_gen")
	@SequenceGenerator(name="part_gen",sequenceName = "part_gen_seq")
	@Column(name="part_id_col")
	private Integer id;
	@Column(name="part_code_col")
	private String partCode;
	@Column(name="part_width_col")
	private String partWidth;
	@Column(name="part_len_col")
	private String partLen;
	@Column(name="part_hgh_col")
	private String partHgh;
	@Column(name="part_cost_col")
	private Double partCost;
	@Column(name="basecurr__col")
	private String baseCurr;
	@Column(name="part_desc_col")
	private String partDesc;
	
	@ManyToOne
	@JoinColumn(name="uom_id_col_fk")
    private UomType uomType;//HAS-A 
	@ManyToOne
	private PurchaseOrder purchaseorder;
}
