package br.com.comercio.saldo.repository;

import br.com.comercio.saldo.model.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, BigInteger> {

    @Query(nativeQuery = true, value = "SELECT * FROM SALDO_COMERCIO WHERE ID_ACESSKEY = ?1 ORDER BY ID DESC LIMIT 1")
    public List<Saldo> findSaldoMaisRecente(String codigoCliente);


}
