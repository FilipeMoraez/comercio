package br.com.comercio.saldo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="SALDO_COMERCIO")
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


}
