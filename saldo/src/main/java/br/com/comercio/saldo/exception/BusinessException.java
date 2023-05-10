package br.com.comercio.saldo.exception;

import br.com.comercio.saldo.dto.ErrorDTO;

public abstract class BusinessException extends RuntimeException{
    public abstract ErrorDTO getError();

}
