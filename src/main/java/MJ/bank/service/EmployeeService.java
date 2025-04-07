package MJ.bank.service;


import MJ.bank.component.CheckNullField;
import MJ.bank.dto.EmployeeDto;
import MJ.bank.dto.ProfileDto;
import MJ.bank.dto.request.EmployeeCreateRequest;
import MJ.bank.dto.request.EmployeeUpdateRequest;
import MJ.bank.dto.response.ErrorResponse;
import MJ.bank.entity.Employee;
import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Part;
import MJ.bank.entity.Profile;
import MJ.bank.entity.Rank;
import MJ.bank.entity.UpdateType;
import MJ.bank.mapper.EmployeeMapper;
import MJ.bank.repository.EmployeeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

  public EmployeeDto create(EmployeeCreateRequest createRequest, MultipartFile file){
    if(checkNullField.hasNullFields(createRequest) != null) {
      throw new NullPointerException(checkNullField.hasNullFields(createRequest)+"는 필수입니다.");
    }

    if(employeeRepository.findByEmail(createRequest.getEmail()).isPresent()){
      throw new EntityExistsException(createRequest.getEmail() +"은 이미 등록되어 있는 이메일입니다.");
    }

    if(partService.findPart(createRequest.getPartName()) == null){
      throw new NoSuchElementException(createRequest.getPartName()+"을/를 찾을 수 없습니다.");
    }

    Employee employee = new Employee(createRequest.getEmail(), createRequest.getName(),partService.findPart(
        createRequest.getPartName()) ,createRequest.getRank(),createRequest.getDateOfJoining());

    Profile profileDto = profileService.save(file);
    if(profileDto == null) throw new RuntimeException();
    employee.setProfile(profileDto);
    employeeRepository.save(employee);
    updateLogService.save(employee.getId(),"직원 추가" ,UpdateType.Add,"",createRequest, createRequest.getMemo());

    return employeeMapper.toDto(employee);
  }

  public EmployeeDto update(EmployeeUpdateRequest updateRequest, Long id, MultipartFile file){
    if(employeeRepository.findById(id).isEmpty()) {
      throw new NoSuchElementException(updateRequest.getPartName()+"을/를 찾을 수 없습니다.");
    }

    String newEmail = updateRequest.getEmail();
    String newName = updateRequest.getName();
    String partName = updateRequest.getPartName();
    Rank rank = updateRequest.getRank();
    LocalDate joining = updateRequest.getDateOfJoining();


    Employee employee = employeeRepository.findById(id).get();
    if(newEmail != null) {
      updateLogService.save(employee.getId(),"이메일" ,UpdateType.Update,employee.getEmail(),updateRequest.getEmail(), updateRequest.getMemo());
      employee.setEmail(newEmail);
    }

    if(newName != null) {
      updateLogService.save(employee.getId(),"이름" ,UpdateType.Update,employee.getName(),updateRequest.getName(), updateRequest.getMemo());
      employee.setName(newName);
    }

    if(partName != null){
      if(partService.findPart(partName) == null) {
        throw new NoSuchElementException(partName+"을/를 찾을 수 없습니다.");
      }
      updateLogService.save(employee.getId(),"부서" ,UpdateType.Update,employee.getPart().getPartName(),updateRequest.getPartName(), updateRequest.getMemo());

      employee.setPart(partService.findPart(partName));
    }

    if(rank != null) {
      updateLogService.save(employee.getId(),"직함",UpdateType.Update,employee.getRank(),updateRequest.getRank(), updateRequest.getMemo());
      employee.setRank(rank);
    }
    if(joining != null) {
      updateLogService.save(employee.getId(),"입사일" ,UpdateType.Update,employee.getDateOfJoining(),updateRequest.getDateOfJoining(), updateRequest.getMemo());
      employee.setDateOfJoining(joining);
    }

    profileService.update(id,file);
    employeeRepository.save(employee);

    return employeeMapper.toDto(employee);
  }

  public void delete(Long id){
    if(employeeRepository.findById(id).isEmpty()) throw new NoSuchElementException("존재하지 않는 직원입니다.");


    profileService.delete(id);
    updateLogService.save(id,"직원 데이터 삭제",UpdateType.Delete,employeeRepository.findById(id),"","직원 삭제");
    employeeRepository.deleteById(id);
  }

  public EmployeeDto findInfo(Long id){
    if(employeeRepository.findById(id).isEmpty()) throw new NoSuchElementException("해당 직원을 찾을 수 없습니다.");

    return employeeMapper.toDto(employeeRepository.findById(id).get());
  }

  public Integer getSize(){
    List<Employee> list = employeeRepository.findAll();
    int count = 0;

    for(Employee e : list){
      if(e.getStatus().equals(EmployeeStatus.Join) || e.getStatus().equals(EmployeeStatus.OnLeave)) count++;
    }
    return count;
  }

  public List<Integer> findPart(){
    List<Part> parts = partService.findAll();
    List<Employee> employees = employeeRepository.findAll();

    List<Integer> counts = new ArrayList<>();

    for (Part part : parts) {
      int count = 0;
      for (Employee employee : employees) {
        if (part.equals(employee.getPart()) && (
            employee.getStatus().equals(EmployeeStatus.Join) ||
                employee.getStatus().equals(EmployeeStatus.OnLeave)))
          count++;
      }
      counts.add(count);
    }

    return counts;
  }

  public List<Integer> findRank(){
    List<Rank> ranks = Stream.of(Rank.values()).toList();
    List<Employee> employees = employeeRepository.findAll();

    List<Integer> counts = new ArrayList<>();

    for(Rank rank : ranks){
      int count = 0;
      for(Employee e : employees){
        if(e.getRank().equals(rank)  && (
            e.getStatus().equals(EmployeeStatus.Join) ||
                e.getStatus().equals(EmployeeStatus.OnLeave))) count++;
      }
      counts.add(count);
    }

    return counts;
  }
}
