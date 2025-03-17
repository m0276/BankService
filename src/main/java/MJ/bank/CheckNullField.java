package MJ.bank;

import MJ.bank.dto.response.ErrorResponse;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckNullField {

  public ResponseEntity<?> hasNullFields(Object obj) {
    for (Field field : obj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(obj) == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
              new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),
                  "잘못된 요청입니다.", field.getName()+"은/는 필수입니다."));
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
