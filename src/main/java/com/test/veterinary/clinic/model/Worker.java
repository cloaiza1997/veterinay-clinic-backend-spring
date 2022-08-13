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
    @NotNull(message = "{validation.worker.firstName.NotNull}")
    @NotEmpty(message = "{validation.worker.firstName.NotNull}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "{validation.worker.firstName.NotNull}")
    @NotEmpty(message = "{validation.worker.firstName.NotNull}")
    private String lastName;

    @Column(name = "document_type", nullable = false)
    @NotNull(message = "{validation.worker.documentType.NotNull}")
    @Enumerated(EnumType.STRING)
    private GeneralTypes.DocTypes documentType;

    @Column(name = "document_number", nullable = false, unique = true)
    @NotNull(message = "{validation.worker.documentNumber.NotNull}")
    private Integer documentNumber;

    @Column(name = "position_name", nullable = false)
    @NotNull(message = "{validation.worker.positionName.NotNull}")
    @NotEmpty(message = "{validation.worker.positionName.NotNull}")
    private String positionName;

    @Column(name = "speciality", nullable = false)
    @NotNull(message = "{validation.worker.speciality.NotNull}")
    @NotEmpty(message = "{validation.worker.speciality.NotNull}")
    private String speciality;

    @OneToMany(mappedBy = "worker")
    @JsonBackReference
    private List<ClinicHistoryDetail> clinicHistoryDetails;
}
