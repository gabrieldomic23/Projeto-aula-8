package br.edu.pucgoias.ads1253.demo.service.exception;

/** Excecao de dominio lancada quando um identificador nao corresponde a nenhum registro. */
public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto nao encontrado para o identificador " + id);
    }
}
