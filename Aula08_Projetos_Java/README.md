# ADS1253 — Aula 8 — Operações CRUD integradas a aplicações orientadas a objetos

Material de apoio em código. Três projetos Maven independentes:

| Pasta | Uso |
|-------|-----|
| `demo-produto/` | Projeto da demonstração conduzida pelo professor (Parte 1). CRUD completo da entidade `Produto` com Spring Data JPA e H2, incluindo `CommandLineRunner` que executa o ciclo CREATE/READ/UPDATE/DELETE no console e uma suíte `@DataJpaTest`. |
| `lab-clinica-starter/` | Projeto entregue aos alunos (Parte 2). Contém a entidade `Tutor`, o `TutorRepository` e duas operações de serviço como referência; as demais etapas são o exercício. Distribuir esta pasta pelo AVA. |
| `lab-clinica-solucao/` | Implementação de referência da atividade, com os sete casos de teste completos. Uso exclusivo do professor. |

## Requisitos

JDK 21, Maven 3.9+. O banco H2 é embarcado e em memória: nenhuma instalação
é necessária no laboratório. O arquivo `application-mysql.properties` de cada
projeto traz a configuração equivalente para MySQL.

## Comandos

    mvn test              # executa a suíte de testes
    mvn spring-boot:run   # inicia a aplicação
