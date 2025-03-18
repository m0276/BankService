package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Backup {

  @Id
  Long id;
  String worker;
  LocalDateTime startTime;
  LocalDateTime endTime;
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  BackupStatus backupStatus;


  public Backup(String worker, LocalDateTime startTime, LocalDateTime endTime,
      BackupStatus backupStatus) {
    this.worker = worker;
    this.startTime = startTime;
    this.endTime = endTime;
    this.backupStatus = backupStatus;
  }

  public Backup() {

  }
}
