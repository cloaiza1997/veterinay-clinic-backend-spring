package com.test.veterinary.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.veterinary.clinic.helper.GeneralTypes;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Generated
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "{validation.user.firstName.NotNull}")
    @NotEmpty(message = "{validation.user.firstName.NotNull}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "{validation.user.lastName.NotNull}")
    @NotEmpty(message = "{validation.user.lastName.NotNull}")
    private String lastName;

    @Column(name = "document_type", nullable = false)
    @NotNull(message = "{validation.user.documentType.NotNull}")
    @Enumerated(EnumType.STRING)
    private GeneralTypes.DocTypes documentType;

    @Column(name = "document_number", nullable = false, unique = true)
    @NotNull(message = "{validation.user.documentNumber.NotNull}")
    private Integer documentNumber;

    @Column(name = "status")
    @NotNull(message = "{validation.user.status.NotNull}")
    private Integer status;

    @Column(name = "gender", nullable = false)
    @NotNull(message = "{validation.user.gender.NotNull}")
    @Enumerated(EnumType.STRING)
    private GeneralTypes.Gender gender;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Pet> pets;
}
