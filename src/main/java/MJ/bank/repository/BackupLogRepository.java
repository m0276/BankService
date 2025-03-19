package MJ.bank.repository;

import MJ.bank.dto.BackupDto;
import MJ.bank.entity.BackupLog;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupLogRepository extends JpaRepository<BackupLog, Long> {

  Boolean existsByIdLessThan(Long id);

  List<BackupLog> findAllBy(Pageable page);

  List<BackupLog> findByIdOrderBy(Long id, Pageable pageable);
}
