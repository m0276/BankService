package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
public class BackupLog {

  @Id
  Long id;
  String worker;
  LocalDateTime startTime;
  LocalDateTime endTime;
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  BackupStatus backupStatus;
  Long fileNumber;


  public BackupLog(String worker, LocalDateTime startTime, LocalDateTime endTime,
      BackupStatus backupStatus, Long fileNumber) {
    this.worker = worker;
    this.startTime = startTime;
    this.endTime = endTime;
    this.backupStatus = backupStatus;
    this.fileNumber = fileNumber;
  }

  public BackupLog() {

  }
}
