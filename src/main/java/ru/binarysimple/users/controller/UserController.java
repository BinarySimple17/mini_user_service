package ru.binarysimple.users.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.binarysimple.users.dto.CreateUserDto;
import ru.binarysimple.users.dto.UserDto;
import ru.binarysimple.users.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "CRUD operations for users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestHeader("X-Username") String currentUsername, @RequestBody @Valid CreateUserDto dto) {
        // здесь есть проверка x-username потому что запрос приходит от gateway, который валидировал токен и заполнил x-username
        if (!currentUsername.equals(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied, wrong username");
        }
        return userService.create(dto);
    }


    @GetMapping(params = {"username"})
    public UserDto getOneByUsername(@RequestHeader("X-Username") String currentUsername, @RequestParam String username) {

        if (!currentUsername.equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return userService.getByUsername(username);
    }

    @PutMapping(params = {"username"})
    public UserDto updateByUsername(@RequestHeader("X-Username") String currentUsername, @RequestParam String username, @RequestBody JsonNode patchNode) throws IOException {
        if (!currentUsername.equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return userService.updateByUsername(username, patchNode);
    }

    /**
     * todo через брокера событий дергать авторизацию и блокировать учетку тоже
     *
     * @param currentUsername
     * @param username
     * @return
     * @throws IOException
     */
    @DeleteMapping(params = {"username"})
    public UserDto delete(@RequestHeader("X-Username") String currentUsername, @RequestParam String username) throws IOException {
        if (!currentUsername.equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return userService.setInactiveByUsername(username);
    }
}
