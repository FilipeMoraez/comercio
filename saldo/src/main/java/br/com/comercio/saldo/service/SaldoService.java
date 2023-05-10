package br.com.comercio.saldo.service;


import br.com.comercio.saldo.dto.MovimentacaoDTO;
import br.com.comercio.saldo.dto.SaldoDTO;
import br.com.comercio.saldo.dto.SaldoTransactionDTO;
import br.com.comercio.saldo.exception.InsuficientBalanceException;
import br.com.comercio.saldo.model.Saldo;
import br.com.comercio.saldo.repository.SaldoRepository;
import br.com.comercio.saldo.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaldoService {

    final private SaldoRepository saldoRepository;

    public SaldoTransactionDTO debito(MovimentacaoDTO movimentacao,String authorization ){
        var customerId = SecurityUtils.getCodeAccess(authorization);
        List<Saldo> transaction = saldoRepository.findSaldoMaisRecente(customerId);
        var novoSaldo = new Saldo();
        novoSaldo.setValor(movimentacao.getValor());
        novoSaldo.setDescricao(movimentacao.getDescricao());
        novoSaldo.setIdTransacao(UUID.randomUUID().toString());
        novoSaldo.setTipoTrasancao("DEBITO");
        if(Objects.nonNull(transaction) && !transaction.isEmpty()) {
            var ultimoSaldo=  transaction.get(0);
            if(ultimoSaldo.getSaldo().compareTo(movimentacao.getValor()) >= 0){
                novoSaldo.setSaldo(ultimoSaldo.getSaldo().subtract(movimentacao.getValor()));
            }else{
                throw new InsuficientBalanceException();
            }
        }else{
            throw new InsuficientBalanceException();
        }
        novoSaldo.setDataTransacao(new Date());
        novoSaldo.setCodigoCliente(customerId);
        saldoRepository.save(novoSaldo);

        return new SaldoTransactionDTO(novoSaldo.getIdTransacao(), novoSaldo.getValor(), novoSaldo.getDataTransacao());
    }


    public SaldoTransactionDTO credito(MovimentacaoDTO movimentacao,String authorization ){
        var customerId = SecurityUtils.getCodeAccess(authorization);
        List<Saldo> transaction = saldoRepository.findSaldoMaisRecente(customerId);
        var novoSaldo = new Saldo();
        novoSaldo.setValor(movimentacao.getValor());
        novoSaldo.setDescricao(movimentacao.getDescricao());
        novoSaldo.setIdTransacao(UUID.randomUUID().toString());
        novoSaldo.setTipoTrasancao("CREDITO");
        if(Objects.nonNull(transaction) && !transaction.isEmpty()) {
            var ultimoSaldo=  transaction.get(0);
            novoSaldo.setSaldo(ultimoSaldo.getSaldo().add(movimentacao.getValor()));
        }else{
            novoSaldo.setSaldo(movimentacao.getValor());
        }
        novoSaldo.setDataTransacao(new Date());
        novoSaldo.setCodigoCliente(customerId);

        saldoRepository.save(novoSaldo);

        return new SaldoTransactionDTO(novoSaldo.getIdTransacao(), novoSaldo.getValor(), novoSaldo.getDataTransacao());
    }

    public SaldoDTO balance(String authorization) {
            var customerId = SecurityUtils.getCodeAccess(authorization);
            List<Saldo> transaction = saldoRepository.findSaldoMaisRecente(customerId);
            if (Objects.nonNull(transaction) && !transaction.isEmpty()) {
                var ultimoSaldo = transaction.get(0);
                return new SaldoDTO(ultimoSaldo.getSaldo(), new Date());
            } else {
                return new SaldoDTO(new BigDecimal("0"), new Date());
            }
    }
}
