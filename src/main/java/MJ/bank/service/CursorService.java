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

  //TODO : backup, update log custom repository 생성하고 Impl 작성. sort 삭제
  public CursorPageResponseBackupDto getBackups(Long cursorId, Pageable page) {
    final List<BackupDto> backups = getBackup(cursorId, page);
    final Long lastIdOfList = backups.isEmpty() ?
        null : backups.getLast().id();

    return new CursorPageResponseBackupDto(backups,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) backups.size(),hasNextInBackup(lastIdOfList));
  }

  private List<BackupDto> getBackup(Long id, Pageable page) {
    List<BackupLog> list;
    if(id == null){
      list = backupLogRepository.findAll(page).getContent();
    }
    else{
      list = backupLogRepository.findAllById(id, page).getContent();
    }

    List<BackupDto> result = new ArrayList<>();
    for(BackupLog log : list){
      result.add(backupLogMapper.toDto(log));
    }
    result.sort(Comparator.comparing(BackupDto::startedAt)); // 삭제 필요
    return result;
  }

  private Boolean hasNextInBackup(Long id) {
    if (id == null) return false;
    return backupLogRepository.existsByIdLessThan(id);
  }


  public CursorPageResponseUpdateLogDto getUpdateLogs(Long cursorId, Pageable page) {
    final List<UpdateLogDto> updateLogs = getUpdateLog(cursorId, page);
    final Long lastIdOfList = updateLogs.isEmpty() ?
        null : updateLogs.getLast().id();

    return new CursorPageResponseUpdateLogDto(updateLogs,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) updateLogs.size(),hasNextInUpdate(lastIdOfList));
  }

  private List<UpdateLogDto> getUpdateLog(Long id, Pageable page) {
    List<UpdateLog> list;
    if(id == null){
      list = updateLogRepository.findAll(page).getContent();
    }
    else{
      list = updateLogRepository.findAllById(id,page).getContent();
    }

    List<UpdateLogDto> result = new ArrayList<>();
    for(UpdateLog log : list){
      result.add(updateLogMapper.toDto(log));
    }

    result.sort(Comparator.comparing(UpdateLogDto::updateTime)); // 삭제 필요

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
