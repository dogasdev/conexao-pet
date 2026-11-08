
package br.com.adotapet.adota_pet.dto;

import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;
import br.com.adotapet.adota_pet.enums.StatusAnuncio;
import br.com.adotapet.adota_pet.enums.UnidadeIdade;

import java.time.LocalDateTime;

public record DetalhesAnuncioDTO(
        Integer id,
        String nomeAnimal,
        Integer idade,
        UnidadeIdade unidadeIdade,
        Boolean idadeAproximada,
        Especie especie,
        Sexo sexo,
        Porte porte,
        String descricao,
        String fotoAnimal,
        LocalDateTime dataPublicacao,
        StatusAnuncio status,
        String nomeResponsavel,
        String cidadeResponsavel
) {
}
