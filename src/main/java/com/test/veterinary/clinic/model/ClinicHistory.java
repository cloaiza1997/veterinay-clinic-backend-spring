package com.test.veterinary.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Generated
@Entity
@Getter
@Setter
@Table(name = "clinic_histories")
public class ClinicHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pet_id", nullable = false)
    @NotNull(message = "{validation.pet.petId.NotNull}")
    private Long petId;

    @Column(name = "created_at", insertable = false)
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    @OneToMany(mappedBy = "clinicHistory")
    @JsonBackReference
    private List<ClinicHistoryDetail> clinicHistoryDetails;
}
