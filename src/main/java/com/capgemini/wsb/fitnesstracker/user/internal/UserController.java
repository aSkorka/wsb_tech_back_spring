package com.capgemini.wsb.fitnesstracker.user.internal;


import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    private final TrainingServiceImpl trainingService;

    @GetMapping("full")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("simple")
    public List<SimpleUserDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }


    @GetMapping("single/{id}")
    public UserDto getSingleUser(@PathVariable long id) {
        return userService.getUser(id).map(userMapper::toDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found"));
    }


    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        if (userService.getUser(id).isPresent()) {
            trainingService.removeTraining(id);
            userService.deleteUser(id);
        }
    }

    @GetMapping("email")
    public List<EmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email).stream().map(userMapper::toEmailDto).toList();

    }

    @GetMapping("older_then/{age}")
    public List<UserDto> getUserOlderThen(@PathVariable("age") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return userService.getOlderThen(date).stream().map(userMapper::toDto).toList();
    }


    @PutMapping("update/{id}")
    public User updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        return userService.updateUser(userMapper.updateToEntity(userDto, id));


    }
}