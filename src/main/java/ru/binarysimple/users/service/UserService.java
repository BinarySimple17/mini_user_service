package ru.binarysimple.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.binarysimple.users.dto.CreateUserDto;
import ru.binarysimple.users.dto.UserDto;
import ru.binarysimple.users.filter.UserFilter;
import java.io.IOException;
import java.util.List;

public interface UserService {

    /**
     * Получить страницу пользователей с фильтрацией.
     */
    Page<UserDto> getAll(UserFilter filter, Pageable pageable);

    /**
     * Получить одного пользователя по ID.
     * @throws ru.binarysimple.users.exception.EntityNotFoundException если не найден
     */
    UserDto getOne(Long id);

    /**
     * Получить несколько пользователей по списку ID.
     */
    List<UserDto> getMany(List<Long> ids);

    /**
     * Создать нового пользователя.
     */
    UserDto create(CreateUserDto dto);

    /**
     * Частично обновить пользователя по ID (патч).
     */
    UserDto patch(Long id, com.fasterxml.jackson.databind.JsonNode patchNode) throws IOException;

    /**
     * Частично обновить несколько пользователей.
     */
    List<Long> patchMany(List<Long> ids, com.fasterxml.jackson.databind.JsonNode patchNode) throws IOException;

    /**
     * Удалить пользователя по ID.
     * @return DTO удалённого пользователя (или null, если не найден)
     */
    UserDto delete(Long id);

    /**
     * Удалить несколько пользователей по ID.
     */
    void deleteMany(List<Long> ids);
}