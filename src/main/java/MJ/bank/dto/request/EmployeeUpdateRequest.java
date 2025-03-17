package MJ.bank.dto.request;

import MJ.bank.entity.Rank;
import java.time.LocalDate;

public record EmployeeUpdateRequest(
    String email,
    String name,
    String partName,
    Rank rank,
    LocalDate dateOfJoining,
    String memo
) {


}
