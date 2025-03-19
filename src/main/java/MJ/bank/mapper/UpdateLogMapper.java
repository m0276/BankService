package MJ.bank.mapper;


import MJ.bank.dto.UpdateLogDto;
import MJ.bank.entity.UpdateLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UpdateLogMapperHelper.class})
public interface UpdateLogMapper {

    @Mapping(source = "employeeId", target = "employeeNumber", qualifiedByName = "LongToString")
    public UpdateLogDto toDto(UpdateLog updateLog);

}
