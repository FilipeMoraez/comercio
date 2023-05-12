package br.com.comercio.saldo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovDTO {
    private String idTransacao;
    private String descricao;
    private BigDecimal valor;
    private String data;
    private BigDecimal saldo;
    private String tipo;
}
