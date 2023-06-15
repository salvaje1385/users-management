package com.users.service;

import com.users.dto.UserDto;
import com.users.exception.UserException;
import com.users.model.User;

import java.util.List;

public interface IUserService {

    UserDto create(User user) throws UserException;

    List<UserDto> findAll();

    UserDto findById(final Long userId);

    void delete(final Long userId);

    UserDto update(final UserDto user);
}
