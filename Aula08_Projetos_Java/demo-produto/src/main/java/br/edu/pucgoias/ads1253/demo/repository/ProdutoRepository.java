package br.edu.pucgoias.ads1253.demo.repository;

import br.edu.pucgoias.ads1253.demo.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio da entidade Produto.
 * A interface nao possui implementacao escrita manualmente: o Spring Data JPA
 * gera a implementacao em tempo de execucao.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Consulta derivada: o nome do metodo define a clausula WHERE.
    List<Produto> findByNomeContainingIgnoreCase(String trecho);

    List<Produto> findByPrecoLessThanEqualOrderByPrecoAsc(BigDecimal precoMaximo);

    // Consulta explicita em JPQL, quando o nome do metodo se tornaria extenso.
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEmEstoque < :minimo")
    List<Produto> buscarComEstoqueAbaixoDe(@Param("minimo") int minimo);
}
