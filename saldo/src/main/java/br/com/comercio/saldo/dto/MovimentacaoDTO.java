package br.com.comercio.saldo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovimentacaoDTO {
    private String descricao;
    private BigDecimal valor;

}
