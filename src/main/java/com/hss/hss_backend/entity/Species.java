package com.hss.hss_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "species")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Species extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "species_id")
    private Long speciesId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Breed> breeds;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animals;
}
