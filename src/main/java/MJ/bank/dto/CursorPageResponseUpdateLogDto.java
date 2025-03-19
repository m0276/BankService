package MJ.bank.dto;

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
