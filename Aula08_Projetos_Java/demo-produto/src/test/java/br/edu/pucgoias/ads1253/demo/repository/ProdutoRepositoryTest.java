package br.edu.pucgoias.ads1253.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.pucgoias.ads1253.demo.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @DataJpaTest carrega apenas a camada de persistencia e executa cada teste
 * dentro de uma transacao revertida ao final, garantindo isolamento.
 */
@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repositorio;

    @BeforeEach
    void prepararDados() {
        repositorio.deleteAll();
        repositorio.save(new Produto("Teclado mecanico", new BigDecimal("289.90"), 12));
        repositorio.save(new Produto("Mouse optico", new BigDecimal("79.90"), 2));
    }

    @Test
    @DisplayName("save atribui identificador gerado pelo banco de dados")
    void saveAtribuiIdentificador() {
        Produto salvo = repositorio.save(
                new Produto("Webcam HD", new BigDecimal("199.00"), 7));

        assertTrue(salvo.getId() != null);
    }

    @Test
    @DisplayName("consulta derivada localiza produto por trecho do nome")
    void consultaDerivadaPorNome() {
        List<Produto> encontrados = repositorio.findByNomeContainingIgnoreCase("MOUSE");

        assertEquals(1, encontrados.size());
        assertEquals("Mouse optico", encontrados.get(0).getNome());
    }

    @Test
    @DisplayName("consulta JPQL retorna produtos com estoque abaixo do minimo")
    void consultaJpqlPorEstoque() {
        List<Produto> criticos = repositorio.buscarComEstoqueAbaixoDe(5);

        assertEquals(1, criticos.size());
        assertEquals("Mouse optico", criticos.get(0).getNome());
    }

    @Test
    @DisplayName("delete remove o registro correspondente")
    void deleteRemoveRegistro() {
        Produto produto = repositorio.findByNomeContainingIgnoreCase("teclado").get(0);

        repositorio.deleteById(produto.getId());

        Optional<Produto> resultado = repositorio.findById(produto.getId());
        assertTrue(resultado.isEmpty());
    }
}
