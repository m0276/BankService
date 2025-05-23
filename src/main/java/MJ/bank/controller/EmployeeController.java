package MJ.bank.controller;

import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.request.EmployeeCreateRequest;
import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.dto.response.CursorPageResponseEmployeeDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.CursorService;
import MJ.bank.service.EmployeeService;
import jakarta.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final CursorService cursorService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> create(@RequestPart(name = "request") EmployeeCreateRequest createRequest, @RequestPart(name = "file",required = false) MultipartFile file){
    try{
      return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(createRequest,file));
    } catch (NullPointerException | EntityExistsException | NoSuchElementException e){
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", e.getMessage()));
    } catch (RuntimeException e){
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new ErrorResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류입니다.",
            "파일을 생성 할 수 없습니다.")
      );
    }
  }

  @PatchMapping(value = "/{id}",consumes = {"multipart/form-data"})
  public ResponseEntity<?> update(@RequestPart(name = "request") EmployeeUpdateRequest updateRequest,@PathVariable(name = "id") Long id,
  @RequestPart(name = "file",required = false) MultipartFile file){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(updateRequest,id,file));
    }
    catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),
          "잘못된 요청입니다.", e.getMessage()));
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id){
    try{
      employeeService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.",
              "해당 직원을 찾을 수 없습니다."));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable("id") Long id){
    try{
      return ResponseEntity.status(HttpStatus.OK).body(employeeService.findInfo(id));
    } catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.",
              "해당 직원을 찾을 수 없습니다."));
    }
  }

  @GetMapping
  public CursorPageResponseEmployeeDto findAll(@ModelAttribute("request") CursorPageRequest request, Pageable page){
    return cursorService.getEmployee(request,page);
  }
}
