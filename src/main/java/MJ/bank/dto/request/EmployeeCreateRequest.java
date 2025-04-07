package MJ.bank.dto.request;

import MJ.bank.entity.Rank;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateRequest {
  String email;
  String name;
  String partName;
  Rank rank;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  LocalDate dateOfJoining;
  String memo;

}
