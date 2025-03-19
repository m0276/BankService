package MJ.bank.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BackupController {

//  총 페이지 개수 = Math.ceil(전체 컨텐츠 개수 / 한 페이지에 보여줄 컨텐츠의 개수)
//
//  화면에 보여질 페이지 그룹 = Math.ceil(현재 페이지 번호 / 한 화면에 보여줄 페이지의 개수)
//
//  화면에 보여질 페이지의 첫번째 페이지 번호 = ((페이지 그룹 번호 - 1) * 한 화면에 보여줄 페이지의 개수) + 1
//
//  화면에 보여질 페이지의 마지막 페이지 번호 = 페이지 그룹 번호 * 한 화면에 보여줄 페이지의 개수
//
//  단, 페이지 그룹 번호 * 한 화면에 보여줄 페이지의 개수가 전체 페이지 개수보다 크다면 전체 페이지가 된다

}
