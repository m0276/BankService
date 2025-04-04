package MJ.bank.controller;


import MJ.bank.dto.DashboardDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.DashboardService;
import MJ.bank.service.EmployeeService;
import MJ.bank.service.UpdateLogService;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping
  public String homeRedirect() {
    return "redirect:/api/dashboard";
  }

  @GetMapping("/api/dashboard")
  public ResponseEntity<?> get(){
    try{
      return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getDashboard());
    } catch (Exception e){
      e.printStackTrace();
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(
         LocalDateTime.now(),
         HttpStatus.INTERNAL_SERVER_ERROR.value(),
         "서버 오류입니다.",
         "예기치 못한 오류가 발생했습니다."
     ));
    }
  }
}
