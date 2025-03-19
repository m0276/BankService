package MJ.bank.service;


import MJ.bank.component.CursorResult;
import MJ.bank.dto.BackupDto;
import MJ.bank.entity.BackupLog;
import MJ.bank.mapper.BackupLogMapper;
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

  CursorResult<BackupDto> getBackups(Long cursorId, Pageable page) {
    final List<BackupDto> backups = getBackup(cursorId, page);
    final Long lastIdOfList = backups.isEmpty() ?
        null : backups.getLast().id();

    return new CursorResult<>(backups, hasNextInBackup(lastIdOfList));
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

}
