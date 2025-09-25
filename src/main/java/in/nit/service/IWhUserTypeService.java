package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.WhUserType;


public interface IWhUserTypeService {
	
	Integer saveWhUserType(WhUserType wt);
	void updateWhUserType(WhUserType wt);
	void deleteWhUserType(Integer id);
	
	Optional<WhUserType> getOneWhUserType(Integer id);
	List<WhUserType> getALLWhUserTypes();
	boolean isWhUserTypeExist(Integer id);
	
	public Map<Integer,String> getwhUseridAndType(String userType);

}
