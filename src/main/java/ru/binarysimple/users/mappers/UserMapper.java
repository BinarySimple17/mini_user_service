package ru.binarysimple.users.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.binarysimple.users.dto.CreateUserDto;
import ru.binarysimple.users.dto.UpdateUserDto;
import ru.binarysimple.users.dto.UserDto;
import ru.binarysimple.users.kafka.UserEvent;
import ru.binarysimple.users.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toUserDto(User user);

    UpdateUserDto toUpdateUserDto(User user);

    User toNewEntity(CreateUserDto user);

    User updateWithNull(UpdateUserDto userDto, @MappingTarget User user);

    UserEvent toUserEvent(User user);

}