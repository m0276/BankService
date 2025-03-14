package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Employee {
  @Id
  String email;
  String name;

  @ManyToOne
  Part part;

  UUID employeeNumber;
  Rank rank;
  LocalDate dateOfJoining;
  EmployeeStatus status = EmployeeStatus.Join;

  @OneToOne
  Profile profile;

}
