package MJ.bank.component;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.BackupLog;
import MJ.bank.entity.Employee;
import MJ.bank.entity.Part;
import MJ.bank.entity.QBackupLog;
import MJ.bank.entity.QEmployee;
import MJ.bank.entity.QPart;
import MJ.bank.entity.QUpdateLog;
import MJ.bank.entity.UpdateLog;
import MJ.bank.entity.UpdateType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MakeSlice {

  private final JPAQueryFactory queryFactory;

  public Slice<?> findAll(CursorPageRequest request, Pageable pageable, EntityPath<?> o) {
    if(noRequest(request)) return new SliceImpl<>(queryFactory.selectFrom(o).orderBy(sortBy(request.getSortDirection(),request.getSortType(),o)).fetch()
        ,pageable,hasNext(request,queryFactory.selectFrom(o).fetch().size()));


    return new SliceImpl<>(find(request, o),pageable,hasNext(request,queryFactory.selectFrom(o).fetch().size()));
  }

  private boolean hasNext(CursorPageRequest request, int len){
    int size = request.getSize() != null ? request.getSize() : 10;
    return size < len;
  }

  private boolean noRequest(CursorPageRequest request){
    for(Field field : request.getClass().getDeclaredFields()){
      field.setAccessible(true);
      if(field.getName().equals("direction") || field.getName().equals("type")) continue;

      try {
        if(field.get(request) != null) return false;
      } catch (IllegalAccessException e){
        e.printStackTrace();
      }
    }

    return true;
  }

  private List<?> find(CursorPageRequest request,EntityPath<?> o) {
    BooleanBuilder builder = new BooleanBuilder();

    if(o.getClass().equals(QEmployee.class)){
      builder = makeEmployeeLog(request);
    }
    else if(o.getClass().equals(QPart.class)){
      builder = makePart(request);
    }
    else if(o.getClass().equals(QBackupLog.class)){
      builder = makeBackupLog(request);
    }
    else if(o.getClass().equals(QUpdateLog.class)){
      builder = makeUpdateLog(request);
    }

    return queryFactory.selectFrom(o).where(builder).fetch();
  }

  private OrderSpecifier<?> sortBy(String direction, String type, EntityPath<?> o){

    if(o.getClass().equals(QEmployee.class)){
      PathBuilder<Employee> pathBuilder = new PathBuilder<>(Employee.class, "employee");
      QEmployee employee = QEmployee.employee;

      if (type == null) {
        return direction == null ? employee.id.desc() :
            direction.equals("desc") ? employee.id.desc() : employee.id.asc();
      } else {
        ComparableExpressionBase<?> path = pathBuilder.getComparable(type, Comparable.class);

        return direction == null ? path.desc() :
            direction.equals("desc") ? path.desc() : path.asc();
      }
    }
    else if(o.getClass().equals(QPart.class)){
      PathBuilder<Part> pathBuilder = new PathBuilder<>(Part.class, "part");
      QPart part = QPart.part;
      if (type == null) {
        return direction == null ? part.partName.desc() :
            direction.equals("desc") ? part.partName.desc() : part.partName.asc();
      } else {
        ComparableExpressionBase<?> path = pathBuilder.getComparable(type, Comparable.class);

        return direction == null ? path.desc() :
            direction.equals("desc") ? path.desc() : path.asc();
      }
    }
    else if(o.getClass().equals(QUpdateLog.class)){
      PathBuilder<UpdateLog> pathBuilder = new PathBuilder<>(UpdateLog.class,"updateLog");
      QUpdateLog updateLog= QUpdateLog.updateLog;

      if (type == null) {
        return direction == null ? updateLog.ip.desc() :
            direction.equals("desc") ? updateLog.ip.desc() : updateLog.ip.asc();
      } else {
        ComparableExpressionBase<?> path = pathBuilder.getComparable(type, Comparable.class);

        return direction == null ? path.desc() :
            direction.equals("desc") ? path.desc() : path.asc();
      }
    }
    else if(o.getClass().equals(QBackupLog.class)){
      PathBuilder<BackupLog> pathBuilder = new PathBuilder<>(BackupLog.class,"backupLog");
      QBackupLog backupLog= QBackupLog.backupLog;

      if (type == null) {
        return direction == null ? backupLog.endTime.desc() :
            direction.equals("desc") ? backupLog.endTime.desc() : backupLog.endTime.asc();
      } else {
        ComparableExpressionBase<?> path = pathBuilder.getComparable(type, Comparable.class);

        return direction == null ? path.desc() :
            direction.equals("desc") ? path.desc() : path.asc();
      }
    }

    return null;
  }
  private BooleanBuilder makeEmployeeLog(CursorPageRequest request){
    BooleanBuilder builder = new BooleanBuilder();

    if (request.getEmployeeId() != null) {
      builder.and(Expressions.stringOperation(Ops.STRING_CAST, QEmployee.employee.employeeNumber)
          .like("%" + request.getEmployeeId().toString() + "%"));
    }
    if (request.getPartName() != null) {
      builder.and(QEmployee.employee.part.partName.like("%" + request.getPartName() + "%"));
    }
    if (request.getName() != null) {
      builder.and(QEmployee.employee.name.like("%" + request.getName() + "%"));
    }
    if (request.getRank() != null) {
      builder.and(QEmployee.employee.rank.stringValue().like("%" + request.getRank().toString() + "%"));
    }
    if (request.getStatus() != null) {
      builder.and(
          QEmployee.employee.status.stringValue().like("%" + request.getStatus().toString() + "%"));
    }

    return builder;
  }

  private BooleanBuilder makeBackupLog(CursorPageRequest request){
    BooleanBuilder builder = new BooleanBuilder();

    if(request.getWorker() != null){
      builder.and(QBackupLog.backupLog.worker.like("%" + request.getWorker() + "%"));
    }
    if (request.getStartTime() != null && request.getEndTime() != null) {
      builder.and(QBackupLog.backupLog.startTime.between(request.getStartTime(), request.getEndTime()));
    } else if (request.getStartTime() != null) {
      builder.and(QBackupLog.backupLog.startTime.goe(request.getStartTime()));
    } else if (request.getEndTime() != null) {
      builder.and(QBackupLog.backupLog.startTime.loe(request.getEndTime()));
    }
    if(request.getBackupStatus() != null){
      builder.and(QBackupLog.backupLog.backupStatus.eq(request.getBackupStatus()));
    }
    if(request.getFileNumber() != null){
      builder.and(QBackupLog.backupLog.fileNumber.eq(request.getFileNumber()));
    }

    return builder;
  }

  private BooleanBuilder makePart(CursorPageRequest request){
    BooleanBuilder builder = new BooleanBuilder();
    if(request.getPartName() != null){
      builder.and(QPart.part.partName.like("%" + request.getPartName() + "%"));
    }
    if(request.getDescription() != null){
      builder.and(QPart.part.explanation.like("%" + request.getDescription() + "%"));
    }
    if(request.getEstablish() != null){
      builder.and(QPart.part.explanation.like("%" + request.getEstablish() + "%"));
    }

    return builder;
  }

  private BooleanBuilder makeUpdateLog(CursorPageRequest request){
    BooleanBuilder builder = new BooleanBuilder();

    if(request.getUpdateTime() != null){
      builder.and(QUpdateLog.updateLog.updateTime.loe(request.getUpdateTime()));
    }
    if(request.getPropertyName() != null){
      builder.and(QUpdateLog.updateLog.propertyName.eq(request.getPropertyName()));
    }
    if(request.getPrevContent() != null){
      builder.and(QUpdateLog.updateLog.prevContent.eq(request.getPrevContent()));
    }
    if(request.getCurrContent() != null){
      builder.and(QUpdateLog.updateLog.currContent.eq(request.getCurrContent()));
    }
    if(request.getMemo() != null){
      builder.and(QUpdateLog.updateLog.memo.like("%" + request.getMemo() + "%"));
    }
    if(request.getIp() != null){
      builder.and(QUpdateLog.updateLog.ip.eq(request.getIp()));
    }
    if(request.getUpdateType() != null){
      builder.and(QUpdateLog.updateLog.updateType.eq(request.getUpdateType()));
    }

    return builder;
  }
}
