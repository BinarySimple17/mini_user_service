package ru.binarysimple.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.binarysimple.users.model.User;

/**
 * DTO for {@link User}
 */
@NoArgsConstructor
@Data
public class CreateUserDto {

    @NotNull
    @Size(max = 256)
    @NotEmpty
    String username;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    @Email
    @NotEmpty
    String email;

    @NotNull
    String phone;
}