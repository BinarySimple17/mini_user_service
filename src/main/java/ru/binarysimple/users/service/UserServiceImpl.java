package ru.binarysimple.users.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.binarysimple.users.dto.CreateUserDto;
import ru.binarysimple.users.dto.UserDto;
import ru.binarysimple.users.exception.EntityNotFoundException;
import ru.binarysimple.users.filter.UserFilter;
import ru.binarysimple.users.mappers.UserMapper;
import ru.binarysimple.users.model.User;
import ru.binarysimple.users.repository.UserRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDto);
    }

    @Override
    public UserDto getOne(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userMapper.toUserDto(userOptional.orElseThrow(() ->
                new EntityNotFoundException("Entity with id `%s` not found".formatted(id))));
    }

    @Override
    public List<UserDto> getMany(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto create(CreateUserDto dto) {

        logger.debug("UserService create user: {}", dto);

        User user = userMapper.toNewEntity(dto);
        User resultUser = userRepository.save(user);
        return userMapper.toUserDto(resultUser);
    }

    @Override
    public UserDto patch(Long id, JsonNode patchNode) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entity with id `%s` not found".formatted(id)));

        return updateUser(user, patchNode);
//        UserDto userDto = userMapper.toUserDto(user);
//        objectMapper.readerForUpdating(userDto).readValue(patchNode);
//        userMapper.updateWithNull(userDto, user);
//
//        User resultUser = userRepository.save(user);
//        return userMapper.toUserDto(resultUser);
    }

    @Override
    public List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException {
        Collection<User> users = userRepository.findAllById(ids);

        for (User user : users) {
            UserDto userDto = userMapper.toUserDto(user);
            objectMapper.readerForUpdating(userDto).readValue(patchNode);
            userMapper.updateWithNull(userDto, user);
        }

        List<User> resultUsers = userRepository.saveAll(users);
        return resultUsers.stream()
                .map(User::getId)
                .toList();
    }

    @Override
    public UserDto delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteMany(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    @Override
    public UserDto getByUsername(String username) {

        User user = userRepository.findUserByUsername(username).orElse(null);

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateByUsername(String username, JsonNode patchNode) throws IOException {

        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity with username `%s` not found".formatted(username)));

        return updateUser(user, patchNode);
    }

    private UserDto updateUser(User user, JsonNode patchNode) throws IOException {
        UserDto userDto = userMapper.toUserDto(user);
        objectMapper.readerForUpdating(userDto).readValue(patchNode);
        userMapper.updateWithNull(userDto, user);

        User resultUser = userRepository.save(user);
        return userMapper.toUserDto(resultUser);
    }

    @Override
    public UserDto deleteByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElse(null);

        if (user != null) {
            userRepository.delete(user);
        }
        return userMapper.toUserDto(user);
    }
}