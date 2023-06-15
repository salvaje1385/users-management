package com.users.service;

import com.users.config.JwtTokenProvider;
import com.users.exception.UserException;
import com.users.mapper.UserMapper;
import com.users.dto.UserDto;
import com.users.model.Phone;
import com.users.model.User;
import com.users.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    private User user;

    @BeforeEach
    public void init() {
        userMapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper,
                authenticationManager, tokenProvider, passwordEncoder);
        user = user = createMockUser();
    }

    @Test
    void createUser() {
        when(userRepository.save(any())).thenReturn(user);

        final UserDto response = userService.create(user);

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getPassword(), response.getPassword());
        assertNotNull(user.getPhones());
        assertTrue(!user.getPhones().isEmpty());
        assertEquals(user.getPhones().get(0).getNumber(), response.getPhones().get(0).getNumber());
    }

    @Test
    void createUser_invalid_email() {
        user.setPassword("pgonzalez");

        final UserException userException = assertThrows(UserException.class,
                () -> userService.create(user));
        assertTrue(userException.getMessage().contains("is invalid"));
    }

    @Test
    void login() {
        final String token = "df56a1f4d56fd56";
        final Optional<User> userOpt = Optional.of(user);
        when(userRepository.findByEmail(anyString())).thenReturn(userOpt);
        when(tokenProvider.createToken(anyString())).thenReturn(token);
        when(userRepository.save(any())).thenReturn(user);

        final Optional<String> response =
                userService.login(user.getEmail(), user.getPassword());

        assertTrue(response.isPresent());
    }

    @Test
    void findAll() {
        final Iterable<User> userIterable = Arrays.asList(user);

        when(userRepository.findAll()).thenReturn(userIterable);

        final List<UserDto> response = userService.findAll();

        assertNotNull(response);
        assertEquals(user.getEmail(), response.get(0).getEmail());
    }

    @Test
    void findById() {
        final Optional<User> userOpt = Optional.of(user);

        when(userRepository.findById(anyString())).thenReturn(userOpt);

        final UserDto response = userService.findById(user.getUserId());

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void delete() {
        final Optional<User> userOpt = Optional.of(user);

        when(userRepository.findById(anyString())).thenReturn(userOpt);

        userService.delete(user.getUserId());

        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void update() {
        final UserDto userDto = userMapper.toUserDto(user);
        final Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById(anyString())).thenReturn(userOpt);
        when(userRepository.save(any())).thenReturn(user);

        final UserDto response = userService.update(userDto);

        assertNotNull(response);
        assertEquals(user.getEmail(), response.getEmail());
    }

    private User createMockUser() {
        final User user = new User();
        user.setUserId("f5d4d564fd564");
        user.setName("Pedro Gonzalez");
        user.setEmail("pgonzalez@gmail.com");
        user.setPassword("pgonD!fs1588");

        final Phone phone = new Phone();
        phone.setNumber("81451615");
        phone.setCitycode("1");
        phone.setCountrycode("57");

        user.setPhones(Arrays.asList(phone));
        return user;
    }
}
