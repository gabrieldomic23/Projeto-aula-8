package br.edu.pucgoias.ads1253.clinica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import br.edu.pucgoias.ads1253.clinica.service.exception.CpfDuplicadoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.RecursoNaoEncontradoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.TutorComAnimaisException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Suite de testes da atividade pratica. Cada teste executa em uma transacao
 * revertida ao final, de modo que os casos nao interferem uns nos outros.
 */
@SpringBootTest
@Transactional
class ClinicaServiceTest {

    @Autowired
    private ClinicaService servico;

    private Tutor novoTutor(String nome, String cpf) {
        return new Tutor(nome, cpf, "62999990000");
    }

    @Test
    @DisplayName("1. Cadastro de tutor atribui identificador gerado pelo banco")
    void cadastroDeTutorAtribuiIdentificador() {
        Tutor salvo = servico.cadastrarTutor(novoTutor("Ana Souza", "11111111111"));

        assertNotNull(salvo.getId());
        assertEquals("Ana Souza", servico.buscarTutor(salvo.getId()).getNome());
    }

    @Test
    @DisplayName("2. Cadastro com CPF ja existente lanca CpfDuplicadoException")
    void cadastroComCpfDuplicadoLancaExcecao() {
        servico.cadastrarTutor(novoTutor("Ana Souza", "22222222222"));

        CpfDuplicadoException erro = assertThrows(CpfDuplicadoException.class,
                () -> servico.cadastrarTutor(novoTutor("Bruno Lima", "22222222222")));

        assertTrue(erro.getMessage().contains("22222222222"));
    }

    @Test
    @DisplayName("3. Busca por identificador inexistente lanca RecursoNaoEncontradoException")
    void buscaPorIdentificadorInexistenteLancaExcecao() {
        assertThrows(RecursoNaoEncontradoException.class, () -> servico.buscarTutor(9999L));
    }

    @Test
    @DisplayName("4. Cadastro de animal vincula o registro ao tutor informado")
    void cadastroDeAnimalVinculaAoTutor() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Carla Dias", "33333333333"));

        Animal animal = servico.cadastrarAnimal(tutor.getId(),
                new Animal("Rex", "Cao", LocalDate.of(2022, 3, 15)));

        assertNotNull(animal.getId());
        assertEquals(tutor.getId(), animal.getTutor().getId());
    }

    @Test
    @DisplayName("5. Atualizacao de tutor persiste as alteracoes")
    void atualizacaoDeTutorPersisteAlteracoes() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Diego Alves", "44444444444"));

        servico.atualizarTutor(tutor.getId(), "Diego Alves Filho", "62988887777");

        Tutor recuperado = servico.buscarTutor(tutor.getId());
        assertEquals("Diego Alves Filho", recuperado.getNome());
        assertEquals("62988887777", recuperado.getTelefone());
        assertEquals(tutor.getId(), recuperado.getId());
    }

    @Test
    @DisplayName("6. Remocao de tutor com animais vinculados lanca TutorComAnimaisException")
    void remocaoDeTutorComAnimaisLancaExcecao() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Elisa Nunes", "55555555555"));
        servico.cadastrarAnimal(tutor.getId(), new Animal("Mel", "Gato", LocalDate.of(2021, 8, 1)));

        TutorComAnimaisException erro = assertThrows(TutorComAnimaisException.class,
                () -> servico.removerTutor(tutor.getId()));

        assertTrue(erro.getMessage().contains(String.valueOf(tutor.getId())));
    }

    @Test
    @DisplayName("7. Listagem retorna apenas os animais do tutor informado")
    void listagemRetornaApenasAnimaisDoTutor() {
        Tutor primeiro = servico.cadastrarTutor(novoTutor("Fabio Rocha", "66666666666"));
        Tutor segundo = servico.cadastrarTutor(novoTutor("Gabriela Melo", "77777777777"));
        servico.cadastrarAnimal(primeiro.getId(), new Animal("Bidu", "Cao", LocalDate.of(2020, 1, 10)));
        servico.cadastrarAnimal(primeiro.getId(), new Animal("Nina", "Cao", LocalDate.of(2023, 5, 2)));
        servico.cadastrarAnimal(segundo.getId(), new Animal("Tom", "Gato", LocalDate.of(2019, 11, 30)));

        List<Animal> animais = servico.listarAnimaisDoTutor(primeiro.getId());

        assertEquals(2, animais.size());
        assertTrue(animais.stream().allMatch(a -> a.getTutor().getId().equals(primeiro.getId())));
    }
}
