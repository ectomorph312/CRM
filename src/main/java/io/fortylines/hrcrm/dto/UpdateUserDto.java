package io.fortylines.hrcrm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpdateUserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String active;

    @NotNull
    private Set roles;
}
