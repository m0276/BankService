package MJ.bank.component;


import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Employee;
import MJ.bank.entity.Part;
import MJ.bank.entity.QEmployee;
import MJ.bank.entity.QPart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.lang.reflect.Field;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MakeSlice {

  JPAQueryFactory queryFactory;

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
      builder = makeEmployeeBuilder(request, builder, QEmployee.employee);
    }
    else if(o.getClass().equals(QPart.class)){
      builder = makePartBuilder(request,builder,QPart.part);
    }


    return queryFactory.selectFrom(o).where(builder).fetch();
  }

  private OrderSpecifier<?> sortBy(String direction, String type, EntityPath<?> o){
    PathBuilder<Employee> pathBuilder = new PathBuilder<>(Employee.class, "employee");

    if(o.getClass().equals(QEmployee.class)){
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

    return null;
  }

  private BooleanBuilder makeEmployeeBuilder(CursorPageRequest request, BooleanBuilder builder, QEmployee employee){
    if (request.getEmployeeId() != null) {
      builder.and(Expressions.stringOperation(Ops.STRING_CAST, employee.employeeNumber)
          .like("%" + request.getEmployeeId().toString() + "%"));
    }
    if (request.getPartName() != null) {
      builder.and(employee.part.partName.like("%" + request.getPartName() + "%"));
    }
    if (request.getName() != null) {
      builder.and(employee.name.like("%" + request.getName() + "%"));
    }
    if (request.getRank() != null) {
      builder.and(employee.rank.stringValue().like("%" + request.getRank().toString() + "%"));
    }
    if (request.getStatus() != null) {
      builder.and(employee.status.stringValue().like("%" + request.getStatus().toString() + "%"));
    }

    return builder;
  }

  private BooleanBuilder makePartBuilder(CursorPageRequest request, BooleanBuilder builder, QPart part){
    if(request.getPartName() != null){
      builder.and(part.partName.like("%" + request.getPartName() + "%"));
    }
    if(request.getDescription() != null){
      builder.and(part.explanation.like("%" + request.getDescription() + "%"));
    }

    return builder;
  }

}
