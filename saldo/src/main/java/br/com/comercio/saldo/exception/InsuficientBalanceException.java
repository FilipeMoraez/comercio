package br.com.comercio.saldo.exception;

import br.com.comercio.saldo.dto.ErrorDTO;

import java.util.Date;

public class InsuficientBalanceException extends BusinessException{
    @Override
    public ErrorDTO getError() {
        return new ErrorDTO("ER03", "Saldo Insuficiente", new Date());
    }
}
