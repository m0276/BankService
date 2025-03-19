package MJ.bank.repository;


import MJ.bank.entity.UpdateLog;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateLogRepository extends JpaRepository<UpdateLog, Long> {

  List<UpdateLog> findAllByOrderByIpAsc(Pageable page);

  List<UpdateLog> findByIdLessThanOrderByIpAsc(Long id, Pageable page);

  Boolean existsByIdLessThan(Long id);
}
