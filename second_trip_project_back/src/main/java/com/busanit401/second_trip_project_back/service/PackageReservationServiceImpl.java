package com.busanit401.second_trip_project_back.service;

import com.busanit401.second_trip_project_back.domain.member.Member;
import com.busanit401.second_trip_project_back.domain.packages.PackageItem;
import com.busanit401.second_trip_project_back.domain.packages.PackageReservation;
import com.busanit401.second_trip_project_back.dto.PackageReservationDTO;
import com.busanit401.second_trip_project_back.enums.ReservationStatus;
import com.busanit401.second_trip_project_back.repository.MemberRepository;
import com.busanit401.second_trip_project_back.repository.PackageItemRepository;
import com.busanit401.second_trip_project_back.repository.PackageReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PackageReservationServiceImpl implements PackageReservationService {

    private final MemberRepository memberRepository;
    private final PackageItemRepository packageItemRepository;
    private final PackageReservationRepository reservationRepository;


    @Override
    public Long register(PackageReservationDTO packageReservationDto, String email) {

        // 1. 이메일로 회원 정보 조회
        Member member = memberRepository.findByMid(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 패키지 정보 조회
        PackageItem packageItem = packageItemRepository.findById((long) packageReservationDto.getPackageId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 패키지입니다."));


        // 3. 예약 생성
        PackageReservation reservation = PackageReservation.builder()
                .member(member)
                .packageItem(packageItem)
                .reservationDate(packageReservationDto.getReservationDate())
                .peopleCount(packageReservationDto.getPeopleCount())
                .totalPrice(packageReservationDto.getTotalPrice())
                .status(ReservationStatus.PENDING)
                .build();

        return (long) reservationRepository.save(reservation).getId();
    }
}