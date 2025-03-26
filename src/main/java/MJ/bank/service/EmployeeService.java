package MJ.bank.service;


import MJ.bank.component.CheckNullField;
import MJ.bank.dto.EmployeeDto;
import MJ.bank.dto.request.EmployeeCreateRequest;
import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.entity.Employee;
import MJ.bank.entity.Rank;
import MJ.bank.entity.UpdateType;
import MJ.bank.mapper.EmployeeMapper;
import MJ.bank.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final PartService partService;
  private final EmployeeMapper employeeMapper;
  private final CheckNullField checkNullField;
  private final ProfileService profileService;
  private final UpdateLogService updateLogService;

  public EmployeeDto create(EmployeeCreateRequest createRequest){
    if(checkNullField.hasNullFields(createRequest) != null) {
      throw new NullPointerException(checkNullField.hasNullFields(createRequest)+"는 필수입니다.");
    }

    if(employeeRepository.findByEmail(createRequest.email()).isPresent()){
      throw new EntityExistsException(createRequest.email() +"은 이미 등록되어 있는 이메일입니다.");
    }

    if(partService.findPart(createRequest.partName()) == null){
      throw new NoSuchElementException(createRequest.partName()+"을/를 찾을 수 없습니다.");
    }

    Employee employee = new Employee(createRequest.email(), createRequest.name(),partService.findPart(
        createRequest.partName()) ,createRequest.rank(),createRequest.dateOfJoining());

    //profileService.save(employee.getId());
    employeeRepository.save(employee);
    updateLogService.save(employee.getId(),"직원 추가" ,UpdateType.Add,null,createRequest, createRequest.memo());

    return employeeMapper.toDto(employee);
  }

  public EmployeeDto update(EmployeeUpdateRequest updateRequest, Long id){
    if(employeeRepository.findById(id).isEmpty()) {
      throw new NoSuchElementException(updateRequest.partName()+"을/를 찾을 수 없습니다.");
    }

    String newEmail = updateRequest.email();
    String newName = updateRequest.name();
    String partName = updateRequest.partName();
    Rank rank = updateRequest.rank();
    LocalDate joining = updateRequest.dateOfJoining();


    Employee employee = employeeRepository.findById(id).get();
    if(newEmail != null) {
      updateLogService.save(employee.getId(),"이메일" ,UpdateType.Update,employee.getEmail(),updateRequest.email(), updateRequest.memo());
      employee.setEmail(newEmail);
    }

    if(newName != null) {
      updateLogService.save(employee.getId(),"이름" ,UpdateType.Update,employee.getName(),updateRequest.name(), updateRequest.memo());
      employee.setName(newName);
    }

    if(partName != null){
      if(partService.findPart(partName) == null) {
        throw new NoSuchElementException(partName+"을/를 찾을 수 없습니다.");
      }
      updateLogService.save(employee.getId(),"부서" ,UpdateType.Update,employee.getPart().getPartName(),updateRequest.partName(), updateRequest.memo());

      employee.setPart(partService.findPart(partName));
    }

    if(rank != null) {
      updateLogService.save(employee.getId(),"직함",UpdateType.Update,employee.getRank(),updateRequest.rank(), updateRequest.memo());
      employee.setRank(rank);
    }
    if(joining != null) {
      updateLogService.save(employee.getId(),"입사일" ,UpdateType.Update,employee.getDateOfJoining(),updateRequest.dateOfJoining(), updateRequest.memo());
      employee.setDateOfJoining(joining);
    }

    profileService.update(id);
    employeeRepository.save(employee);

    return employeeMapper.toDto(employee);
  }

  public void delete(Long id){
    if(employeeRepository.findById(id).isEmpty()) throw new NoSuchElementException("존재하지 않는 직원입니다.");


    profileService.delete(id);
    updateLogService.save(id,"직원 데이터 삭제",UpdateType.Delete,employeeRepository.findById(id),null,"직원 삭제");
    employeeRepository.deleteById(id);
  }

  public EmployeeDto findInfo(Long id){
    if(employeeRepository.findById(id).isEmpty()) throw new NoSuchElementException("해당 직원을 찾을 수 없습니다.");

    return employeeMapper.toDto(employeeRepository.findById(id).get());
  }


}
