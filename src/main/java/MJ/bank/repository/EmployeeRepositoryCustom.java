package MJ.bank.repository;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeRepositoryCustom {
  Slice<Employee> searchAll(CursorPageRequest request, Pageable page);
}