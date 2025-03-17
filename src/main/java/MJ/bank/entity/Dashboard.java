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
  BigDecimal difference; // 최근 1년 월별 직원수 변동 추이
  List<Integer> partEmployees;
  List<Integer> rankEmployees;
}
