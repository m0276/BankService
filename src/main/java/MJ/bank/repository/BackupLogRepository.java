package MJ.bank.repository;

import MJ.bank.entity.BackupLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupLogRepository extends JpaRepository<BackupLog, Long>,BackupLogRepositoryCustom {

  Boolean existsByIdLessThan(Long id);
  Page<BackupLog> findAllById(Long id, Pageable page);
}
