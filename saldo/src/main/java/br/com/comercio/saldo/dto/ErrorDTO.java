package br.com.comercio.saldo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {
    private String code;
    private String description;
    private Date date;

    public static ErrorDTO  internal(){
        return new ErrorDTO("500", "Serviço indisponivel", new Date());
    }
}
