package MJ.bank.dto;

import java.time.LocalDate;

public record PartDto(
    String partName,
    String explanation,
    LocalDate establish
) {

}
