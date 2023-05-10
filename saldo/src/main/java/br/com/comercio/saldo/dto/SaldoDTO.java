package br.com.comercio.saldo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SaldoDTO {
    private BigDecimal saldo;
    private Date data;

}
