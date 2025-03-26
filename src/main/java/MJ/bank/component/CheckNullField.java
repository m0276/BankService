package MJ.bank.component;

import MJ.bank.dto.response.ErrorResponse;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckNullField {

  public String hasNullFields(Object obj) {
    for (Field field : obj.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      try {
        if (field.get(obj) == null) {
         return field.getName();
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

    }
    return null;
  }
}
