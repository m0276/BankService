package MJ.bank.service;

import MJ.bank.dto.DashboardDto;
import MJ.bank.dto.EmployeeTrendDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
  private final EmployeeService employeeService;
  private final UpdateLogService updateLogService;
  private final BackupService backupService;

  public DashboardDto getDashboard(){
    return new DashboardDto(totalEmployees(),updateLogs(),getLastBackUpTime(),getDifference()
    ,getPartEmployees(),getRankEmployees());
  }


  private Integer totalEmployees(){
    return employeeService.getSize();
  }

  private Integer updateLogs(){
    return updateLogService.getSize();
  }

  private LocalDateTime getLastBackUpTime(){
    return backupService.lastBackupTime();
  }

  //TODO: big decimal로 직원 추이 확인하는 로직 추가 필요
  private BigDecimal getDifference(){
    return BigDecimal.valueOf(1);
  }

  private List<Integer> getPartEmployees(){
    return employeeService.findPart();
  }

  private List<Integer> getRankEmployees(){
    return employeeService.findRank();
  }
}
