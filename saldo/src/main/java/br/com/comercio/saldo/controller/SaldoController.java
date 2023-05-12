package br.com.comercio.saldo.controller;

import br.com.comercio.saldo.dto.MovDTO;
import br.com.comercio.saldo.dto.MovimentacaoDTO;
import br.com.comercio.saldo.dto.SaldoDTO;
import br.com.comercio.saldo.dto.SaldoTransactionDTO;
import br.com.comercio.saldo.service.SaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SaldoController {

    private final SaldoService saldoService;

    @PostMapping("debit")
    public SaldoTransactionDTO debto(@RequestBody MovimentacaoDTO movimentacao, @RequestHeader("Authorization") String authorization){
        return saldoService.debito(movimentacao,authorization );
    }
    @PostMapping("credit")
    public SaldoTransactionDTO credito(@RequestBody MovimentacaoDTO movimentacao, @RequestHeader("Authorization") String authorization){
        return saldoService.credito(movimentacao, authorization);
    }

    @GetMapping("balance")
    public SaldoDTO balance(@RequestHeader("Authorization") String authorization){
        return saldoService.balance(authorization);
    }

    @GetMapping("balance/all")
    public List<MovDTO> balanceAll(@RequestHeader("Authorization") String authorization){
        return saldoService.balanceAll(authorization);
    }

    @GetMapping("balance/ofday")
    public List<MovDTO> balanceAllOfDay(@RequestHeader("Authorization") String authorization){
        return saldoService.balanceAllOfDay(authorization);
    }
}
