package MJ.bank.dto.response;

import MJ.bank.dto.UpdateLogDto;
import java.util.List;

public record CursorPageResponseUpdateLogDto(
    List<UpdateLogDto> content,
    String newCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext
) {

}
