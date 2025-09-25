package in.nit.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_tab")
public class User {
	@Id
	@GeneratedValue
	@Column(name="user_id_col")
	private Integer id;
	@Column(name="user_name_col")
	private String userName;
	@Column(name="user_email_col")
	private String email;
	@Column(name="user_passwrod_col")
	private String password;
	@Column(name="user_active_col")
	private boolean active;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="user_roleTab",joinColumns = @JoinColumn(name="id_join_col"))
	@Column(name="user_role_col")
	private Set<String> userRoles;
	@Column(name="verified")
	private Boolean verified;
	@Column(name="otp")
	private String otp;
	

}
