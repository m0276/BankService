package MJ.bank.mapper;


import MJ.bank.dto.UpdateDiffDto;
import MJ.bank.dto.UpdateLogDto;
import MJ.bank.entity.UpdateLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UpdateLogMapperHelper.class})
public interface UpdateLogMapper {

    @Mapping(source = "propertyName", target = "propertyName", ignore = true)
    @Mapping(source = "currContent", target = "currContent", ignore = true)
    @Mapping(source = "prevContent", target = "prevContent", ignore = true)
    @Mapping(source = "employeeId", target = "employeeNumber", qualifiedByName = "LongToString")
    UpdateLogDto toDto(UpdateLog updateLog);


    @Mapping(source = "employeeId", target = "employeeNumber", ignore = true)
    @Mapping(source = "memo", target = "memo", ignore = true)
    @Mapping(source = "ip", target = "ip", ignore = true)
    @Mapping(source = "updateTime", target = "updateTime", ignore = true)
    UpdateDiffDto toDiffDto(UpdateLog updateLog);

}
