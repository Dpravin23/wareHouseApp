package in.nit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
   
	Optional<User> findByEmail(String email);
	
	@Modifying
	@Query("UPDATE User SET active=:status WHERE id=:id")
	public int updateUserStatus(boolean status,Integer id);
	
	@Query("SELECT count(*) from User where active= false" )
	public int inactiveUserCount();
}
