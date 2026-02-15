package com.javalord.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id: %s", HttpStatus.NOT_FOUND),

    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Current password and new password not the same", HttpStatus.BAD_REQUEST),

    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "Current password is invalid", HttpStatus.BAD_REQUEST),

    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED", "Account has been deactivated", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
