package com.users.service;

import com.users.exception.UserException;
import com.users.model.User;
import com.users.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User create(final User user) throws UserException {
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(Boolean.TRUE);

        final List<User> users = userRepository.findByEmailContaining(user.getEmail());

        if (!users.isEmpty()) {
            throw new UserException("This email address already exists.");
        }

        return userRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
