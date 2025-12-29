package ru.binarysimple.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.binarysimple.users.model.User;

/**
 * DTO for {@link User}
 */
@Value
public class UpdateUserDto {
    @NotNull
    @NotEmpty
    String firstName;
    String lastName;
    @NotNull
    @Email
    @NotEmpty
    String email;
    String phone;
}