package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {
  @Query("select wt.id,wt.userCode from WhUserType wt where wt.userType=:userType")
  public List<Object[]> getwhUseridAndType(String userType);
}
