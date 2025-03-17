package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Employee {
  @Id
  String email;
  String name;

  @ManyToOne
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  Part part;

  UUID employeeNumber;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  Rank rank;
  LocalDate dateOfJoining;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  EmployeeStatus status = EmployeeStatus.Join;

  @OneToOne
  Profile profile;

  public Employee(String email, String name, Part part, UUID employeeNumber, Rank rank,
      LocalDate dateOfJoining, EmployeeStatus status, Profile profile) {
    this.email = email;
    this.name = name;
    this.part = part;
    this.employeeNumber = employeeNumber;
    this.rank = rank;
    this.dateOfJoining = dateOfJoining;
    this.status = status;
    this.profile = profile;
  }

  public Employee() {

  }
}
