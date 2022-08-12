package com.test.veterinary.clinic.model;

import lombok.*;

import javax.persistence.*;
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
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "document_type", nullable = false)
    private Character documentType;

    @Column(name = "document_number", nullable = false)
    private Integer documentNumber;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "gender", nullable = false)
    private Character gender;

    @OneToMany(mappedBy = "user")
    private List<Pet> pets;
}
