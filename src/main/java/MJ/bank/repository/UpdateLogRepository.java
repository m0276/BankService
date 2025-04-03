package MJ.bank.repository;


import MJ.bank.entity.UpdateLog;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateLogRepository extends JpaRepository<UpdateLog, Long>,UpdateLogRepositoryCustom {

  Page<UpdateLog> findAll(Pageable page);

  Page<UpdateLog> findAllById(Long id, Pageable page);

  Boolean existsByIdLessThan(Long id);
}
