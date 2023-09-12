package com.api.vaccinationmanagement.exception;

import com.api.vaccinationmanagement.response.ResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {
    // * Xử lý các lỗi còn lại
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseModel<?>> handleAllException(Exception ex, WebRequest request) throws Exception {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 500, ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // * Xử lý lỗi tài khoản chưa kích hoạt
    @ExceptionHandler(DisabledException.class)
    public final ResponseEntity<ResponseModel<?>> handleDisabledException(DisabledException ex, WebRequest request) throws Exception {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 401, "Employee unable", null);
        return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
    }

    // * Xử lý lỗi đối số không phù hợp json
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public final ResponseEntity<ResponseModel<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) throws Exception {
//        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 400, "Value not valid", null);
//        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
//    }

    // * Xử lý lỗi đối số không phù hợp path
    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<ResponseModel<?>> handleNumberFormatException(NumberFormatException ex, WebRequest request) throws Exception {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 400, "Value not valid", null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ResponseModel<?>> handleRuntimeException(RuntimeException ex, WebRequest request) throws Exception {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 400, ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    // * Xử lý lỗi về không có quyền (httpSecurity)
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ResponseModel<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 401, "UNAUTHORIZED", null);
        return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
    }

    // * Xử lý lỗi về không có quyền
    @ExceptionHandler(UnAuthorizationException.class)
    public final ResponseEntity<ResponseModel<?>> handleUnAuthorizationException(UnAuthorizationException ex) throws RuntimeException {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 401, ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
    }

    // * Xử lý lỗi về không tìm thấy
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseModel<?>> handleNotFoundException(NotFoundException ex) throws RuntimeException {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 404, ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }

    // * Xử lý lỗi đối tượng đã tồn tại
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public final ResponseEntity<ResponseModel<?>> handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex) throws RuntimeException {
        ResponseModel<String> responseModel = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), 400, ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    // * Xử lý lỗi về validation input
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            errors.put(key, value);
        });
        ResponseModel<Map<String, String>> model = new ResponseModel<>(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(), "Invalid", errors);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
