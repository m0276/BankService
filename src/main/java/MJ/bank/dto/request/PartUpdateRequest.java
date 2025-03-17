package MJ.bank.dto.request;

import java.time.LocalDate;

public record PartUpdateRequest(
    String partName,
    String explanation,
    LocalDate establish,
    String newPartName
) {

}
