package MJ.bank.repository;

import MJ.bank.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  Optional<Employee> findByEmail(String email);

  void deleteById(Long id);

  Optional<Employee> findById(Long id);
}
