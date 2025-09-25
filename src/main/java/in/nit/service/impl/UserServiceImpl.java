package in.nit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nit.model.User;
import in.nit.repo.UserRepo;
import in.nit.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private UserRepo ur;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Override
	public Integer saveUser(User u) {
		String pwd =u.getPassword();
		String encPwd=encoder.encode(pwd);
		u.setPassword(encPwd);
		u=ur.save(u);
		return u.getId();
	}

	@Override
	public void updateUser(User u) {
		ur.save(u);

	}

	@Override
	public void deleteUser(Integer id) {
		ur.deleteById(id);

	}

	@Override
	public Optional<User> getOneUser(Integer id) {
		Optional<User> opt=ur.findById(id);
		return opt;
	}

	@Override
	public List<User> getALLUsers() {
		List<User> uList=ur.findAll();
		return uList;
	}

	@Override
	public boolean isUserExist(Integer id) {
		boolean flag=ur.existsById(id);
		return flag;
	}

	@Transactional
	public int updateUserStatus(boolean status, Integer id) {
		return ur.updateUserStatus(status, id);
	}
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
{
	Optional<User> opt=ur.findByEmail(username);
	System.out.println("userFound"+opt.get().getUserName());
	if(opt.isEmpty()||!opt.get().isActive())
	throw new UsernameNotFoundException("User Not Found");
	
	User user=opt.get();
	System.out.println("loading"+user.getPassword());
	return new org.springframework.security.core.userdetails.User(
			username, 
			user.getPassword(),
			user.getUserRoles().
			stream().
			map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
	}

@Override
public Optional<User> findByEmail(String email) {
	// TODO Auto-generated method stub
	return ur.findByEmail(email); 
}

@Override
public int inactiveUserCount() {
	
	return ur.inactiveUserCount();
}


}
