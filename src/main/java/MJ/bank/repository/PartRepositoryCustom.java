package MJ.bank.repository;

import MJ.bank.dto.PartDto;
import MJ.bank.dto.request.CursorPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface PartRepositoryCustom {

  Slice<PartDto> searchAll(CursorPageRequest request, Pageable pageable);

}
