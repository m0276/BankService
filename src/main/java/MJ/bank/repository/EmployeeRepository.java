package MJ.bank.repository;

import MJ.bank.entity.Employee;
import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>,EmployeeRepositoryCustom {
  Optional<Employee> findByEmail(String email);

  void deleteById(Long id);

  Optional<Employee> findById(Long id);

  List<Employee> findAllById(Long id);

  Boolean existsByIdLessThan(Long id);
}
