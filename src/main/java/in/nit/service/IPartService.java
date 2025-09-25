package in.nit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import in.nit.model.Part;

public interface IPartService {
	Integer SavePart(Part pt);
	void updatePart(Part pt);
	
	void deletePart(Integer id);
	Optional<Part> getOnePart (Integer id);
	
	List<Part> getAllParts();
	boolean isPartExist(Integer id);
	
	public Map<Integer,String> getPartIdAndCode();
	//public Map<String, Integer> getPartUsageSummary();
	
}
