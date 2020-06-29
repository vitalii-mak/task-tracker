package com.github.vitaliimak.TaskTracker.model;

import com.github.vitaliimak.TaskTracker.model.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Size(min = 1, max = 900)
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @ManyToOne
    private User user;
}
