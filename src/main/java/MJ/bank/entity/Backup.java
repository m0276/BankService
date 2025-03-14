package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Backup {

  @Id
  Long id;
  String worker;
  LocalDateTime startTime;
  LocalDateTime endTime;
  BackupStatus backupStatus;
  Boolean checkNeedBackup;

}
