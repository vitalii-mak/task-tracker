package com.github.vitaliimak.TaskTracker.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginVM {
    @NotNull
    @Size(min = 1, max = 255)
    private String email;

    @NotNull
    @Size(min = 8, max = 255)
    private String password;
}
