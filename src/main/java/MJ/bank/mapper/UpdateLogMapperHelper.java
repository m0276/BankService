package MJ.bank.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("UpdateLogMapperHelper")
public class UpdateLogMapperHelper {

  @Named("LongToString")
  public String LongToString(Long id){
    return Long.toString(id);
  }

}
