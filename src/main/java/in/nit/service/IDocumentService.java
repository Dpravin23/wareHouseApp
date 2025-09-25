package in.nit.service;

import java.util.List;
import java.util.Optional;

import in.nit.model.Document;

public interface IDocumentService {
	public void saveDocumnt(Document doc);
	public List<Object[]> findIdAndName();
	public Optional<Document> findDocument(Integer id);
	boolean isDocIdExist(Integer docId);
}
