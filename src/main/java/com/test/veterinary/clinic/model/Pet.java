package com.test.veterinary.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.veterinary.clinic.helper.GeneralTypes;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Generated
@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "{validation.pet.name.NotNull}")
    @NotEmpty(message = "{validation.pet.name.NotNull}")
    private String name;

    @Column(name = "breed", nullable = false)
    @NotNull(message = "{validation.pet.breed.NotNull}")
    @NotEmpty(message = "{validation.pet.breed.NotNull}")
    private String breed;

    @Column(name = "gender", nullable = false)
    @NotNull(message = "{validation.pet.gender.NotNull}")
    @Enumerated(EnumType.STRING)
    private GeneralTypes.Gender gender;

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "{validation.worker.userId.NotNull}")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "pet")
    @JsonBackReference
    private ClinicHistory clinicHistory;
}
