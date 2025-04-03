package MJ.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DashboardDto(
    Integer totalEmployees,
    Integer modifyCaseWeek,
    LocalDateTime lastBackUpTime,
    BigDecimal difference, // 최근 1년 월별 직원수 변동 추이
    List<Integer>partEmployees,
    List<Integer> rankEmployees
) {

}
