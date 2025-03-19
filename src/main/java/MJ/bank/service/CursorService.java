package MJ.bank.service;


import MJ.bank.component.CursorResult;
import MJ.bank.dto.BackupDto;
import MJ.bank.dto.CursorPageResponseBackupDto;
import MJ.bank.dto.CursorPageResponseUpdateLogDto;
import MJ.bank.dto.UpdateLogDto;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.UpdateLog;
import MJ.bank.mapper.BackupLogMapper;
import MJ.bank.mapper.UpdateLogMapper;
import MJ.bank.repository.BackupLogRepository;
import MJ.bank.repository.UpdateLogRepository;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursorService {

  private final BackupLogRepository backupLogRepository;
  private final UpdateLogRepository updateLogRepository;
  private final BackupLogMapper backupLogMapper;
  private final UpdateLogMapper updateLogMapper;

  CursorPageResponseBackupDto getBackups(Long cursorId, Pageable page) {
    final List<BackupDto> backups = getBackup(cursorId, page);
    final Long lastIdOfList = backups.isEmpty() ?
        null : backups.getLast().id();

    return new CursorPageResponseBackupDto(backups,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) backups.size(),hasNextInBackup(lastIdOfList));
  }

  private List<BackupDto> getBackup(Long id, Pageable page) {
    List<BackupLog> list;
    if(id == null){
      list = backupLogRepository.findAllByOrderByStartTimeAsc(page);
    }
    else{
      list = backupLogRepository.findByIdLessThanOrderByStartTimeAsc(id,page);
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


  CursorPageResponseUpdateLogDto getUpdateLogs(Long cursorId, Pageable page) {
    final List<UpdateLogDto> updateLogs = getUpdateLog(cursorId, page);
    final Long lastIdOfList = updateLogs.isEmpty() ?
        null : updateLogs.getLast().id();

    return new CursorPageResponseUpdateLogDto(updateLogs,lastIdOfList != null ? lastIdOfList.toString() : null
        ,lastIdOfList,10,(long) updateLogs.size(),hasNextInBackup(lastIdOfList));
  }

  private List<UpdateLogDto> getUpdateLog(Long id, Pageable page) {
    List<UpdateLog> list;
    if(id == null){
      list = updateLogRepository.findAllByOrderByIpAsc(page);
    }
    else{
      list = updateLogRepository.findByIdLessThanOrderByIpAsc(id,page);
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

}
