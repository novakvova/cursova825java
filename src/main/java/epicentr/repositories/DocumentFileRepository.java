package epicentr.repositories;

import epicentr.entities.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {
}
