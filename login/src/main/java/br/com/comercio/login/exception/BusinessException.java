package br.com.comercio.login.exception;

import br.com.comercio.login.dto.ErrorDTO;

public abstract class BusinessException extends RuntimeException{
    public abstract ErrorDTO getError();

}
