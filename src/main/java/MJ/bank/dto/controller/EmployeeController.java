package MJ.bank.dto.controller;


import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

  EmployeeService employeeService;

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@RequestBody EmployeeUpdateRequest updateRequest,@PathVariable Long id){
   return employeeService.update(updateRequest,id);
  }
}
