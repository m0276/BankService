package MJ.bank.repository;


import MJ.bank.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeRepositoryCustom {
  Page<Employee> searchAll(Page page);
}
