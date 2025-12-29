package ru.binarysimple.users.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping
//    public PagedModel<UserDto> getAll(@ParameterObject @ModelAttribute UserFilter filter, @ParameterObject Pageable pageable) {
//        Page<UserDto> userDtos = userService.getAll(filter, pageable);
//        return new PagedModel<>(userDtos);
//    }

    @GetMapping(params = {"id"})
    public UserDto getOne(@RequestParam Long id) {
        return userService.getOne(id);
    }

//    @GetMapping("/by-ids")
//    public List<UserDto> getMany(@RequestParam List<Long> ids) {
//        return userService.getMany(ids);
//    }

    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserDto dto) {
        return userService.create(dto);
    }

    @PutMapping(params = {"id"})
    public UserDto put(@RequestParam Long id, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patch(id, patchNode);
    }

//    @PatchMapping
//    public List<Long> patchMany(@RequestParam @Valid List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
//        return userService.patchMany(ids, patchNode);
//    }

    @DeleteMapping(params = {"id"})
    public UserDto delete(@RequestParam Long id) {
        return userService.delete(id);
    }

//    @DeleteMapping
//    public void deleteMany(@RequestParam List<Long> ids) {
//        userService.deleteMany(ids);
//    }
}
