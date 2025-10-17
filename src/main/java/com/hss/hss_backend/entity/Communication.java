package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "communication")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Communication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communication_id")
    private Long communicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", length = 20)
    @Builder.Default
    private CommunicationType communicationType = CommunicationType.EMAIL;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private Status status = Status.SENT;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 10)
    @Builder.Default
    private Priority priority = Priority.NORMAL;

    @Column(name = "response_required")
    @Builder.Default
    private Boolean responseRequired = false;

    @Column(name = "response_received")
    @Builder.Default
    private Boolean responseReceived = false;

    public enum CommunicationType {
        EMAIL, SMS, PHONE, LETTER, IN_PERSON
    }

    public enum Status {
        DRAFT, SENT, DELIVERED, READ, FAILED
    }

    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }
}
