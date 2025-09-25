package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nit.model.Document;

public interface DocumentRepository extends JpaRepository<Document,Integer>{
	@Query("select dc.docId,dc.docName from Document dc")
	List<Object[]> findIdAndName();
	
	@Query("select count(dc1.docId) from Document dc1 where dc1.docId=:docId")
	public Integer getdocIdCount(Integer docId);
	}

