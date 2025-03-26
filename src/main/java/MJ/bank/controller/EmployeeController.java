package MJ.bank.controller;

import MJ.bank.dto.ProfileDto;
import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.EmployeeService;
import MJ.bank.service.ProfileService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final ProfileService profileService;

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@RequestBody EmployeeUpdateRequest updateRequest,@PathVariable Long id){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(updateRequest,id));
    }
    catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),
          "잘못된 요청입니다.", e.getMessage()));
    }

  }

  @PostMapping("/upload")
  public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) {
    try{
      ProfileDto profileDto = profileService.save(file);
      return ResponseEntity.status(HttpStatus.OK).body(profileDto);
    }
    catch (IOException e){

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          new ErrorResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류입니다.",
              "파일을 불러올 수 없습니다.")
      );
    }
  }
}
