package MJ.bank.dto.request;

import MJ.bank.entity.EmployeeStatus;
import MJ.bank.entity.Rank;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursorPageRequest {
  String name;
  String email;
  String partName;
  Rank rank;
  UUID id;
  //LocalDate joinDate;
  EmployeeStatus status;
  String sortType;
  Integer size;
  String sortDirection;
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
