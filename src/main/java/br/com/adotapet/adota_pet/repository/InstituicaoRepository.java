package br.com.adotapet.adota_pet.repository;

import br.com.adotapet.adota_pet.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

    boolean existsByCnpj(String cnpj);
}