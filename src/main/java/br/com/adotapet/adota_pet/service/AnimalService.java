
package br.com.adotapet.adota_pet.service;

import br.com.adotapet.adota_pet.entity.Animal;
import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal salvarAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public List<Animal> listarAnimais() {
        return animalRepository.findAll();
    }

    public Animal buscarPorId(Integer id) {
        return animalRepository.findById(id).orElse(null);
    }

    public List<Animal> buscarPorEspecie(Especie especie) {
        return animalRepository.findByEspecie(especie);
    }
}
