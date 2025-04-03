package MJ.bank.controller;


import MJ.bank.dto.UpdateLogDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.response.CursorPageResponseUpdateLogDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.CursorService;
import MJ.bank.service.UpdateLogService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateLogController {
  private final UpdateLogService updateLogService;
  private final CursorService cursorService;

  @GetMapping
  public CursorPageResponseUpdateLogDto get(@ModelAttribute("request")CursorPageRequest request, Pageable pageable){
    try{
      return cursorService.getUpdateLogs(request,pageable);
    } catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getLog(@PathVariable Long id){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(updateLogService.findById(id));
    } catch (NoSuchElementException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
          new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),
              "잘못된 요청입니다.", e.getMessage())
      );
    }
  }

}
