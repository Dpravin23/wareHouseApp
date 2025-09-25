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
@Table(name="wh_user_type_tab")
public class WhUserType {
	@Id
	@GeneratedValue(generator = "whusertype_seq")
	@SequenceGenerator(name="whusertype",sequenceName = "whusertype_seq")
	
	@Column(name="wh_usr_id_col")
	private Integer id;
	@Column(name="wh_usr_type_col")
	private String userType;
	@Column(name="wh_usr_code_col")
	private String userCode;
	@Column(name="wh_usr_for_col")
	private String userFor;
	@Column(name="wh_usr_mail_col")
	private String userMail;
	@Column(name="wh_usr_contact_col")
	private String userContact;
	@Column(name="wh_usr_id_type_col")
	private String userIdType;
	@Column(name="wh_usr_other_col")
	private String ifOther;
	@Column(name="wh_usr_id_number_col")
	private String idNumber;
 
	
	
}
