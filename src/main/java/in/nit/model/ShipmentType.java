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
@Table(name="Shipment_type_tab")
public class ShipmentType {
	 
	 @Id
	 @GeneratedValue
	 @Column(name="Ship_id_col")
	 private Integer id;
	 @Column(name="Ship_mode_col")
	 private String shipmentMode;
	 @Column(name="Ship_code_col")
	 private String shipmentCode;
	 @Column(name="enable_ship_col")
	 private String enableShipment;
	 @Column(name="Ship_grade_col")
	 private String shipmentGrade;
	 @Column(name="desc_col")
	 private String description;
	 
	 
	
	
}
