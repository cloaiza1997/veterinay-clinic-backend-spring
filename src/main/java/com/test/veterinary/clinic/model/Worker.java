package com.test.veterinary.clinic.model;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Generated
@Entity
@Getter
@Setter
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "document_type", nullable = false)
    private Character documentType;

    @Column(name = "document_number", nullable = false)
    private Integer documentNumber;

    @Column(name = "position_name", nullable = false)
    private Integer positionName;

    @Column(name = "speciality", nullable = false)
    private Character speciality;

    @OneToMany(mappedBy = "worker")
    private List<ClinicHistoryDetail> clinicHistoryDetails;
}
