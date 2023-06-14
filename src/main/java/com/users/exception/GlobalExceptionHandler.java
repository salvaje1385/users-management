package com.users.exception;

import com.users.dto.UserExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity userException(UserException userException) {
        return new ResponseEntity<UserExceptionMessage>(
                userException.getUserExceptionMessage(), HttpStatus.NOT_FOUND);
    }

}
