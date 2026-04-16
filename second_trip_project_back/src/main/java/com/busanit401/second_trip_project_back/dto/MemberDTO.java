package com.busanit401.second_trip_project_back.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String mid;
    private String mpw;
    private String mname;
    private String email;
    private String phone;
    private String role; // "USER" 또는 "ADMIN"
    private LocalDateTime regDate;
}