package MJ.bank.repository;

import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Part;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PartRepositoryCustom {

  Slice<?> findAll(CursorPageRequest request, Pageable pageable);

}
