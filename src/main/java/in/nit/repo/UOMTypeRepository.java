package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.UomType;

public interface UOMTypeRepository extends JpaRepository<UomType, Integer> {
	@Query("SELECT id,uomModel from UomType")
	List<Object[]> getUomIdAndModel();

	@Query("SELECT  DISTINCT uomMode from UomType")
	 List<String> getallUomMode();
	@Query("SELECT count(*) from UomType where uomMode=:uomMode")
	Integer getCountOfUomMode(String uomMode);

}
