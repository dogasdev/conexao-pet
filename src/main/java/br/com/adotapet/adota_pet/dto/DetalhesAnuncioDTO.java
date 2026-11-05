package br.com.adotapet.adota_pet.dto;

import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;

public record DetalhesAnuncioDTO(
        Integer id,
        String nomeAnimal,
        Integer idade,
        Especie especie,
        Sexo sexo,
        Porte porte,
        String nomeResponsavel,
        String cidadeResponsavel
) {
}