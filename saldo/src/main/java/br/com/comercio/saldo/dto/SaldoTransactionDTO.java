package br.com.comercio.saldo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SaldoTransactionDTO extends SaldoDTO{
    public SaldoTransactionDTO(String transactionId, BigDecimal saldo, Date data){
        this.transactionId = transactionId;
        super.setData(data);
        super.setSaldo(saldo);
    }
    private String transactionId;
}
