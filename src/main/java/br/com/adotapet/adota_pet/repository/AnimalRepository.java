package br.com.adotapet.adota_pet.repository;

import br.com.adotapet.adota_pet.entity.Animal;
import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findByEspecie(Especie especie);

    List<Animal> findByPorte(Porte porte);

    List<Animal> findBySexo(Sexo sexo);

    List<Animal> findByIdade(Integer idade);

}