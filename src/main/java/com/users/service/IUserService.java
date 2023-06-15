package com.users.service;

import com.users.dto.UserDto;
import com.users.exception.UserException;
import com.users.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDto create(User user) throws UserException;

    List<UserDto> findAll();

    UserDto findById(final String userId);

    void delete(final String userId);

    UserDto update(final UserDto user);

    Optional<String> login(String email, String password);
}
