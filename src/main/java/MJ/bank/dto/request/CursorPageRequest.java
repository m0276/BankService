package MJ.bank.dto.request;

import MJ.bank.entity.BackupStatus;
import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Rank;
import MJ.bank.entity.UpdateType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CursorPageRequest {
  //employee
  String name;
  String email;
  String partName;
  Rank rank;
  UUID employeeId;
  EmployeeStatus status;

  //part
  String description;
  LocalDateTime establish;

  //backup
  String worker;
  LocalDateTime startTime;
  LocalDateTime endTime;
  BackupStatus backupStatus;
  Long fileNumber;

  //update
  UpdateType updateType;
  String propertyName;
  String prevContent;
  String currContent;
  String memo;
  String ip;
  LocalDateTime updateTime;

  //common
  String sortType;
  Integer size;
  String sortDirection;
  Integer pageNo;
  Long cursorId;
}