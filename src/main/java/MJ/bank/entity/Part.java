package MJ.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Part {
  @Id
  String partName;
  String explanation;
  LocalDate establish;

  @OneToMany(mappedBy = "email")
  List<Employee> employees;
}
