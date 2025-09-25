package in.nit.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="document_tab")
public class Document
{
    @Id 
    @GeneratedValue(generator = "doc_gen")
    @SequenceGenerator(name="om_gen",sequenceName = "doc_gen_seq")
    @Column(name="doc_id_col")
    private Integer docId;
    @Column(name="doc_name_col")
    private String docName;
    
    @Column(name="doc_data_col", columnDefinition = "BYTEA")
    @Lob
    private byte[] docData;
}

