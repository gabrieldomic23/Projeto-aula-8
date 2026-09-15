package br.edu.pucgoias.ads1253.demo.service;

import br.edu.pucgoias.ads1253.demo.model.Produto;
import br.edu.pucgoias.ads1253.demo.repository.ProdutoRepository;
import br.edu.pucgoias.ads1253.demo.service.exception.ProdutoNaoEncontradoException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de servico: concentra as regras de negocio e define os limites
 * transacionais. O repositorio e responsavel apenas pelo acesso aos dados.
 */
@Service
public class ProdutoService {

    private final ProdutoRepository repositorio;

    public ProdutoService(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    // CREATE
    @Transactional
    public Produto cadastrar(Produto produto) {
        return repositorio.save(produto);
    }

    // READ
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return repositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String trecho) {
        return repositorio.findByNomeContainingIgnoreCase(trecho);
    }

    // UPDATE
    @Transactional
    public Produto atualizarPreco(Long id, BigDecimal novoPreco) {
        Produto produto = buscarPorId(id);
        produto.setPreco(novoPreco);
        // A entidade esta gerenciada dentro da transacao: a atualizacao seria
        // gravada mesmo sem a chamada explicita a save (dirty checking).
        return repositorio.save(produto);
    }

    // DELETE
    @Transactional
    public void remover(Long id) {
        Produto produto = buscarPorId(id);
        repositorio.delete(produto);
    }
}
