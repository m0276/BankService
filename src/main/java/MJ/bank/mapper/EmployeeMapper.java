package MJ.bank.mapper;


import MJ.bank.dto.EmployeeDto;
import MJ.bank.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  public EmployeeDto toDto(Employee employee);
}
