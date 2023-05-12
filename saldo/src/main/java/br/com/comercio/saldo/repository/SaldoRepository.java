package br.com.comercio.saldo.repository;

import br.com.comercio.saldo.model.Saldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, BigInteger> {

    @Query(nativeQuery = true, value = "SELECT * FROM saldo_comercio WHERE id_acesskey = ?1 ORDER BY id DESC LIMIT 1")
    public List<Saldo> findSaldoMaisRecente(String codigoCliente);

    @Query(nativeQuery = true, value = "SELECT * FROM saldo_comercio WHERE id_acesskey = ?1 ORDER BY id DESC")
    public List<Saldo> findSSaldo(String codigoCliente);

    @Query(nativeQuery = true, value = "SELECT * FROM saldo_comercio WHERE DATE(data_transacao) = CURDATE() AND id_acesskey = ?1 ORDER BY id DESC")
    public List<Saldo> findSSaldoDoDia(String codigoCliente);
}
