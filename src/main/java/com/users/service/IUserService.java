package com.users.service;

import com.users.exception.UserException;
import com.users.model.User;

public interface IUserService {

    User create(User user) throws UserException;

    Iterable<User> findAll();

    User findById(final Long userId);

    void delete(final Long userId);

    User update(final User user);
}
