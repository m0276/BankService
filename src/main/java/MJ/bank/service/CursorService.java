package MJ.bank.service;


import MJ.bank.dto.BackupDto;
import MJ.bank.dto.EmployeeDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.dto.response.CursorPageResponseBackupDto;
import MJ.bank.dto.response.CursorPageResponseEmployeeDto;
import MJ.bank.dto.response.CursorPageResponseUpdateLogDto;
import MJ.bank.dto.UpdateLogDto;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.Employee;
import MJ.bank.entity.UpdateLog;
import MJ.bank.mapper.BackupLogMapper;
import MJ.bank.mapper.EmployeeMapper;
import MJ.bank.mapper.UpdateLogMapper;
import MJ.bank.repository.BackupLogRepository;
import MJ.bank.repository.EmployeeRepository;
import MJ.bank.repository.UpdateLogRepository;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursorService {

  private final BackupLogRepository backupLogRepository;
  private final UpdateLogRepository updateLogRepository;
  private final EmployeeRepository employeeRepository;
  private final BackupLogMapper backupLogMapper;
  private final UpdateLogMapper updateLogMapper;
  private final EmployeeMapper employeeMapper;

  public CursorPageResponseBackupDto getBackups(CursorPageRequest request, Pageable page) {
    final List<BackupDto> backups = getBackup(request, page);
    final Long lastIdOfList = backups.isEmpty() ?
        null : backups.getLast().id();

    return new CursorPageResponseBackupDto(backups,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) backups.size(),hasNextInBackup(lastIdOfList));
  }

  private List<BackupDto> getBackup(CursorPageRequest request, Pageable page) {
    List<BackupLog> list;
    if(request.getCursorId() == null){
      list = backupLogRepository.searchAll(request,page).getContent();
    }
    else{
      list = backupLogRepository.findAllById(request.getCursorId(), page).getContent();
    }

    List<BackupDto> result = new ArrayList<>();
    for(BackupLog log : list){
      result.add(backupLogMapper.toDto(log));
    }

    return result;
  }

  private Boolean hasNextInBackup(Long id) {
    if (id == null) return false;
    return backupLogRepository.existsByIdLessThan(id);
  }


  public CursorPageResponseUpdateLogDto getUpdateLogs(CursorPageRequest request, Pageable page) {
    final List<UpdateLogDto> updateLogs = getUpdateLog(request, page);
    final Long lastIdOfList = updateLogs.isEmpty() ?
        null : updateLogs.getLast().id();

    return new CursorPageResponseUpdateLogDto(updateLogs,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) updateLogs.size(),hasNextInUpdate(lastIdOfList));
  }

  private List<UpdateLogDto> getUpdateLog(CursorPageRequest request, Pageable page) {
    List<UpdateLog> list;
    if(request.getCursorId() == null){
      list = updateLogRepository.searchAll(request,page).getContent();
    }
    else{
      list = updateLogRepository.findAllById(request.getCursorId(), page).getContent();
    }

    List<UpdateLogDto> result = new ArrayList<>();
    for(UpdateLog log : list){
      result.add(updateLogMapper.toDto(log));
    }

    return result;
  }

  private Boolean hasNextInUpdate(Long id) {
    if (id == null) return false;
    return updateLogRepository.existsByIdLessThan(id);
  }


  public CursorPageResponseEmployeeDto getEmployee(CursorPageRequest request, Pageable page) {
    final List<EmployeeDto> employees = getEmployees(request, request.getCursorId(), page);
    final Long lastIdOfList = employees.isEmpty() ?
        null : employees.getLast().id();

    return new CursorPageResponseEmployeeDto(employees,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) employees.size(),hasNextInEmployee(lastIdOfList));
  }

  private List<EmployeeDto> getEmployees(CursorPageRequest request,Long id, Pageable page) {
    List<Employee> list;
    if(id == null){
      list = employeeRepository.searchAll(request,page).getContent();
    }
    else{
      list = employeeRepository.findAllById(id);
    }

    List<EmployeeDto> result = new ArrayList<>();
    for(Employee log : list){
      result.add(employeeMapper.toDto(log));
    }

    return result;
  }

  private Boolean hasNextInEmployee(Long id) {
    if (id == null) return false;
    return employeeRepository.existsByIdLessThan(id);
  }


}
