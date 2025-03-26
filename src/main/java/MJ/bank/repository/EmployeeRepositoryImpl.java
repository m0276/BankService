package MJ.bank.repository;

//https://velog.io/@jkijki12/Spring-QueryDSL-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0

import MJ.bank.component.MakeSlice;
import MJ.bank.dto.request.CursorPageRequest;
import MJ.bank.entity.Employee;
import MJ.bank.entity.QEmployee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{

  QEmployee employee = QEmployee.employee;
  MakeSlice makeSlice;

  // Base sort : id
  @Override
  public Slice<?> searchAll(CursorPageRequest request, Pageable page) {
    return makeSlice.findAll(request,page,employee);
  }

}

//
//- **{이름 또는 이메일}**, **{부서}**, **{직함}**, **{사원번호}**, **{입사일}**, **{상태}**로 직원 목록을 조회할 수 있습니다.
//    - **{이름 또는 이메일}**, **{부서}**, **{직함}**, **{사원번호}**는 부분 일치 조건입니다.
//    - **{입사일}**은 범위 조건입니다.
//    - **{상태}**는 완전 일치 조건입니다.
//    - 조회 조건이 여러 개인 경우 모든 조건을 만족한 결과로 조회합니다.
//    - **{이름}**, **{입사일}**, **{사원번호}**로 정렬 및 페이지네이션을 구현합니다.
//    - 여러 개의 정렬 조건 중 선택적으로 1개의 정렬 조건만 가질 수 있습니다.
//    - 정확한 페이지네이션을 위해 **{이전 페이지의 마지막 요소 ID}**를 활용합니다.
//    - 화면을 고려해 적절한 페이지네이션 전략을 선택합니다.