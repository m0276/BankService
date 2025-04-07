package MJ.bank.repository;

import MJ.bank.component.MakeSlice;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.QUpdateLog;
import MJ.bank.entity.UpdateLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class UpdateLogRepositoryImpl implements UpdateLogRepositoryCustom{
  QUpdateLog updateLog = QUpdateLog.updateLog;
  private  final MakeSlice makeSlice;

  @Override
  public Slice<UpdateLog> searchAll(CursorPageRequest request, Pageable pageable) {
    return (Slice<UpdateLog>) makeSlice.findAll(request,pageable,updateLog);
  }
}
