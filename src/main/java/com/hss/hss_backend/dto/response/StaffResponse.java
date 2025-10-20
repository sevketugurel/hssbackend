package com.hss.hss_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private Long staffId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private Boolean active;
    private String position;
    private String department;
    private BigDecimal salary;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
