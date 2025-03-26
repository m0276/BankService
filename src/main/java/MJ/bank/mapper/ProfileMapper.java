package MJ.bank.mapper;

import MJ.bank.dto.ProfileDto;
import MJ.bank.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toDto(Profile profile);
}
