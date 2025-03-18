package MJ.bank.mapper;


import MJ.bank.dto.BackupDto;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.BackupStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BackupLogMapper {

  @Mapping(source = "fileNumber", target = "fileId")
  @Mapping(source = "backupStatus", target = "status")
  @Mapping(source = "endTime", target = "endedAt")
  @Mapping(source = "startTime", target = "startedAt")
  public BackupDto toDto(BackupLog backupLog);
}

