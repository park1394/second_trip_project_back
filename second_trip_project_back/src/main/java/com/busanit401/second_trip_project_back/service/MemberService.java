package com.busanit401.second_trip_project_back.service;

import com.busanit401.second_trip_project_back.dto.MemberDTO;

public interface MemberService {
    // 회원가입
    void register(MemberDTO memberDTO);

    // 회원 정보 조회
    MemberDTO read(String mid);

    // 회원 수정
    void modify(MemberDTO memberDTO);

    // 회원 삭제
    void remove(String mid);

    // 기존 코드 아래에 추가
    MemberDTO login(String mid, String mpw);
}