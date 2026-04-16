package com.busanit401.second_trip_project_back.service;

import com.busanit401.second_trip_project_back.domain.member.Member;
import com.busanit401.second_trip_project_back.domain.member.MemberRole;
import com.busanit401.second_trip_project_back.dto.MemberDTO;
import com.busanit401.second_trip_project_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(MemberDTO memberDTO) {
        Member member = Member.builder()
                .mid(memberDTO.getMid())
                .mpw(passwordEncoder.encode(memberDTO.getMpw())) // 암호화 저장!
                .mname(memberDTO.getMname())
                .email(memberDTO.getEmail())
                .phone(memberDTO.getPhone())
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);
    }

    @Override
    public MemberDTO read(String mid) {
        Optional<Member> result = memberRepository.findByMid(mid);
        Member member = result.orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        return entityToDTO(member);
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findByMid(memberDTO.getMid());
        Member member = result.orElseThrow(() -> new RuntimeException("수정할 회원이 없습니다."));
        member.changeMname(memberDTO.getMname());
        member.changeEmail(memberDTO.getEmail());
        member.changePhone(memberDTO.getPhone());
        memberRepository.save(member);
    }

    @Override
    public void remove(String mid) {
        Member member = memberRepository.findByMid(mid)
                .orElseThrow(() -> new RuntimeException("삭제할 회원이 없습니다."));
        memberRepository.deleteById(member.getId());
    }

    // ⭐ 로그인 메소드는 딱 이 '하나'만 남겨둬야 해!
    @Override
    public MemberDTO login(String mid, String mpw) {
        Member member = memberRepository.findByMid(mid)
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(mpw, member.getMpw())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return entityToDTO(member);
    }

    @Override
    public Optional<Member> findByMid(String mid) {
        return memberRepository.findByMid(mid);
    }

    @Override
    public boolean existsByMid(String mid) {
        return memberRepository.existsByMid(mid);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    private MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mid(member.getMid())
                .mpw(member.getMpw())
                .mname(member.getMname())
                .email(member.getEmail())
                .phone(member.getPhone())
                .role(member.getRole().name())
                .regDate(member.getRegDate())
                .build();
    }
}