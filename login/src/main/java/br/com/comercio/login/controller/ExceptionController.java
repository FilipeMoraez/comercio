package br.com.comercio.login.controller;

import br.com.comercio.login.dto.ErrorDTO;
import br.com.comercio.login.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorDTO> resourceBusinessException(BusinessException ex, WebRequest request) {
        return new ResponseEntity<ErrorDTO>(ex.getError(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorDTO> internal(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<ErrorDTO>(ErrorDTO.internal(), HttpStatus.NOT_FOUND);
    }
}
