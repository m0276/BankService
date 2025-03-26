package MJ.bank.repository;

//https://velog.io/@jkijki12/Spring-QueryDSL-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0

import MJ.bank.entity.Employee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Employee> searchAll(Page page) {

    //JPAQueryFactory query = queryFactory.selectFrom()
    return null;
  }
}
