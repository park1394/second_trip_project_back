package com.busanit401.second_trip_project_back.controller;

import com.busanit401.second_trip_project_back.dto.MemberDTO;
import com.busanit401.second_trip_project_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member") // 이 주소로 요청을 보낼 거야!
public class MemberController {

    private final MemberService memberService;

    // 1. 회원가입 창구 (POST 방식)
    @PostMapping("/register")
    public String register(@RequestBody MemberDTO memberDTO) {
        log.info("회원가입 요청 들어옴! 데이터: " + memberDTO);
        memberService.register(memberDTO);
        return "success";
    }

    // 2. 회원정보 조회 창구 (GET 방식)
    @GetMapping("/{mid}")
    public MemberDTO read(@PathVariable("mid") String mid) {
        log.info("회원조회 요청 들어옴! 아이디: " + mid);
        return memberService.read(mid);
    }

    @PostMapping("/login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {
        log.info("로그인 시도 아이디: " + memberDTO.getMid());

        // 서비스의 로그인 호출!
        return memberService.login(memberDTO.getMid(), memberDTO.getMpw());
    }
}