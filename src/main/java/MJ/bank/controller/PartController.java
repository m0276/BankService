package MJ.bank.controller;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.request.PartCreateRequest;
import MJ.bank.dto.request.PartUpdateRequest;
import MJ.bank.dto.response.CursorPageResponsePartDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.PartService;
import jakarta.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class PartController {

  private final PartService partService;

  @GetMapping
  public CursorPageResponsePartDto get(@ModelAttribute CursorPageRequest request, Pageable pageable){
    try{
      return partService.getParts(request,pageable);
    } catch (Exception e){
      e.printStackTrace();
      return new CursorPageResponsePartDto(null,null,null,null,null,false);
    }
  }

  @PostMapping
  public ResponseEntity<?> post(@RequestBody PartCreateRequest request){
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(partService.creat(request));
    } catch (NullPointerException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),
              HttpStatus.BAD_REQUEST.value(),
              "잘못된 요청입니다.",
              e.getMessage()+"는 필수입니다."));
    }
  }

  @PatchMapping("/{partName}")
  public ResponseEntity<?> update(@RequestBody PartUpdateRequest request){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(partService.update(request));
    } catch (NoSuchElementException | EntityExistsException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),
              HttpStatus.BAD_REQUEST.value(),
              "잘못된 요청입니다.",
              e.getMessage()));
    }
  }

  @DeleteMapping("/{partName}")
  public ResponseEntity<?> delete(@PathVariable String partName){
    try{
      partService.delete(partName);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),
              HttpStatus.BAD_REQUEST.value(),
              "잘못된 요청입니다.",
              e.getMessage()));
    }
  }
}
