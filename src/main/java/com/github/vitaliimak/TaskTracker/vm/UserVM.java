package com.github.vitaliimak.TaskTracker.vm;

import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserVM extends UserDto {
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;
}
