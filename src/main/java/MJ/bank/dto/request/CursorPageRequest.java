package MJ.bank.dto.request;

import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Rank;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursorPageRequest {
  String name;
  String email;
  String partName;
  Rank rank;
  UUID employeeId;
  String description;
  EmployeeStatus status;
  String sortType;
  Integer size;
  String sortDirection;
  Integer pageNo;
  Long cursorId;
}