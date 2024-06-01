package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    public User toEntity(UserDto user) {
        return new User(
                user.firstName(),
                user.lastName(),
                user.birthdate(),
                user.email());
    }
    SimpleUserDto toSimpleDto(User user) {
        return new SimpleUserDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    EmailDto toEmailDto(User user) {
        return new EmailDto(user.getId(),
                user.getEmail());
    }

    public User updateToEntity(UserDto userDto, Long id) {
        return new User(
                id,
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }
}
