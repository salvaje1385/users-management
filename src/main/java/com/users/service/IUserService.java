package com.users.service;

import com.users.exception.UserException;
import com.users.model.User;

public interface IUserService {

    User create(User user) throws UserException;

    Iterable<User> findAll();
}
