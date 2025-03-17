package MJ.bank.dto.request;

import MJ.bank.entity.Rank;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCreateRequest(
    String email,
    String name,
    String partName,
    Rank rank,
    LocalDate dateOfJoining,
    Boolean checkProfile,
    String url
) {


}
