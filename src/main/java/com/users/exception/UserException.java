package com.users.exception;

import com.users.dto.UserExceptionMessage;

public class UserException extends RuntimeException {
    private UserExceptionMessage message;

    public UserException(String message) {
        super(message);
        final UserExceptionMessage userExceptionMessage =
                new UserExceptionMessage(message);
        this.message = userExceptionMessage;
    }

    public UserExceptionMessage getUserExceptionMessage() {
        return message;
    }

    public UserException() {
    }
}