package com.hss.hss_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffUpdateRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    @Pattern(regexp = "^[+]?[0-9\\s\\-()]*$", message = "Phone number format is invalid")
    private String phone;

    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date must be in the past or present")
    private LocalDate hireDate;

    @Past(message = "Termination date must be in the past")
    private LocalDate terminationDate;

    @Size(max = 100, message = "Position must not exceed 100 characters")
    private String position;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @DecimalMin(value = "0.0", message = "Salary must be positive")
    @DecimalMax(value = "999999.99", message = "Salary must not exceed 999999.99")
    private BigDecimal salary;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;

    @Size(max = 20, message = "Emergency contact phone must not exceed 20 characters")
    @Pattern(regexp = "^[+]?[0-9\\s\\-()]*$", message = "Emergency contact phone format is invalid")
    private String emergencyContactPhone;
}
