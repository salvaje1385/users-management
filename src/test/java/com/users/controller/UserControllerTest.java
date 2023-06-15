package com.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.dto.LoginDto;
import com.users.dto.UserDto;
import com.users.exception.UserException;
import com.users.mapper.UserMapper;
import com.users.model.Phone;
import com.users.model.User;
import com.users.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    public static final String URL = "/api/v1/users";

    @MockBean
    private IUserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void create_endpoint() throws Exception {
        final User user = createMockUser();
        final UserDto userDto = userMapper.toUserDto(user);
        String content = objectMapper.writeValueAsString(user);

        when(userService.create(any())).thenReturn(userDto);

        mockMvc.perform(post(URL + "/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isCreated());
    }

    @Test
    void create_endpoint_invalid_email() throws Exception {
        final User user = createMockUser();
        user.setEmail("pedro");
        String content = objectMapper.writeValueAsString(user);
        final String errorMessage = "The email is invalid";

        when(userService.create(any())).thenThrow(new UserException(errorMessage));

        mockMvc.perform(post(URL + "/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.message").value(errorMessage));
    }

    @Test
    void login_endpoint() throws Exception {
        final User user = createMockUser();
        final LoginDto loginDto = new LoginDto(user.getEmail(), user.getPassword());
        final String content = objectMapper.writeValueAsString(loginDto);
        final String token = "fd564f6s4df54d";

        when(userService.login(anyString(), anyString()))
                .thenReturn(Optional.of(token));

        mockMvc.perform(post(URL + "/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk());
    }

    @Test
    void findAll() throws Exception {
        final User user = createMockUser();
        final UserDto userDto = userMapper.toUserDto(user);
        final List<UserDto> userDtoList = Arrays.asList(userDto);

        when(userService.findAll())
                .thenReturn(userDtoList);

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    void findById() throws Exception {
        final User user = createMockUser();
        final UserDto userDto = userMapper.toUserDto(user);

        when(userService.findById(anyString()))
                .thenReturn(userDto);

        mockMvc.perform(get(URL + "/{userId}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk());
    }

    @Test
    void delete_user() throws Exception {
        final User user = createMockUser();

        doNothing().when(userService).delete(anyString());

        mockMvc.perform(delete(URL + "/{userId}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk());
    }

    @Test
    void update_endpoint() throws Exception {
        final User user = createMockUser();
        final UserDto userDto = userMapper.toUserDto(user);
        String content = objectMapper.writeValueAsString(userDto);

        when(userService.update(any())).thenReturn(userDto);

        mockMvc.perform(put(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isCreated());
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
