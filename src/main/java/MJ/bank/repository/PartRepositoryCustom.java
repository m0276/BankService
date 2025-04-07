package MJ.bank.repository;

import MJ.bank.dto.PartDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Part;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface PartRepositoryCustom {

  Slice<Part> searchAll(CursorPageRequest request, Pageable pageable);

}
