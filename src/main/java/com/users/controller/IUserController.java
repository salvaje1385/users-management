package com.users.controller;

import com.users.dto.UserDto;
import com.users.exception.UserException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserController {

    @ApiOperation(value = "This method is used to get the users.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token ex. Bearer XXXX",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    List<UserDto> findAll();

    @ApiOperation(value = "This method is used to get one user.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token ex. Bearer XXXX",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    UserDto findById(String userId);

    @ApiOperation(value = "This method is used to delete one user.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token ex. Bearer XXXX",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    void deleteById(@PathVariable("userId") String userId);

    @ApiOperation(value = "This method is used to update one user.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token ex. Bearer XXXX",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    ResponseEntity<UserDto> update(@Validated @RequestBody UserDto userDto)
            throws UserException;
}
