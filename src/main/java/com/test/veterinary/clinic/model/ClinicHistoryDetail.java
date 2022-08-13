package com.test.veterinary.clinic.model;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Generated
@Entity
@Getter
@Setter
@Table(name = "clinic_histories_detail")
public class ClinicHistoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "heart_rate", nullable = false)
    private Double heartRate;

    @Column(name = "breathing_rate", nullable = false)
    private Double breathingRate;

    @Column(name = "date_time", insertable = false, updatable = false)
    private Date dateTime;

    @Column(name = "feeding", nullable = false)
    @NotNull(message = "{validation.clinicHistoryDetail.feeding.NotNull}")
    @NotEmpty(message = "{validation.clinicHistoryDetail.feeding.NotNull}")
    private String feeding;

    @Column(name = "habitat", nullable = false)
    @NotNull(message = "{validation.clinicHistoryDetail.habitat.NotNull}")
    @NotEmpty(message = "{validation.clinicHistoryDetail.habitat.NotNull}")
    private String habitat;

    @Column(name = "observations", nullable = false)
    private String observations;

    @Column(name = "worker_id", nullable = false)
    @NotNull(message = "{validation.clinicHistoryDetail.workerId.NotNull}")
    private Long workerId;

    @Column(name = "clinic_history_id", nullable = false)
    @NotNull(message = "{validation.clinicHistoryDetail.clinicHistoryId.NotNull}")
    private Long clinicHistoryId;

    @ManyToOne
    @JoinColumn(name = "worker_id", insertable = false, updatable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "clinic_history_id", insertable = false, updatable = false)
    private ClinicHistory clinicHistory;
}
