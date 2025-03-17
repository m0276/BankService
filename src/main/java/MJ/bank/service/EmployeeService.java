package MJ.bank.service;


import MJ.bank.CheckNullField;
import MJ.bank.dto.EmployeeDto;
import MJ.bank.dto.request.EmployeeCreateRequest;
import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.entity.Employee;
import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Rank;
import MJ.bank.mapper.EmployeeMapper;
import MJ.bank.repository.EmployeeRepository;
import MJ.bank.repository.PartRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

  public ResponseEntity<?> create(EmployeeCreateRequest createRequest){
    if(checkNullField.hasNullFields(createRequest).hasBody()) return checkNullField.hasNullFields(createRequest);

    if(employeeRepository.findByEmail(createRequest.email()).isPresent()){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT.value(), "잘못된 요청입니다.",
          createRequest.email()) +"은 이미 등록되어 있는 이메일입니다.");
    }

    if(partService.findPart(createRequest.partName()) == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",
              createRequest.partName()+"을/를 찾을 수 없습니다."));
    }

    Employee employee = new Employee(createRequest.email(), createRequest.name(),partService.findPart(
        createRequest.partName()) ,createRequest.rank(),createRequest.dateOfJoining());

    profileService.save(employee.getId());
    employeeRepository.save(employee);

    return ResponseEntity.status(HttpStatus.CREATED).body(employeeMapper.toDto(employee));
  }

  public ResponseEntity<?> update(EmployeeUpdateRequest updateRequest, Long id){
    if(employeeRepository.findById(id).isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",
            updateRequest.email()+"을/를 찾을 수 없습니다."));

    String newEmail = updateRequest.email();
    String newName = updateRequest.name();
    String partName = updateRequest.partName();
    Rank rank = updateRequest.rank();
    LocalDate joining = updateRequest.dateOfJoining();

    Employee employee = employeeRepository.findById(id).get();
    if(newEmail != null) employee.setEmail(newEmail);
    if(newName != null) employee.setName(newName);
    if(partName != null){
      if(partService.findPart(partName) == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",
                partName+"을/를 찾을 수 없습니다."));
      }
      employee.setPart(partService.findPart(partName));
    }
    if(rank != null) employee.setRank(rank);
    if(joining != null) employee.setDateOfJoining(joining);

    profileService.update(id);

    employeeRepository.save(employee);

    return ResponseEntity.status(HttpStatus.OK).body(employeeMapper.toDto(employee));
  }

  public ResponseEntity<?> delete(Long id){
    if(employeeRepository.findById(id).isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",
            "존재하지 않는 직원입니다."));

    if(employeeRepository.findById(id).get().getProfile() != null){
      profileService.delete(id);
    }
    employeeRepository.deleteById(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public Object findInfo(Long id){
    if(employeeRepository.findById(id).isEmpty()) return new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "잘못된 요청입니다.",
            "해당 직원을 찾을 수 없습니다.");

    return employeeMapper.toDto(employeeRepository.findById(id).get());
  }


}
