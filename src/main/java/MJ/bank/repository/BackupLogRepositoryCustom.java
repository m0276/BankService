package MJ.bank.repository;

import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.BackupLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface BackupLogRepositoryCustom {
  Slice<BackupLog> searchAll(CursorPageRequest request, Pageable page);
}
