package MJ.bank.dto;

import MJ.bank.entity.UpdateType;
import java.time.LocalDateTime;

public record UpdateLogDto(
    Long id,
    UpdateType updateType,
    String employeeNumber, // employeeId(Long) to string
    String memo,
    String ip,
    LocalDateTime updateTime
) {

}
