package com.example.demo.configs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerHandlerException {
    
        @ExceptionHandler(RuntimeException.class)
        @ResponseBody
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        //在这个方法里定义我们需要返回的格式
        public Map<String, Object> handleUserNotExistException(RuntimeException ex){
            Map<String, Object> result = new HashMap<>();
            result.put("message", ex.getMessage());
            return result;
        }
 
}