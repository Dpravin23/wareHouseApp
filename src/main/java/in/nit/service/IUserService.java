package in.nit.service;

import java.util.List;
import java.util.Optional;

import in.nit.model.User;

public interface IUserService {
	
	Integer saveUser(User u);
	void updateUser(User u);
	void deleteUser(Integer id);
	
	Optional<User> getOneUser(Integer id);

	List<User> getALLUsers(); 
	boolean isUserExist(Integer id);
	Optional<User> findByEmail(String email);
	int updateUserStatus(boolean status,Integer id);
	int inactiveUserCount();
	

}
