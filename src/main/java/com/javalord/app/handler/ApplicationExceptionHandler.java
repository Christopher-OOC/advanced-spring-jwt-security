package com.javalord.app.handler;

import com.javalord.app.exception.BusinessException;
import com.javalord.app.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {

        final ErrorResponse body = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .build();

        log.info("Business exception: {}", ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return ResponseEntity.status(
                ex.getErrorCode().getStatus() != null
                ? ex.getErrorCode().getStatus() : HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException ex) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.ERR_USER_DISABLED.getCode())
                .message(ErrorCode.ERR_USER_DISABLED.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.ERR_USER_DISABLED.getStatus())
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("Bad credentials exception: {}", ex.getMessage());
        final ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.BAD_CREDENTIAL.getDefaultMessage())
                .code(ErrorCode.BAD_CREDENTIAL.getCode())
                .build();

        return ResponseEntity.status(ErrorCode.ERR_USER_DISABLED.getStatus())
                .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public  ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.info("Username not found exception: {}", ex.getMessage());
        final ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.USER_NOT_FOUND.getDefaultMessage())
                .code(ErrorCode.USER_NOT_FOUND.getCode())
                .build();

        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus())
                .body(response);
    }
}
