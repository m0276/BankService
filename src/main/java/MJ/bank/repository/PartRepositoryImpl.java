package MJ.bank.repository;

import MJ.bank.component.MakeSlice;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Part;
import MJ.bank.entity.QPart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.lang.reflect.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;


@RequiredArgsConstructor
public class PartRepositoryImpl implements PartRepositoryCustom{

  JPAQueryFactory queryFactory;
  QPart part = QPart.part;
  MakeSlice makeSlice;

  @Override
  public Slice<?> findAll(CursorPageRequest request, Pageable pageable) {
    return makeSlice.findAll(request,pageable,part);
  }

}
