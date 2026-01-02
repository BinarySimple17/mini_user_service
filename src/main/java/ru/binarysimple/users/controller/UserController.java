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

//    @GetMapping(params = {"id"})
//    public UserDto getOne(@RequestParam Long id) {
//        return userService.getOne(id);
//    }

    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserDto dto) {
        return userService.create(dto);
    }

//    @PutMapping(params = {"id"})
//    public UserDto put(@RequestParam Long id, @RequestBody JsonNode patchNode) throws IOException {
//        return userService.patch(id, patchNode);
//    }


//    @DeleteMapping(params = {"id"})
//    public UserDto delete(@RequestParam Long id) {
//        return userService.delete(id);
//    }

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

    @DeleteMapping(params = {"username"})
    public UserDto delete(@RequestHeader("X-Username") String currentUsername, @RequestParam String username) throws IOException {
        if (!currentUsername.equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return userService.setInactiveByUsername(username);
    }
}
