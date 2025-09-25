package in.nit.model;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name="grn_tab")
public class Grn {
    @GeneratedValue
    @Id
    @Column(name="grn_id_col")
	private Integer id;
    
    @Column(name="grn_code_col")
	private String grnCode;
    
    @Column(name="grn_type_col")
	private String grnType;
    
    @Column(name="grn_desc_col")
	private String description;
    @ManyToOne
    @JoinColumn(name="po_id_fk",unique=true)//one -to-one
    private PurchaseOrder po;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="grndtl_id_fk",unique=true)//one -to-one
    private GrnDtl grndt;
    @OneToMany(mappedBy="grn",fetch=FetchType.EAGER)
    private List<GrnDtl> dtls;
	
	
}
