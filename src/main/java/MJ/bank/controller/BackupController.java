package MJ.bank.controller;


import MJ.bank.dto.BackupDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.response.CursorPageResponseBackupDto;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.service.BackupService;
import MJ.bank.service.CursorService;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backups")
public class BackupController {
  private final CursorService cursorService;
  private final BackupService backupService;
  @GetMapping
  public CursorPageResponseBackupDto get(@ModelAttribute("request")CursorPageRequest request, Pageable pageable){
    try {
      return cursorService.getBackups(request,pageable);
    } catch (NoSuchElementException e){
      e.printStackTrace();
      return null;
    }
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody BackupDto backupDto){
    boolean check = backupService.create();
    if(!check){
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
         new ErrorResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
             " 서버 오류입니다.","백업에 실패하였습니다.")
     );
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
