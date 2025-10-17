package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "report_schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReportSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false, length = 20)
    private Frequency frequency;

    @Column(name = "cron_expression", length = 100)
    private String cronExpression;

    @Column(name = "last_run")
    private LocalDateTime lastRun;

    @Column(name = "next_run")
    private LocalDateTime nextRun;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", length = 50)
    @Builder.Default
    private ReportType reportType = ReportType.GENERAL;

    @Column(name = "parameters", columnDefinition = "jsonb")
    private String parameters; // JSON string for report parameters

    @ElementCollection
    @CollectionTable(name = "report_email_recipients", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "email")
    private List<String> emailRecipients;

    public enum Frequency {
        DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY, CUSTOM
    }

    public enum ReportType {
        GENERAL, FINANCIAL, MEDICAL, INVENTORY, APPOINTMENT
    }
}
