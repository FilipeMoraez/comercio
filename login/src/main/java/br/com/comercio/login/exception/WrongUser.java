package br.com.comercio.login.exception;

import br.com.comercio.login.dto.ErrorDTO;

import java.util.Date;

public class WrongUser extends BusinessException {
    @Override
    public ErrorDTO getError() {
        return new ErrorDTO("ER02", "Senha ou usuário inválido", new Date());
    }
}
