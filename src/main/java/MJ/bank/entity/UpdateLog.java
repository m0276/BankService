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
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "update_log")
@Getter
@Setter
public class UpdateLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "update_type")
  UpdateType updateType;

  @Column(name = "employee_id")
  Long employeeId;

  @Column(name = "property_name")
  String propertyName;

  @Column(name = "prev_content")
  String prevContent;

  @Column(name = "curr_content")
  String currContent;

  @Column
  String memo;

  @Column
  String ip;

  @Column(name = "update_time")
  LocalDateTime updateTime;

  public UpdateLog(UpdateType updateType, Long employeeId, String propertyName, String prevContent,
      String currContent, String memo, String ip, LocalDateTime updateTime) {
    this.updateType = updateType;
    this.employeeId = employeeId;
    this.propertyName = propertyName;
    this.prevContent = prevContent;
    this.currContent = currContent;
    this.memo = memo;
    this.ip = ip;
    this.updateTime = updateTime;
  }

  public UpdateLog(){

  }
}
