package com.users.controller;

import com.users.dto.LoginDto;
import com.users.exception.UserException;
import com.users.model.User;
import com.users.dto.UserDto;
import com.users.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
@Api( tags = "Users")
public class UserController implements IUserController {

    private final IUserService userService;

    @ApiOperation(value = "This method is used to create one user.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Validated @RequestBody User user)
            throws UserException {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findById(@PathVariable("userId") String userId) {
        return userService.findById(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable("userId") String userId) {
        userService.delete(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(@Validated @RequestBody UserDto userDto)
            throws UserException {
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    @ResponseStatus(code = HttpStatus.OK)
    public String login(@RequestBody @Valid LoginDto loginDto) {
        return userService.login(loginDto.getEmail(), loginDto.getPassword()).orElseThrow(
                ()->new UserException("Login failed"));
    }
}
