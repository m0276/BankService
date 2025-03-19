package MJ.bank.mapper;


import MJ.bank.dto.BackupDto;
import MJ.bank.entity.BackupLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BackupLogMapper {

  @Mapping(source = "fileNumber", target = "fileId")
  @Mapping(source = "backupStatus", target = "status")
  @Mapping(source = "endTime", target = "endedAt")
  @Mapping(source = "startTime", target = "startedAt")
  BackupDto toDto(BackupLog backupLog);
}

