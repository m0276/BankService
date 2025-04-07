package MJ.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "part")
public class Part {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "part_name")
  String partName;

  @Column
  String explanation;

  @Column
  LocalDate establish;

  @OneToMany(mappedBy = "part")
  @JsonIgnore
  List<Employee> employees;

  public Part(String partName, String explanation, LocalDate establish) {
    this.partName = partName;
    this.explanation = explanation;
    this.establish = establish;
  }

  public Part(){

  }
}
