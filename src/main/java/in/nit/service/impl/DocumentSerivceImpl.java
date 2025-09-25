package in.nit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nit.model.Document;
import in.nit.repo.DocumentRepository;
import in.nit.service.IDocumentService;

@Service
public class DocumentSerivceImpl implements IDocumentService {
    @Autowired
    private DocumentRepository repo;
    @Transactional
    public void saveDocumnt(Document doc)
    {
        repo.save(doc);
    }
    @Transactional(readOnly =true)
   	public List<Object[]> findIdAndName() {
   		return repo.findIdAndName();
   	}
    @Transactional(readOnly =true)
	public Optional<Document> findDocument(Integer id) {
		
		return repo.findById(id);
	}
    @Transactional(readOnly = true)
	public boolean isDocIdExist(Integer docId) {
		int count=repo.getdocIdCount(docId);
		System.out.println("count"+count);
		boolean flag=(count>0  ? true:false);
		System.out.println("flag :"+flag);
		return flag;
	}
   
}

