package MJ.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column
  String email;

  @Column
  String name;

  @ManyToOne
  @JoinColumn(name = "part_name")
  Part part;

  @Setter(AccessLevel.NONE)
  @Column(name = "employee_number")
  UUID employeeNumber = UUID.randomUUID();

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column
  Rank rank;

  @Column(name = "date_of_joining")
  LocalDate dateOfJoining;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column
  EmployeeStatus status = EmployeeStatus.Join;

  @OneToOne
  @JoinColumn(name = "profile_id")
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
