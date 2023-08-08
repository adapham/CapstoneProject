package vn.com.edu.fpt.capstoneproject.exception;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.edu.fpt.capstoneproject.model.error.ErrorResponse;

import java.util.Objects;


@ControllerAdvice
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.info(message);
        ErrorResponse response = new ErrorResponse();
        response.setCode("APP-01");
        response.setDescription(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ErrorResponse> handleSecurity(AppException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode("APP-69");
        errorResponse.setDescription(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }
}
