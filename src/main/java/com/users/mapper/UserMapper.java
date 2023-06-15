package com.users.mapper;

import com.users.dto.UserDto;
import com.users.model.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    User toUser(UserDto userDto);

    List<User> toUsers(List<UserDto> userDtos);
}
