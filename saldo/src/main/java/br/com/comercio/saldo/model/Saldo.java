package br.com.comercio.saldo.model;

import br.com.comercio.saldo.dto.MovimentacaoDTO;
import br.com.comercio.saldo.exception.InsuficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="saldo_comercio", schema = "comerciosaldodb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Saldo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private BigInteger id;

    @Column(name="descricao")
    private String descricao;

    @Column(name="valor")
    private BigDecimal valor;

    @Column(name="data_transacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTransacao;

    @Column(name="tipo_transacao")
    private String tipoTrasancao;

    @Column(name="saldo")
    private BigDecimal saldo;

    @Column(name="id_transacao")
    private String idTransacao;

    @Column(name="id_acesskey")
    private String codigoCliente;


    public static Saldo buildSaldo(MovimentacaoDTO movimentacao, String customerId, List<Saldo> transaction) {
        var saldo = new Saldo();
        saldo.setValor(movimentacao.getValor());
        saldo.setDescricao(movimentacao.getDescricao());
        saldo.setIdTransacao(UUID.randomUUID().toString());
        saldo.setDataTransacao(new Date());
        saldo.setCodigoCliente(customerId);
        return saldo;
    }


}
