package MJ.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Dashboard {
  Integer totalEmployees;
  Integer modifyCaseWeek;
  LocalDateTime lastBackUpTime;
  BigDecimal difference;
  List<Integer> partEmployees;
  List<Integer> rankEmployees;
}
