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
@Table(name="Uom_type_tab")
public class UomType {
     @Id
	 @GeneratedValue
	 @Column(name="Uom_id_col")
	 private Integer id;
	 @Column(name="Uom_type_col")
	 private String  uomMode;
	 @Column(name="Uom_model_col")
	 private String  uomModel;
	 @Column(name="Uom_desc_col")
	 private String  description;

		
}
