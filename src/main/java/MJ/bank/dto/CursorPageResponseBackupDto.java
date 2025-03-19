package MJ.bank.dto;

import java.util.List;

public record CursorPageResponseBackupDto(
    List<BackupDto> content,
    String newCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext
) {

}
