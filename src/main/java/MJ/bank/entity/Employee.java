package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
public class Employee {
  @Id
  Long id;

  String email;
  String name;

  @ManyToOne
  //@JoinColumn("part_name")
  Part part;

  @Setter(AccessLevel.NONE)
  UUID employeeNumber = UUID.randomUUID();

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  Rank rank;
  LocalDate dateOfJoining;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  EmployeeStatus status = EmployeeStatus.Join;

  @OneToOne
  Profile profile;

  public Employee(String email, String name, Part part, Rank rank, LocalDate dateOfJoining) {
    this.email = email;
    this.name = name;
    this.part = part;
    this.rank = rank;
    this.dateOfJoining = dateOfJoining;
  }

  public Employee() {

  }
}
