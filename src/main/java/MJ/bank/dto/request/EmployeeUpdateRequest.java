package MJ.bank.dto.request;

import MJ.bank.entity.Rank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateRequest{
  String email;
  String name;
  String partName;
  Rank rank;
  LocalDate dateOfJoining;
  String memo;

}
