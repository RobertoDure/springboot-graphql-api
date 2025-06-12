package com.ie.graphql.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    private Date dateOfBirth;

    @NotNull(message = "Classroom ID cannot be null")
    private int idClassroom;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    @NotEmpty(message = "Nationality cannot be empty")
    private String nationality;

    @NotNull(message = "Teacher ID cannot be null")
    private Long idTeacher;

    @NotNull(message = "Guardian name cannot be empty")
    private String guardianName;

    @NotNull(message = "Guardian contact cannot be empty")
    private String guardianContact;
}