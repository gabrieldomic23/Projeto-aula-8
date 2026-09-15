package br.edu.pucgoias.ads1253.clinica.repository;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorId(Long tutorId);

    List<Animal> findByEspecieIgnoreCase(String especie);
}