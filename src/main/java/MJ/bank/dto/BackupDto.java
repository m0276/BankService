package MJ.bank.dto;

import MJ.bank.entity.BackupStatus;
import java.time.LocalDateTime;

public record BackupDto(
    Long id,
    String worker,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    BackupStatus status,
    Long fileId
) {

}
