package com.tcs.company.domain.repository;

import com.tcs.company.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
    List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);
    // Cliente findOneByNome(String nome);
    boolean existsByNome(String nome);

    // Consulta: JPQL
    @Query(value=" select c from Cliente c where c.nome like :nome ")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    // Consulta: SQL Nativa
    @Query(value=" select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeSQL(@Param("nome") String nome);

    @Query(" delete from Cliente c where c.nome = :nome ")
    @Modifying
    void deleteByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id );
}
