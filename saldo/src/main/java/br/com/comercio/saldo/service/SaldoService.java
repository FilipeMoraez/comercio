package br.com.comercio.saldo.service;


import br.com.comercio.saldo.dto.MovDTO;
import br.com.comercio.saldo.dto.MovimentacaoDTO;
import br.com.comercio.saldo.dto.SaldoDTO;
import br.com.comercio.saldo.dto.SaldoTransactionDTO;
import br.com.comercio.saldo.exception.InsuficientBalanceException;
import br.com.comercio.saldo.model.Saldo;
import br.com.comercio.saldo.repository.SaldoRepository;
import br.com.comercio.saldo.util.DateUtils;
import br.com.comercio.saldo.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaldoService {

    final private SaldoRepository saldoRepository;

    public SaldoTransactionDTO debito(MovimentacaoDTO movimentacao,String authorization ){
        var customerId = SecurityUtils.getCodeAccess(authorization);
        List<Saldo> transaction = saldoRepository.findSaldoMaisRecente(customerId);
        var saldo = Saldo.buildSaldo(movimentacao, customerId, transaction);
        saldo.setTipoTrasancao("DEBITO");
        if(Objects.nonNull(transaction) && !transaction.isEmpty()) {
            var ultimoSaldo=  transaction.get(0);
            if(ultimoSaldo.getSaldo().compareTo(movimentacao.getValor()) >= 0){
                saldo.setSaldo(ultimoSaldo.getSaldo().subtract(movimentacao.getValor()));
            }else{
                throw new InsuficientBalanceException();
            }
        }else{
            throw new InsuficientBalanceException();
        }
        saldoRepository.save(saldo);

        return new SaldoTransactionDTO(saldo.getIdTransacao(), saldo.getSaldo(), saldo.getDataTransacao());
    }



    public SaldoTransactionDTO credito(MovimentacaoDTO movimentacao,String authorization ){
        var customerId = SecurityUtils.getCodeAccess(authorization);
        List<Saldo> transaction = saldoRepository.findSaldoMaisRecente(customerId);
        var novoSaldo = Saldo.buildSaldo(movimentacao, customerId, transaction);
        novoSaldo.setTipoTrasancao("CREDITO");
        if(Objects.nonNull(transaction) && !transaction.isEmpty()) {
            var ultimoSaldo=  transaction.get(0);
            novoSaldo.setSaldo(ultimoSaldo.getSaldo().add(movimentacao.getValor()));
        }else{
            novoSaldo.setSaldo(movimentacao.getValor());
        }
        saldoRepository.save(novoSaldo);

        return new SaldoTransactionDTO(novoSaldo.getIdTransacao(), novoSaldo.getSaldo(), novoSaldo.getDataTransacao());
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

    public List<MovDTO> balanceAll(String authorization) {
        var customerId = SecurityUtils.getCodeAccess(authorization);

        var transaction = saldoRepository.findSSaldo(customerId);

        var list = new ArrayList<MovDTO>();

        transaction.forEach(e -> {if(e.getTipoTrasancao().equals("DEBITO")){e.setValor((e.getValor().multiply(BigDecimal.valueOf(-1l))));} });
        list.addAll(transaction.stream().map(e ->
                new MovDTO(e.getIdTransacao(), e.getDescricao(), e.getValor(), DateUtils.convertData(e.getDataTransacao()), e.getSaldo(), e.getTipoTrasancao())).collect(Collectors.toList()));
        list.add(new MovDTO("Saldo Inicial", "-", BigDecimal.valueOf(0l), "-", BigDecimal.valueOf(0l), ""));

        return list;
    }

    public List<MovDTO> balanceAllOfDay(String authorization) {
        var customerId = SecurityUtils.getCodeAccess(authorization);

        var transaction = saldoRepository.findSSaldoDoDia(customerId);

        var list = new ArrayList<MovDTO>();

        transaction.forEach(e -> {if(e.getTipoTrasancao().equals("DEBITO")){e.setValor((e.getValor().multiply(BigDecimal.valueOf(-1l))));} });
        list.addAll(transaction.stream().map(e ->
                new MovDTO(e.getIdTransacao(), e.getDescricao(), e.getValor(), DateUtils.convertData(e.getDataTransacao()), e.getSaldo(), e.getTipoTrasancao())).collect(Collectors.toList()));

        return list;
    }
}
