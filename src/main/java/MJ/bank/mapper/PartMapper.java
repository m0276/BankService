package MJ.bank.mapper;

import MJ.bank.dto.PartDto;
import MJ.bank.entity.Part;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartMapper {

  public PartDto toDto(Part part);
}
