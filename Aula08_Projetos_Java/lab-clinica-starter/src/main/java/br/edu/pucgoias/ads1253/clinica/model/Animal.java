package br.edu.pucgoias.ads1253.clinica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String especie;

    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    protected Animal() {
    }

    public Animal(String nome, String especie, LocalDate dataNascimento, Tutor tutor) {
        this.nome = nome;
        this.especie = especie;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public Animal(String nome, String especie, LocalDate dataNascimento) {
    this.nome = nome;
    this.especie = especie;
    this.dataNascimento = dataNascimento;
}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}