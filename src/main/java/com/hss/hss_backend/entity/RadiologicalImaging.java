package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "radiological_imaging")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RadiologicalImaging extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 50)
    private String mimeType;
}
