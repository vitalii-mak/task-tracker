package com.github.vitaliimak.TaskTracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 1, max = 255)
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    @Column(name = "password")
    private String password;
}
