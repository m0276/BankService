package MJ.bank.repository;

import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.UpdateLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface UpdateLogRepositoryCustom {
  Slice<UpdateLog> searchAll(CursorPageRequest request, Pageable pageable);
}
