package MJ.bank.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class UpdateLogMapperHelper {

  @Named("LongToString")
  public String LongToString(Long id){
    return Long.toString(id);
  }

}
