package MJ.bank.dto;

import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Part;
import MJ.bank.entity.Profile;
import MJ.bank.entity.Rank;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDto(
    Long id,
    String email,
    String name,
    Part part,
    Rank rank,
    UUID  employeeNumber,
    EmployeeStatus status,
    LocalDate dateOfJoining,
    Profile profile
) {

}
