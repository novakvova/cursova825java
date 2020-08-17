package epicentr.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity=DocumentFile.class, mappedBy="document",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentFile> documentFiles = new ArrayList<>();

    private String name;
    @CreationTimestamp
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @UpdateTimestamp
    private Date updatedAt;

    public List<DocumentFile> getDocumentFiles() {
        return documentFiles;
    }

    public void setDocumentFiles(List<DocumentFile> documentFiles) {
        this.documentFiles = documentFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
