package MJ.bank.controller;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class PartController {

  private final PartService partService;

  @GetMapping
  public ResponseEntity<?> get(@RequestBody CursorPageRequest request){
    try{
      return ResponseEntity.status(HttpStatus.OK).body(partService.getPartList(request));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
