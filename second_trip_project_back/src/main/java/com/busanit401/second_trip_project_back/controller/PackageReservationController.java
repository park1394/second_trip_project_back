package com.busanit401.second_trip_project_back.controller;

import com.busanit401.second_trip_project_back.dto.PackageReservationDTO;
import com.busanit401.second_trip_project_back.service.PackageReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Log4j2
public class PackageReservationController {

    private final PackageReservationService packageReservationService;

    @PostMapping("/")
    public ResponseEntity<Long> register(
            @Valid @RequestBody PackageReservationDTO packageReservationDto,
            Principal principal) {

        // 1. 요청이 여기까지 오는지 확인
        log.info("📢 예약 컨트롤러 진입 성공!");

        // 2. Principal이 제대로 넘어오는지 확인
        if (principal == null) {
            log.error("❌ 에러: Principal이 null입니다. (로그인 토큰이 전달되지 않음)");
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }

        log.info("예약 요청 데이터: " + packageReservationDto);

        String email = principal.getName();

        Long reservationId = packageReservationService.register(packageReservationDto, email);

        return ResponseEntity.ok(reservationId);
    }
}