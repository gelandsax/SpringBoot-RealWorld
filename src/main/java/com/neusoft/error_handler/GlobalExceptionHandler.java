package com.neusoft.error_handler;

import com.neusoft.exception.AuthFailException;
import com.neusoft.exception.UserExistException;
import com.neusoft.response.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(UserExistException.class)
    public ApiResponse<String> handldUserExistException(UserExistException exception){
        return ApiResponse.error(510, exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handldError(Exception exception){
        return ApiResponse.error(500, exception.getMessage());
    }
    @ExceptionHandler(AuthFailException.class)
    public ApiResponse<String> handldAuthFailException(AuthFailException exception){
        return ApiResponse.error(401, exception.getMessage());
    }
}
