package br.com.adotapet.adota_pet.repository;

import br.com.adotapet.adota_pet.entity.AnuncioAnimal;
import br.com.adotapet.adota_pet.enums.StatusAnuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnuncioAnimalRepository extends JpaRepository<AnuncioAnimal, Integer> {

    List<AnuncioAnimal> findByStatus(StatusAnuncio status);

}