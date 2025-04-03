package MJ.bank.repository;

import MJ.bank.component.MakeSlice;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.QBackupLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackupLogRepositoryImpl implements BackupLogRepositoryCustom{

  QBackupLog backupLog = QBackupLog.backupLog;
  private final MakeSlice makeSlice;

  @Override
  public Slice<BackupLog> searchAll(CursorPageRequest request, Pageable page) {
    return (Slice<BackupLog>) makeSlice.findAll(request,page,backupLog);
  }
}
