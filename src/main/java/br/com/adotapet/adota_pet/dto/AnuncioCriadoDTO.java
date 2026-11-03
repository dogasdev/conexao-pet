package br.com.adotapet.adota_pet.dto;

import br.com.adotapet.adota_pet.enums.Especie;
import br.com.adotapet.adota_pet.enums.Porte;
import br.com.adotapet.adota_pet.enums.Sexo;
import br.com.adotapet.adota_pet.enums.StatusAnuncio;
import br.com.adotapet.adota_pet.enums.UnidadeIdade;

import java.time.LocalDateTime;

public class AnuncioCriadoDTO {

    private Integer idAnuncio;
    private Integer idAnimal;
    private String nome;
    private Integer idade;
    private UnidadeIdade unidadeIdade;
    private Boolean idadeAproximada;
    private Especie especie;
    private Sexo sexo;
    private Porte porte;
    private String descricao;
    private String fotoAnimal;
    private LocalDateTime dataPublicacao;
    private StatusAnuncio status;

    public AnuncioCriadoDTO() {
    }

    public AnuncioCriadoDTO(Integer idAnuncio, Integer idAnimal) {
        this.idAnuncio = idAnuncio;
        this.idAnimal = idAnimal;
    }

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public Integer getIdAnimal() {
        return idAnimal;
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

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public StatusAnuncio getStatus() {
        return status;
    }

    public void setStatus(StatusAnuncio status) {
        this.status = status;
    }
}