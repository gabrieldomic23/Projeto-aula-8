package br.edu.pucgoias.ads1253.demo.runner;

import br.edu.pucgoias.ads1253.demo.model.Produto;
import br.edu.pucgoias.ads1253.demo.service.ProdutoService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Executa o ciclo CRUD completo na inicializacao da aplicacao,
 * permitindo acompanhar no console o SQL gerado pelo Hibernate.
 */
@Component
public class DemoRunner implements CommandLineRunner {

    private final ProdutoService servico;

    public DemoRunner(ProdutoService servico) {
        this.servico = servico;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n--- CREATE ---");
        Produto teclado = servico.cadastrar(
                new Produto("Teclado mecanico", new BigDecimal("289.90"), 12));
        servico.cadastrar(new Produto("Mouse optico", new BigDecimal("79.90"), 40));
        servico.cadastrar(new Produto("Monitor 24 polegadas", new BigDecimal("899.00"), 5));
        System.out.println("Registro inserido: " + teclado);

        System.out.println("\n--- READ ---");
        servico.listarTodos().forEach(System.out::println);
        System.out.println("Busca por 'mou': " + servico.buscarPorNome("mou"));

        System.out.println("\n--- UPDATE ---");
        Produto atualizado = servico.atualizarPreco(teclado.getId(), new BigDecimal("249.90"));
        System.out.println("Registro atualizado: " + atualizado);

        System.out.println("\n--- DELETE ---");
        servico.remover(teclado.getId());
        System.out.println("Total de registros apos a remocao: " + servico.listarTodos().size());
    }
}
