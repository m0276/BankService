package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "update_log")
public class UpdateLog {

  @Id
  Long id;

  UpdateType updateType;
  Long employeeId;
  Object prevContent;
  Object currContent;
  String memo;
  String ip;
  LocalDateTime updateTime;

  public UpdateLog(UpdateType updateType, Long employeeId, Object prevContent, Object currContent,
      String memo, String ip, LocalDateTime updateTime) {
    this.updateType = updateType;
    this.employeeId = employeeId;
    this.prevContent = prevContent;
    this.currContent = currContent;
    this.memo = memo;
    this.ip = ip;
    this.updateTime = updateTime;
  }

  public UpdateLog(){

  }
}
