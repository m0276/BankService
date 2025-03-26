package MJ.bank.repository;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface EmployeeRepositoryCustom {
  Slice<?> searchAll(CursorPageRequest request, Pageable page);
}