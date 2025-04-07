package MJ.bank.repository;

import MJ.bank.component.MakeSlice;
import MJ.bank.dto.PartDto;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Part;
import MJ.bank.entity.QPart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class PartRepositoryImpl implements PartRepositoryCustom{

  QPart part = QPart.part;
  private final MakeSlice makeSlice;

  @Override
  public Slice<Part> searchAll(CursorPageRequest request, Pageable pageable) {
    return (Slice<Part>) makeSlice.findAll(request,pageable,part);
  }

}
