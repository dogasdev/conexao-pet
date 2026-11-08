package br.com.adotapet.adota_pet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;
import br.com.adotapet.adota_pet.enums.UnidadeIdade;
import jakarta.validation.constraints.Size;

public class CriarAnuncioDTO {

    @NotBlank
    @Size(max = 60)
    private String nome;

    @NotNull
    @Min(0)
    private Integer idade;

    @NotNull
    private UnidadeIdade unidadeIdade;

    @NotNull
    private Boolean idadeAproximada;

    @NotNull
    private Especie especie;

    @NotNull
    private Sexo sexo;

    @NotNull
    private Porte porte;

    @NotBlank
    private String descricao;

    @NotBlank
    @Size(max = 255)
    private String fotoAnimal;

    public CriarAnuncioDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public UnidadeIdade getUnidadeIdade() {
        return unidadeIdade;
    }

    public void setUnidadeIdade(UnidadeIdade unidadeIdade) {
        this.unidadeIdade = unidadeIdade;
    }

    public Boolean getIdadeAproximada() {
        return idadeAproximada;
    }

    public void setIdadeAproximada(Boolean idadeAproximada) {
        this.idadeAproximada = idadeAproximada;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoAnimal() {
        return fotoAnimal;
    }

    public void setFotoAnimal(String fotoAnimal) {
        this.fotoAnimal = fotoAnimal;
    }
}