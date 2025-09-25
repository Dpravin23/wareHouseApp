package in.nit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.Part;
import in.nit.repo.PartRepository;
import in.nit.service.IPartService;

@Service
public class PartServiceImpl implements IPartService {
	@Autowired
	private PartRepository repo;

	@Transactional
	public Integer SavePart(Part pt) {
		
		return repo.save(pt).getId();
	}

	@Transactional
	public void updatePart(Part pt) {
		repo.save(pt);

	}

	@Transactional
	public void deletePart(Integer id) {
		repo.deleteById(id);

	}

	@Transactional(readOnly = true)
	public Optional<Part> getOnePart(Integer id) {
		
		return repo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Part> getAllParts() {
		
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	public boolean isPartExist(Integer id) {
		
		return repo.existsById(id);
	}

	@Override
	public Map<Integer, String> getPartIdAndCode() {
		return repo.getPartIdAndCode()
		.stream()
		.collect(Collectors.toMap(ob->Integer.valueOf(ob[0].toString()), ob->ob[1].toString()));
	}
	/*
	 * public Map<String, Integer> getPartUsageSummary() { List<Part> usages =
	 * repo.findAll(); return usages.stream() .collect(Collectors.groupingBy(
	 * Part::getPartCode, Collectors.summingInt(Part::getId) )); }
	 */
}
