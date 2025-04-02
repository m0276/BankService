package MJ.bank.dto.response;

import MJ.bank.dto.EmployeeDto;
import java.util.List;

public record CursorPageResponseEmployeeDto(
    List<EmployeeDto> content,
    String newCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext
) {

}
