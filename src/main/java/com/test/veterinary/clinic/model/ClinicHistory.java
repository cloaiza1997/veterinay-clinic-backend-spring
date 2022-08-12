package com.test.veterinary.clinic.model;

import lombok.*;

import javax.persistence.*;
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
    private String petId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    @OneToMany(mappedBy = "clinicHistory")
    private List<ClinicHistoryDetail> clinicHistoryDetails;
}
