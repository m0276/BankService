package MJ.bank.dto.response;

import MJ.bank.dto.PartDto;
import java.util.List;

public record CursorPageResponsePartDto(
    List<PartDto> content,
    String newCursor,
    String nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext
) {
}
