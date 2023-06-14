package com.users.service;

import com.users.exception.UserException;
import com.users.model.User;
import com.users.repository.UserRepository;
import com.users.util.UserUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*?[!$\"" +
            "&\\/()=?¿#%])[A-Za-z0-9!$\"&\\/()=?¿#%]{8,32}$";

    @Override
    public User create(final User user) throws UserException {
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(Boolean.TRUE);

        validations(user, Boolean.FALSE);

        return userRepository.save(user);
    }

    private void validations(final User user, final boolean updating) {
        validateEmail(user.getEmail(), updating);
        validatePassword(user.getPassword());
    }

    private void validateEmail(final String email, final boolean updating) throws UserException {
        final List<User> users = userRepository.findByEmail(email);

        if (!users.isEmpty() && !updating) {
            throw new UserException("This email address already exists.");
        } else if (!UserUtil.patternMatches(email, EMAIL_REGEX)) {
            throw new UserException("The email address is invalid.");
        }
    }

    private void validatePassword(final String password) throws UserException {
    if (!UserUtil.patternMatches(password, PASSWORD_REGEX)) {
            throw new UserException("The password is invalid.");
        }
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(final Long userId) {
        final Optional<User> result = userRepository.findById(userId);
        return result.orElse(null);
    }

    @Override
    public void delete(final Long userId) {
        final Optional<User> result = userRepository.findById(userId);
        final User user = result.orElseThrow(() -> new UserException("This user doesn't exist."));
        userRepository.delete(user);
    }

    @Override
    public User update(final User user) {
        final User userToUpdate = findById(user.getUserId());

        if (userToUpdate == null) {
            throw new UserException("This user Id doesn't exist.");
        }
        user.setModified(LocalDateTime.now());
        validations(user, Boolean.TRUE);
        return userRepository.save(user);
    }

}
