package MJ.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "backup_log")
public class BackupLog {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column
  String worker;

  @Column(name = "start_time")
  LocalDateTime startTime;

  @Column(name = "end_time")
  LocalDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "backup_status")
  BackupStatus backupStatus;

  @Column(name = "file_number")
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
