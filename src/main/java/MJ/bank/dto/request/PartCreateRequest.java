package MJ.bank.dto.request;

import java.time.LocalDate;

public record PartCreateRequest(
    String partName,
    String explanation,
    LocalDate establish
) {

}
