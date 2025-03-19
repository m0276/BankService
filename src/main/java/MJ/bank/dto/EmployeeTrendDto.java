package MJ.bank.dto;

import java.time.LocalDate;

public record EmployeeTrendDto(
    LocalDate date,
    Integer count,
    Integer change,
    Double changeRate
) {

}
