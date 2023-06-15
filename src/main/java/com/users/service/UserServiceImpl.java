package com.users.service;

import com.users.dto.UserDto;
import com.users.exception.UserException;
import com.users.mapper.UserMapper;
import com.users.model.User;
import com.users.repository.UserRepository;
import com.users.config.JwtTokenProvider;
import com.users.util.UserUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*?[!$\"" +
            "&\\/()=?¿#%])[A-Za-z0-9!$\"&\\/()=?¿#%]{8,32}$";

    @Override
    public UserDto create(final User user) throws UserException {
        validations(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(Boolean.TRUE);

        return userMapper.toUserDto(userRepository.save(user));
    }

    private void validations(final User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validateEmail(final String email) throws UserException {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
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
    public List<UserDto> findAll() {
        final Iterable<User> iterable = userRepository.findAll();
        List<User> result = new ArrayList<>();
        iterable.forEach(element -> result.add(element));
        return userMapper.toUserDtos(result);
    }

    @Override
    public UserDto findById(final String userId) {
        final Optional<User> result = userRepository.findById(userId);
        return userMapper.toUserDto(result.orElse(null));
    }

    private User findUserById(final String userId) {
        final Optional<User> result = userRepository.findById(userId);
        return result.orElse(null);
    }

    @Override
    public void delete(final String userId) {
        final Optional<User> result = userRepository.findById(userId);
        final User user = result.orElseThrow(() -> new UserException("This user doesn't exist."));
        userRepository.delete(user);
    }

    @Override
    public UserDto update(final UserDto userDto) {
        final User user = findUserById(userDto.getUserId());

        if (user == null) {
            throw new UserException("This user Id doesn't exist.");
        }

        final User userMapped = buildUser(userDto, user);
        return userMapper.toUserDto(userRepository.save(userMapped));
    }

    private User buildUser(final UserDto userDto, final User user) {
        final User userMapped = userMapper.toUser(userDto);
        userMapped.setEmail(user.getEmail());
        userMapped.setPassword(user.getPassword());
        userMapped.setCreated(user.getCreated());
        userMapped.setLastLogin(user.getLastLogin());
        userMapped.setToken(user.getToken());
        userMapped.setIsActive(user.getIsActive());
        userMapped.setModified(LocalDateTime.now());
        return userMapped;
    }

    @Override
    public Optional<String> login(String email, String password) {
        Optional<String> token = Optional.empty();
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseThrow(
                () -> new UserException("The User does not exist"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            token = Optional.of(createToken(email));
            user.setLastLogin(LocalDateTime.now());
            user.setToken(token.get());
            userRepository.save(user);
        } catch (AuthenticationException e){
            throw new UserException(e.getMessage());
        }
        return token;
    }

    private String createToken(String email) {
        return tokenProvider.createToken(email);
    }

}
